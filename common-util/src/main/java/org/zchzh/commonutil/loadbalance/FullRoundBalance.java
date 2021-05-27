package org.zchzh.commonutil.loadbalance;


import java.util.List;

/**
 * @author zengchzh
 * @date 2021/5/23
 */

public class FullRoundBalance implements LoadBalance {

    private volatile int index;

    @Override
    public LoadBalanceDTO get(List<LoadBalanceDTO> dtoList) {
        if (index == dtoList.size()) {
            index = 0;
        }
        return dtoList.get(index);
    }
}
