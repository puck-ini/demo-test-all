package org.zchzh.commonutil.discovery;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/5/27
 */
public interface DiscoveryService {
    /**
     * 获取所有服务信息
     * @return 返回服务信息列表
     */
    List<DiscoveryDTO> getInstances();

    /**
     * 根据服务命获取改服务下的所有服务信息
     * @param serviceName 服务名
     * @return 返回服务信息列表
     */
    List<DiscoveryDTO> getInstances(String serviceName);

    /**
     * 注册服务信息
     * @param dto 服务信息
     */
    void register(DiscoveryDTO dto);

    /**
     * 更新服务信息
     * @param dto 服务信息
     */
    void update(DiscoveryDTO dto);

    /**
     * 更新列表中的服务信息
     * @param dtoList 服务信息列表
     */
    void update(List<DiscoveryDTO> dtoList);

    /**
     * 注销服务信息
     * @param dto 服务信息
     */
    void remove(DiscoveryDTO dto);

    /**
     * 注销列表中的服务信息
     * @param dtoList 服务信息列表
     */
    void remove(List<DiscoveryDTO> dtoList);


}
