package org.zchzh.commonutil.loadbalance;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/5/23
 */
public interface LoadBalance {

    /**
     * 通过负载均衡获取服务dto
     * @param dtoList 服务列表
     * @return 返回服务dto
     */
    LoadBalanceDTO get(List<LoadBalanceDTO> dtoList);
}
