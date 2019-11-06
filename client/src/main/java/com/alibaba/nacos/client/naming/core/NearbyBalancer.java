package com.alibaba.nacos.client.naming.core;

import com.alibaba.nacos.api.naming.PreservedMetadataKeys;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.alicloud.AliCloudManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Balancer using environment information on alicloud
 *
 * @author nkorange
 * @since 1.1.5
 */
public class NearbyBalancer extends Balancer {

    public static Instance getHostByWeightAndSameAvailableZone(List<Instance> instances) {

        List<Instance> res = filterInstances(instances, AliCloudManager.getAvailableZoneEnvName(), AliCloudManager.getLocalAvailableZoneValue());

        if (res.isEmpty()) {
            res = filterInstances(instances, AliCloudManager.getRegionEnvName(), AliCloudManager.getLocalRegionValue());
        }
        if (res.isEmpty()) {
            res = instances;
        }
        return Balancer.getHostByRandomWeight(res);
    }

    public static Instance getInstanceByWeightAndSameHost(List<Instance> instances) {

        List<Instance> res = filterInstances(instances, AliCloudManager.getHostEnvName(), AliCloudManager.getLocalHostEnvValue());
        if (res.isEmpty()) {
            res = filterInstances(instances, AliCloudManager.getAvailableZoneEnvName(), AliCloudManager.getLocalAvailableZoneValue());
        }
        if (res.isEmpty()) {
            res = filterInstances(instances, AliCloudManager.getRegionEnvName(), AliCloudManager.getLocalRegionValue());
        }
        if (res.isEmpty()) {
            res = instances;
        }
        return Balancer.getHostByRandomWeight(res);
    }

    public static Instance getInstanceByWeightAndSameRegion(List<Instance> instances) {

        List<Instance> res = filterInstances(instances, AliCloudManager.getRegionEnvName(), AliCloudManager.getLocalRegionValue());

        if (res.isEmpty()) {
            res = instances;
        }
        return Balancer.getHostByRandomWeight(res);
    }

    private static List<Instance> filterInstances(List<Instance> instances, String envName, String envValue) {

        String metadataKey = translateEnv2MetadataKey(envName);
        List<Instance> res = new ArrayList<Instance>(16);
        for (Instance instance : instances) {
            if (envValue.equals(instance.getMetadata().get(metadataKey))) {
                res.add(instance);
            }
        }
        return res;
    }

    private static String translateEnv2MetadataKey(String envName) {
        return PreservedMetadataKeys.ENVIRONMENT_ALICLOUD_PREFIX + envName;
    }
}
