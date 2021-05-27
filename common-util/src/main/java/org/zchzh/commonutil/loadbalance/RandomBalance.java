package org.zchzh.commonutil.loadbalance;



import java.util.List;
import java.util.Random;

/**
 * @author zengchzh
 * @date 2021/5/23
 */

public class RandomBalance implements LoadBalance{

    private static final Random RANDOM = new Random();

    @Override
    public LoadBalanceDTO get(List<LoadBalanceDTO> dtoList) {
        return dtoList.get(RANDOM.nextInt(dtoList.size()));
    }
}
