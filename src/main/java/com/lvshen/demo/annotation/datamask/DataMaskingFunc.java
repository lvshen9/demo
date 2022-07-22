package com.lvshen.demo.annotation.datamask;

import org.springframework.util.StringUtils;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-7-22 9:15
 * @since JDK 1.8
 */
public enum DataMaskingFunc {
    /**
     *  脱敏转换器
     */
    NO_MASK((str, maskChar) -> str),
    ALL_MASK((str, maskChar) -> {
        if (StringUtils.hasLength(str)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                sb.append(StringUtils.hasLength(maskChar) ? maskChar : DataMaskingOperation.MASK_CHAR);
            }
            return sb.toString();
        } else {
            return str;
        }
    });

    private final DataMaskingOperation operation;

    DataMaskingFunc(DataMaskingOperation operation) {
        this.operation = operation;
    }

    public DataMaskingOperation operation() {
        return this.operation;
    }

}
