package com.lvshen.demo.spring;

import com.lvshen.demo.spring.annotation.MyAutowired;
import com.lvshen.demo.spring.annotation.MyController;
import com.lvshen.demo.spring.annotation.MyRequestMapping;
import com.lvshen.demo.spring.annotation.MyService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/3/26 17:02
 * @since JDK 1.8
 */
@Slf4j
public class MyDispatcherServlet extends HttpServlet {

    private Properties configProperties = new Properties();
    private List<String> classNames = new ArrayList<>();
    private Map<String, Object> ioc = new HashMap<>();
    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.调用
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception Detail " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        uri = uri.replaceAll(contextPath,"").replaceAll("/+","/");

        if (!this.handlerMapping.containsKey(uri)) {
            resp.getWriter().write("404 Not Found！！");
            return;
        }

        Map<String, String[]> parameterMap = req.getParameterMap();
        Method method = this.handlerMapping.get(uri);
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(beanName,new Object[]{req,resp,parameterMap.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描相关的类
        doScanner(configProperties.getProperty("scanPackage"));

        //3.实例化相关的类
        doInstance();

        //4.完成依赖注入
        doAutowired();

        //5.初始化HandlerMapping
        doInitHandlerMapping();
    }

    private void doInitHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }

                MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");

                handlerMapping.put(url, method);

                log.info("Mapped:[{}.{}]", url, method);

            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(MyAutowired.class)) {
                    continue;
                }
                MyAutowired autowired = field.getAnnotation(MyAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }

                field.setAccessible(true);

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                //beanName默认类名首字母小写
                //<bean id="" class="">
                if (clazz.isAnnotationPresent(MyController.class)) {
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, clazz.getInterfaces());

                } else if (clazz.isAnnotationPresent(MyService.class)) {
                    //1.默认类名是字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());

                    //2.自定义类名
                    MyService service = clazz.getAnnotation(MyService.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = null;
                    try {
                        instance = clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ioc.put(beanName, clazz.getInterfaces());
                    //3.接口不能实例化，应该实例化其实现的类
                    for (Class<?> aClass : clazz.getInterfaces()) {
                        if (ioc.containsKey(aClass.getName())) {
                            try {
                                throw new Exception("the beanName is exists");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ioc.put(aClass.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("类文件加载错误:", e);
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }

    }

    private void doLoadConfig(String config) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(config);
        try {
            configProperties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取properties文件异常：", e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("流关闭异常：", e);
                }
            }
        }
    }
}
