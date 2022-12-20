package com.lvshen.demo.annotation.easyexport;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 8:35
 * @since JDK 1.8
 */
public class Custemhandler extends AbstractColumnWidthStyleStrategy {
    private static final int MAX_COLUMN_WIDTH = 255;
    //因为在自动列宽的过程中，有些设置地方让列宽显得紧凑，所以做出了个判断
    private static final int COLUMN_WIDTH = 20;
    private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);

    public Custemhandler() {

    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = CACHE.get(writeSheetHolder.getSheetNo());
            if (maxColumnWidthMap == null) {
                maxColumnWidthMap = new HashMap(16);
                CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > MAX_COLUMN_WIDTH) {
                    columnWidth = MAX_COLUMN_WIDTH;
                } else {
                    if (columnWidth < COLUMN_WIDTH) {
                        columnWidth = columnWidth * 2;
                    }
                }

                Integer maxColumnWidth = (Integer) ((Map) maxColumnWidthMap).get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    ((Map) maxColumnWidthMap).put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                }
            }
        }
    }

    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }

}
