package com.lvshen.demo.json;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-17 9:18
 * @since JDK 1.8
 */
@Data
public class SurveyConclusionTemplateOptionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 实勘结论选项-标签
     */
    private String label;

    /**
     * 实勘结论选项-标签name
     */
    private String name;

    /**
     * 实勘结论选项-是否只读；true-不能勾选，false-可选
     */
    private boolean readonly;
}
