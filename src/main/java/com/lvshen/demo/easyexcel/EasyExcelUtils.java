package com.lvshen.demo.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/17 13:44
 * @since JDK 1.8
 */
public class EasyExcelUtils {

    public static OutputStream getOutputStream(String fileName, HttpServletResponse response)
            throws Exception{
        try {
            //设置文件名
            fileName = new String((fileName +" "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .getBytes(),"UTF-8");
            //设置文件ContentType类型
            response.setContentType("multipart/form-data");
            //设置服务器响应数据的编码
            response.setCharacterEncoding("utf-8");
            //设置文件头
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("创建文件失败！");
        }

    }

    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list, String fileName,
                                  String sheetName, Class clazz) throws Exception {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1, 0, clazz);
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出带有表头注解的Java实体类模型
     * 导出单个sheet 的Excel，带表头，可自定义sheet表格名称
     *
     * @param outputStream 字节输出流
     * @param clazz 类的字节码文件对象
     * @param sheetName sheet表格名称
     * @param data 实体对象集合
     */
    public static void writeExcel(OutputStream outputStream, Class clazz, String sheetName, List<? extends BaseRowModel> data){

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
            writer.write(data,sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭缓冲字节输出流，释放资源
            if(bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
