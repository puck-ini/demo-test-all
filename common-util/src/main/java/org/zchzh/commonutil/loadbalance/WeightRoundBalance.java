package org.zchzh.commonutil.loadbalance;



import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchzh
 * @date 2021/5/23
 */

public class WeightRoundBalance implements LoadBalance{

    private volatile AtomicInteger index;

    @Override
    public LoadBalanceDTO get(List<LoadBalanceDTO> dtoList) {
        int allWeight = dtoList.stream().mapToInt(LoadBalanceDTO::getWeight).sum();
        int number = (index.getAndIncrement()) % allWeight;
        for (LoadBalanceDTO dto : dtoList) {
            if (dto.getWeight() > number) {
                return dto;
            }
            number -= dto.getWeight();
        }
        return null;
    }
}
