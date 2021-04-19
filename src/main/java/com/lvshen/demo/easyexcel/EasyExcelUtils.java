package com.lvshen.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 13:44
 * @since JDK 1.8
 */
public class EasyExcelUtils {



    public static void exportExcelWeb(HttpServletResponse response, List<? extends BaseRowModel> list, Class clazz, String fileName, String sheetName) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }
        if (StringUtils.isBlank(fileName)) {
            fileName = "Excel导出";
        }
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileSuffix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            fileName = fileSuffix.concat("-").concat(fileName);
            String fileNameAfterEncode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileNameAfterEncode + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(Boolean.FALSE).sheet(sheetName)
                    .doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 导出带有表头注解的Java实体类模型
     * 导出单个sheet 的Excel，带表头，可自定义sheet表格名称
     *
     * @param outputStream 字节输出流
     * @param clazz        类的字节码文件对象
     * @param sheetName    sheet表格名称
     * @param data         实体对象集合
     */
    public static void writeExcel(OutputStream outputStream, Class clazz, String sheetName, List<? extends BaseRowModel> data) {

        BufferedOutputStream bufferedOutputStream = null;

        try {
            //包装成缓冲字节输入流
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            //包装成缓冲字节输入流
            ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
            //创建sheet表格，并设置表格名称
            Sheet sheet = new Sheet(1, 0, clazz);
            sheet.setSheetName(sheetName);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            //把数据写入表格
            writer.write(data, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭缓冲字节输出流，释放资源
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
