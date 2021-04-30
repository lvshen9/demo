package com.lvshen.demo.annotation.sensitive.ruleenginee;

import com.lvshen.demo.annotation.sensitive.SensitiveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-4-30 16:13
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RuleEntity {
    SensitiveType type;
    String infoStr;
}
