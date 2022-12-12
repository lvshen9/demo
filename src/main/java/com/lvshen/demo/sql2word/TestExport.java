package com.lvshen.demo.sql2word;

import org.junit.Test;

/**
 * Description:将你的表结构（sql格式）转换成word文档
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022/9/13 18:09
 * @since JDK 1.8
 */
public class TestExport {
    @Test
    public  void test() throws Exception {
        //填写你Navicat导出的sql结构文件
        String sqlPath = "D:\\Work\\任务\\SRM-寻源到合同\\供应商注册\\SAP同步SRM供应商信息接口涉及相关表.sql";
        //String sqlPath = "D:\\Work\\任务\\SRM-寻源到合同\\供应商注册\\供应商质量\\供应商质量.sql";
        //填写你写出word文件的路径
        //String toWordPath = "D:\\Work\\任务\\SRM-寻源到合同\\供应商注册\\供应商质量\\供应商质量-20220915.docx";
        String toWordPath = "D:\\Work\\任务\\SRM-寻源到合同\\供应商注册\\SAP同步SRM供应商信息接口涉及相关表.docx";
        // 写出word文档 非商业版 Spire.Doc 限制25 张表格 所以我建议25张表 25张表生成，然后手动复制到一个word里面合并
        // Spire.Doc free version is limited to 25 tables. This limitation is enforced during reading or writing files.
        // Upgrade to Commercial Edition of Spire.Doc <https://www.e-iceblue.com/Introduce/doc-for-java.html>
        ExportSqlExcel.writeWord(ExportSqlExcel.readSqlFile(sqlPath), toWordPath);
        System.out.println("生成完成!");
    }
}
