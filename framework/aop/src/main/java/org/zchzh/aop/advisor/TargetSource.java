package org.zchzh.aop.advisor;

import lombok.Data;

/**
 * @author zengchzh
 * @date 2021/10/18
 */

@Data
public class TargetSource {

    private Class<?> tagetClass;

    private Object tagetObject;
}
