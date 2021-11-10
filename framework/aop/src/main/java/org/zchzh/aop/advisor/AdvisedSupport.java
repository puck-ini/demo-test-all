package org.zchzh.aop.advisor;

import lombok.Data;
import org.zchzh.aop.interceptor.AopMethodInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/10/18
 */
@Data
public class AdvisedSupport extends Advisor {

    private TargetSource targetSource;

    private List<AopMethodInterceptor> list = new ArrayList<>();

    public void addAopMethodInterceptor(AopMethodInterceptor interceptor) {
        list.add(interceptor);
    }

    public void addAopMethodInterceptors(List<AopMethodInterceptor> interceptors) {
        list.addAll(interceptors);
    }
}
