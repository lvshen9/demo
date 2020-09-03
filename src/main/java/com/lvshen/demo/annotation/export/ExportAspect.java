package com.lvshen.demo.annotation.export;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: AOP方法增强
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-31 15:05
 * @since JDK 1.8
 */
@Component
@Aspect
@Slf4j
public class ExportAspect {
    @Pointcut("@annotation(com.lvshen.demo.annotation.export.ExportExcel)")
    public void exportPointcut() {

    }

    @Around("exportPointcut()")
    public void doExport(ProceedingJoinPoint point) throws Throwable {
        Object o = point.proceed();
        if (o instanceof List) {
            List list = (List) o;

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method;
            method = point.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
            ExportExcel annotation = method.getAnnotation(ExportExcel.class);
            //1.获取ExportExcel注解上beanClass的值
            Class<?> aClass = annotation.beanClass();
            Object[] args = point.getArgs();
            //2.获取方法第一个参数值
            HttpServletResponse response = (HttpServletResponse) args[0];

            exportUtils(response, list, aClass);
        }

    }

    public <T> void exportUtils(HttpServletResponse response, List<T> list, Class<T> aClass) {

        try {
            XSSFWorkbook workbook = exportExcelByAnnotation(list, aClass);

            OutputStream output = getOutputStream(response);
            //FileOutputStream output = getOutputStreamForTest();
            workbook.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    private FileOutputStream getOutputStreamForTest() throws Exception {
        String exportPositionPath = "E:\\work\\ExportExcel.xlsx";
        File file = new File(exportPositionPath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //workbook.write(fileOutputStream);
        log.info("导出成功！！！");
        return fileOutputStream;
    }

    private OutputStream getOutputStream(HttpServletResponse response) throws IOException {
        response.reset(); // 清除buffer缓存
        //处理请求
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".xlsx");
        return response.getOutputStream();
    }

    // 导出
    public <T> XSSFWorkbook exportExcelByAnnotation(List<T> list, Class<T> relClass) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//日期类型数据导出格式化

        XSSFWorkbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet();

        List<FieldEntity> fieldEntityList = Lists.newArrayList();
        Field[] fields = relClass.getDeclaredFields();
        Row row = sheet.createRow(0);
        int titleNum = 0;
        for (Field field : fields) {
            FieldEntity fieldEntity = new FieldEntity();
            //获取标注了注解的字段
            ExportFiled fieldAnnotation = field.getAnnotation(ExportFiled.class);
            //如果没有标注注解，直接跳过循环
            if (fieldAnnotation == null) {
                continue;
            }
            field.setAccessible(true);
            //获取字段标注的title
            String titleName = fieldAnnotation.name();

            //序号
            String number = fieldAnnotation.number();
            String fieldName = field.getName();

            fieldEntity.setFieldName(fieldName);
            fieldEntity.setNumber(number);

            Cell cell = row.createCell(titleNum);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(titleName);
            fieldEntityList.add(fieldEntity);
            titleNum++;
        }
        //按number排序
        List<FieldEntity> fieldEntitiesAfterSorted = fieldEntityList.stream().sorted(Comparator.comparing(FieldEntity::getNumber)).collect(Collectors.toList());
        List<String> fieldList = fieldEntitiesAfterSorted.stream().map(FieldEntity::getFieldName).collect(Collectors.toList());

        for (int i = 1; i <= list.size(); i++) {
            T obj = list.get(i - 1);
            Row dataRow = sheet.createRow(i);
            for (int j = 0; j < fieldList.size(); j++) {
                String fieldName = fieldList.get(j);
                Field field = obj.getClass().getDeclaredField(fieldName);
                Cell dataCell = dataRow.createCell(j);
                dataCell.setCellType(CellType.STRING);
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    if (field.getType().toString().contains("Date")) {
                        dataCell.setCellValue(sdf.format(field.get(obj)));
                    } else {
                        dataCell.setCellValue(String.valueOf(field.get(obj)));
                    }
                } else {
                    dataCell.setCellValue("");
                }
            }
        }
        return book;
    }

    class FieldEntity {
        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getFieldName() {
            return FieldName;
        }

        public void setFieldName(String fieldName) {
            FieldName = fieldName;
        }

        private String number;
        private String FieldName;
    }
}
