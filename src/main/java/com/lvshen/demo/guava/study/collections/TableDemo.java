package com.lvshen.demo.guava.study.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Set;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 11:03
 * @since JDK 1.8
 */
public class TableDemo {
    @Test
    public void testTable() {
        Table<String, Integer, String> table = HashBasedTable.create();

        for (char a = 'A'; a <= 'C'; ++a) {
            for (Integer b = 1; b <= 3; ++b) {
                table.put(Character.toString(a), b, String.format("%c%d", a, b));
            }
        }

        System.out.println(table);

        // 表格的第2列
        System.out.println("表格的第2列:" + table.column(2));

        // 表格的B行
        System.out.println("表格的B行:" + table.row("B"));

        // 表格的B行2列
        System.out.println("表格的B行2列:" + table.get("B", 2));

        System.out.println(table.contains("D", 1));// D1: false
        System.out.println(table.containsColumn(3));// true
        System.out.println(table.containsRow("C"));// true

        // 取表格的列，转成Map
        System.out.println("取表格的列，转成Map:" + table.columnMap());
        // 取表格的行，转成Map
        System.out.println("取表格的行，转成Map:" + table.rowMap());

        // 遍历表格
        Set<Table.Cell<String,Integer,String>> cellSet = table.cellSet();
        for (Table.Cell<String, Integer, String> cell : cellSet) {
            System.out.println("行"+cell.getRowKey() + ",列" + cell.getColumnKey() + " --> "+ cell.getValue());
        }

        System.out.println(table.remove("B", 3));


    }
}
