package org.zchzh.commonutil.discovery.convert;

import com.alibaba.nacos.api.naming.pojo.Instance;
import org.zchzh.commonutil.discovery.DiscoveryDTO;

/**
 * @author zengchzh
 * @date 2021/5/21
 */
public class NacosConvert {

    public static final String SERVER_NAME = "serverName";

    private static final String CUT = "@@";

    public static DiscoveryDTO toDto(Instance instance) {

        String serverName = getServerName(instance);
        return DiscoveryDTO.builder()
                .serverName(serverName)
                .ip(instance.getIp())
                .port(instance.getPort())
                .meta(instance.getMetadata())
                .build();
    }

    public static Instance toInstance(DiscoveryDTO dto) {
        Instance instance = new Instance();
        instance.setIp(dto.getIp());
        instance.setPort(dto.getPort());
        instance.setMetadata(dto.getMeta());
        instance.getMetadata().put(SERVER_NAME, dto.getServerName());
        return instance;
    }

    public static String getServerName(Instance instance) {
        if (instance.getServiceName() == null) {
            return null;
        }
        return instance.getServiceName().split(CUT)[1];
    }
}
