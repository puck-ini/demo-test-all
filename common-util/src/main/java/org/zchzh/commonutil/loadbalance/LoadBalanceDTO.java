package org.zchzh.commonutil.loadbalance;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zengchzh
 * @date 2021/5/21
 */

@Data
@Builder
public class LoadBalanceDTO implements Serializable {

    private String serverName;

    private String ip;

    private Integer port;

    private String address;

    private Integer weight;

    private Map<String, String> meta;

    public String getAddress() {
        if (address == null || "".equals(address)) {
            address = ip + ":" + port;
        }
        return address;
    }
}
