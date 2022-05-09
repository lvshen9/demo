package com.lvshen.demo.authenticatedstreams.praram;

import com.lvshen.demo.authenticatedstreams.entity.ProcessConfig;
import com.lvshen.demo.authenticatedstreams.entity.ProcessNode;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-5-9 11:10
 * @since JDK 1.8
 */
@Data
public class ProcessParam extends ProcessConfig {
    private List<ProcessNode> processNodes;
}
