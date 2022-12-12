package com.lvshen.demo.sql2word;


import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TableOfContent;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.ParagraphFormat;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022/9/13 13:59
 * @since JDK 1.8
 */
public class ExportSqlExcel {
    /**
     * word 表格 列
     */
    static String[] header = {"名称", "字段名", "数据类型", "长度", "必填", "描述"};
    /**
     * 匹配数据类型长度
     */
    private static final Pattern COLUMN_LEN_REGEX = Pattern.compile("\\(.*\\)");
    /**
     * 匹配字段名
     */
    private static final Pattern COLUMN_START_REGEX = Pattern.compile("\\s{2}`*.*`\\s");
    /**
     * 匹配注释
     */
    private static final Pattern DESC_REGEX = Pattern.compile("\\s'.*',$");
    /**
     * 匹配字段行 数据类型
     */
    private static final Pattern TYPE_REGEX = Pattern.compile("`\\u0020(tinyint|smallint|mediumint|int|integer|bigint|float|double|decimal|date|time|year|datetime|timestamp|char|varchar|tinyblob|tinytext|blob|text|mediumblob|mediumtext|longblob|longtext)([(\\u0020])");
    /**
     * 匹配中文表名
     */
    private static final Pattern CHIN_NAME_REGEX = Pattern.compile("'.*'");
    /**
     * 匹配英文表名
     */
    private static final Pattern ENG_NAME_REGEX = Pattern.compile("`.*`");
    /**
     * 匹配必填
     */
    private static final String MUST = "NOT NULL";


    public static void writeWord(List<DatabaseTable> list, String toPath) {
        Document document = new Document();
        Section section = document.addSection();

        insertHeaderAndFooter(section);

        //设置标题样式
        ParagraphStyle style1 = new ParagraphStyle(document);
        style1.setName("titleStyle");
        style1.getCharacterFormat().setBold(true);
        style1.getCharacterFormat().setTextColor(Color.BLACK);
        style1.getCharacterFormat().setFontName("JetBrains Mono");
        style1.getCharacterFormat().setFontSize(16f);
        document.getStyles().add(style1);


        Paragraph parainserted = new Paragraph(document);
        TextRange tr = parainserted.appendText("目 录");
        tr.getCharacterFormat().setBold(true);
        tr.getCharacterFormat().setTextColor(Color.gray);
        tr.getCharacterFormat().setFontName("微软雅黑");
        document.getSections().get(0).getParagraphs().insert(0, parainserted);
        parainserted.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);

        //通过域代码添加目录表
        Paragraph paragraph = section.addParagraph();
        paragraph.appendTOC(1, 3);
        ParagraphFormat paragraphFormat = paragraph.getFormat();
        paragraphFormat.setHorizontalAlignment(HorizontalAlignment.Right);

        paragraph.appendBreak(BreakType.Page_Break);


        for (DatabaseTable databaseTable : list) {
            //添加word标题
            Paragraph para = section.addParagraph();
            TextRange textRange = para.appendText(getName(databaseTable));
            //para.applyStyle("titleStyle");
            para.applyStyle(BuiltinStyle.Title);
            textRange.getCharacterFormat().setFontName("JetBrains Mono");
            para.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
            para.getFormat().setAfterSpacing(10f);

            //对象转为二维数组 以顺序性写入word表格的每一列
            String[][] data = convertArr(databaseTable.getTableRow());
            addTable(section, data);
        }
        //更新目录
        document.updateTableOfContents();

        insertTextWatermark(document.getSections().get(0));
        //insertPictureWatermark(document);

        document.saveToFile(toPath, FileFormat.Docx_2013);
    }

    private static void insertTextWatermark(Section section) {
        TextWatermark txtWatermark = new TextWatermark();
        txtWatermark.setText("数字供应链专用");
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);
        section.getDocument().setWatermark(txtWatermark);
    }

    public static void insertPictureWatermark(Document document) {
        PictureWatermark picture = new PictureWatermark();
        picture.setPicture("src\\main\\resources\\zvalley.png");
        picture.setScaling(20);
        picture.isWashout(false);
        document.setWatermark(picture);
    }

    private static void insertHeaderAndFooter(Section section) {
        //页码页脚
        //分别获取section的页眉页脚
        HeaderFooter header = section.getHeadersFooters().getHeader();
        HeaderFooter footer = section.getHeadersFooters().getFooter();
        //添加段落到页眉
        Paragraph headerParagraph = header.addParagraph();

        //插入图片到页眉的段落
        DocPicture headerPicture = headerParagraph.appendPicture("src\\main\\resources\\zvalley.png");
        headerPicture.setHorizontalAlignment(ShapeHorizontalAlignment.Left);
        headerPicture.setVerticalOrigin(VerticalOrigin.Top_Margin_Area);
        headerPicture.setVerticalAlignment(ShapeVerticalAlignment.Bottom);
        headerPicture.setHeight(18);
        headerPicture.setWidth(58);

        //添加文字到页眉的段落
        TextRange text = headerParagraph.appendText("数字供应链软件开发室");
        text.getCharacterFormat().setFontName("微软雅黑");
        text.getCharacterFormat().setFontSize(8);
        text.getCharacterFormat().setItalic(true);
        headerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Right);

        //设置文字环绕方式
        headerPicture.setTextWrappingStyle(TextWrappingStyle.Behind);

        //设置页眉段落的底部边线样式
        headerParagraph.getFormat().getBorders().getBottom().setBorderType(BorderStyle.Single);
        headerParagraph.getFormat().getBorders().getBottom().setLineWidth(1f);

        //添加段落到页脚
        Paragraph footerParagraph = footer.addParagraph();

        //添加Field_Page和Field_Num_Pages域到页脚段落，用于显示当前页码和总页数
        footerParagraph.appendField("page number", FieldType.Field_Page);
        footerParagraph.appendText(" of ");
        footerParagraph.appendField("number of pages", FieldType.Field_Num_Pages);
        footerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Right);

        //设置页脚段落的顶部边线样式
        footerParagraph.getFormat().getBorders().getTop().setBorderType(BorderStyle.Single);
        footerParagraph.getFormat().getBorders().getTop().setLineWidth(1f);
    }

    private static String getName(DatabaseTable databaseTable) {
        StringBuilder builder = new StringBuilder();
        if (databaseTable.getChinTableName() != null) {
            builder.append(databaseTable.getChinTableName());
        }
        if (databaseTable.getEngTableName() != null) {
            builder.append(databaseTable.getEngTableName());
        }
        return builder.toString();
    }

    private static String[][] convertArr(List<DatabaseTableRow> tableRow) {
        if (tableRow == null) {
            return null;
        }
        return tableRow.stream()
                .map(r -> new String[]{r.getDesc(), r.getColumnName(), r.getDataType(), r.getDataLen(), r.getMust(), r.getDesc()})
                .toArray(String[][]::new);
    }


    private static void addTable(Section section, String[][] data) {
        if (data == null) {
            return;
        }
        //Add a table
        Table table = section.addTable();
        table.resetCells(data.length + 1, header.length);
        table.applyStyle(DefaultTableStyle.Colorful_List);
        table.getTableFormat().getBorders().setBorderType(BorderStyle.Single);

        //将第一行设置为表头并添加数据
        TableRow row = table.getRows().get(0);
        row.isHeader(true);
        row.setHeight(20);
        row.setHeightType(TableRowHeightType.Exactly);
        row.getRowFormat().setBackColor(Color.lightGray);
        for (int i = 0; i < header.length; i++) {
            row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
            Paragraph p = row.getCells().get(i).addParagraph();
            p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
            TextRange range1 = p.appendText(header[i]);
            range1.getCharacterFormat().setFontName("微软雅黑");
            range1.getCharacterFormat().setFontSize(12f);
            range1.getCharacterFormat().setBold(true);
        }

        //向其余行添加数据
        for (int r = 0; r < data.length; r++) {
            TableRow dataRow = table.getRows().get(r + 1);
            dataRow.setHeight(25);
            dataRow.setHeightType(TableRowHeightType.Exactly);
            dataRow.getRowFormat().setBackColor(Color.white);
            for (int c = 0; c < data[r].length; c++) {
                dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                TextRange range2 = dataRow.getCells().get(c).addParagraph().appendText(data[r][c]);
                range2.getCharacterFormat().setFontName("JetBrains Mono");
                range2.getCharacterFormat().setFontSize(10f);
            }
        }
    }

    /**
     * 将文件中的sql语句以；为单位读取到列表中
     *
     * @param sqlPath /
     * @return /
     * @throws Exception e
     */
    public static List<DatabaseTable> readSqlFile(String sqlPath) throws Exception {
        File file = new File(sqlPath);
        List<DatabaseTable> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String tmp;
            DatabaseTable databaseTable = null;
            while ((tmp = reader.readLine()) != null) {
                //英文表名
                String tableStart = "CREATE TABLE `*.*`";
                if (Pattern.compile(tableStart).matcher(tmp).find()) {
                    databaseTable = new DatabaseTable();
                    databaseTable.setEngTableName(getTableEngName(tmp));
                }
                //中间行
                String rowRegex = "^[\\u0020]{2}.+`[\\u0020](tinyint|smallint|mediumint|int|integer|bigint|float|double|decimal|date|time|year|datetime|timestamp|char|varchar|tinyblob|tinytext|blob|text|mediumblob|mediumtext|longblob|longtext).*,$";
                if (Pattern.compile(rowRegex).matcher(tmp).find()) {
                    assert databaseTable != null;
                    databaseTable.setTableRow(resolveRow(databaseTable, tmp));
                }
                //中文表名
                String tableEnd = "\\)\\sENGINE=";
                if (Pattern.compile(tableEnd).matcher(tmp).find()) {
                    if (databaseTable != null) {
                        databaseTable.setChinTableName(getTableChinName(tmp));
                        list.add(databaseTable);
                    }
                }
            }
        }
        return list;
    }

    private static String getTableChinName(String tmp) {
        Matcher matcher = CHIN_NAME_REGEX.matcher(tmp);
        if (matcher.find()) {
            return matcher.group().replace("'", "");
        }
        return null;
    }

    /**
     * 解析每一行 字段
     *
     * @param databaseTable 表对象
     * @param tmp           当前行
     */
    private static List<DatabaseTableRow> resolveRow(DatabaseTable databaseTable, String tmp) {
        if (databaseTable != null) {
            //获取类型
            List<DatabaseTableRow> tableRow = databaseTable.getTableRow();
            tableRow = checkTableRow(tableRow);
            DatabaseTableRow row = new DatabaseTableRow();
            //字段名
            Matcher matcher = COLUMN_START_REGEX.matcher(tmp);
            if (matcher.find()) {
                String group = matcher.group();
                row.setColumnName(group.replace("`", "").trim());
            }
            //字段类型
            Matcher matcher1 = TYPE_REGEX.matcher(tmp);
            if (matcher1.find()) {
                row.setDataType(matcher1.group().replace("`", "").replace("(", "").trim());
            }
            //数据类型长度
            Matcher matcher2 = COLUMN_LEN_REGEX.matcher(tmp);
            if (matcher2.find()) {
                String group = matcher2.group();
                row.setDataLen(group.substring(group.indexOf("(") + 1, group.indexOf(")")));
            }
            //是否必填
            if (tmp.contains(MUST)) {
                row.setMust("是");
            }
            //注释
            Matcher matcher3 = DESC_REGEX.matcher(tmp);
            if (matcher3.find()) {
                row.setDesc(matcher3.group().replace("'", "").replace(",", ""));
            }
            tableRow.add(row);
            return tableRow;
        }
        return null;
    }

    private static List<DatabaseTableRow> checkTableRow(List<DatabaseTableRow> tableRows) {
        if (tableRows == null) {
            tableRows = new ArrayList<>();
        }
        return tableRows;
    }


    /**
     * 从行中获取表英文名
     *
     * @param tmp CREATE TABLE `tableName`  (
     * @return 返回tableName
     */
    private static String getTableEngName(String tmp) {
        Matcher matcher = ENG_NAME_REGEX.matcher(tmp);
        if (matcher.find()) {
            return matcher.group().replace("`", "");
        }
        return null;
    }


    static class DatabaseTable {
        /**
         * 英文表名
         */
        private String engTableName;
        /**
         * 中文表名(表注释)
         */
        private String chinTableName;
        /**
         * 表字段行
         */
        private List<DatabaseTableRow> tableRow;

        public String getEngTableName() {
            return engTableName;
        }

        public void setEngTableName(String engTableName) {
            this.engTableName = engTableName;
        }

        public String getChinTableName() {
            return chinTableName;
        }

        public void setChinTableName(String chinTableName) {
            this.chinTableName = chinTableName;
        }

        public List<DatabaseTableRow> getTableRow() {
            return tableRow;
        }

        public void setTableRow(List<DatabaseTableRow> tableRow) {
            this.tableRow = tableRow;
        }

        @Override
        public String toString() {
            return "DatabaseTable{" +
                    "engTableName='" + engTableName + '\'' +
                    ", chinTableName='" + chinTableName + '\'' +
                    ", tableRow=" + tableRow +
                    '}';
        }
    }


    static class DatabaseTableRow {
        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getDataLen() {
            return dataLen;
        }

        public void setDataLen(String dataLen) {
            this.dataLen = dataLen;
        }

        public String getMust() {
            return must;
        }

        public void setMust(String must) {
            this.must = must;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 字段名
         */
        private String columnName;
        /**
         * 数据类型
         */
        private String dataType;
        /**
         * 数据长度
         */
        private String dataLen = "0";
        /**
         * 是否必填
         */
        private String must = "否";
        /**
         * 字段注释
         */
        private String desc;
    }

}
