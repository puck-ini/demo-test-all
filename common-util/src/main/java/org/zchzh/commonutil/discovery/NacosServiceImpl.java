package org.zchzh.commonutil.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import lombok.extern.slf4j.Slf4j;
import org.zchzh.commonutil.discovery.convert.NacosConvert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/5/21
 */
@Slf4j
//@AutoService(value = RegisterCenter.class)
//@RegisterSPI(Constants.NACOS)
public class NacosServiceImpl implements DiscoveryService {

    private NamingService namingService;

    public NacosServiceImpl(String address) {
        try {
            namingService = NamingFactory.createNamingService(address);
        } catch (NacosException e) {
            log.error(e.getErrMsg());
        }

    }

    @Override
    public List<DiscoveryDTO> getInstances() {
        List<DiscoveryDTO> dtoList = new ArrayList<>();
        try {
            ListView<String> services = namingService.getServicesOfServer(1, Integer.MAX_VALUE);
            for (String serviceName : services.getData()) {
                dtoList.addAll(getInstances(serviceName));
            }
        } catch (NacosException e) {
            log.error(e.getErrMsg());
        }
        return dtoList;
    }

    @Override
    public List<DiscoveryDTO> getInstances(String serviceName) {
        List<DiscoveryDTO> dtoList = new ArrayList<>();
        try {
            List<Instance> instanceList = namingService.getAllInstances(serviceName);
            for (Instance instance : instanceList) {
                dtoList.add(NacosConvert.toDto(instance));
            }
        } catch (NacosException e) {
            log.error(e.getErrMsg());
        }
        return dtoList;
    }

    @Override
    public void register(DiscoveryDTO dto) {
        Instance instance = NacosConvert.toInstance(dto);
        try {
            namingService.registerInstance(instance.getMetadata().get(NacosConvert.SERVER_NAME), instance);
        } catch (NacosException e) {
            log.error(e.getErrMsg());
        }
    }

    @Override
    public void update(DiscoveryDTO dto) {
        this.register(dto);
    }

    @Override
    public void update(List<DiscoveryDTO> dtoList) {
        dtoList.forEach(this::update);
    }

    @Override
    public void remove(DiscoveryDTO dto) {
        Instance instance = NacosConvert.toInstance(dto);
        try {
            namingService.deregisterInstance(
                    instance.getMetadata().get(NacosConvert.SERVER_NAME),
                    instance.getIp(),
                    instance.getPort()
            );
        } catch (NacosException e) {
            log.error(e.getErrMsg());
        }
    }

    @Override
    public void remove(List<DiscoveryDTO> dtoList) {
        dtoList.forEach(this::remove);
    }
}
