/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.nacos.client.alicloud;

import java.util.HashMap;
import java.util.Map;

/**
 * Interact with alicloud environment
 *
 * @author nkorange
 * @since 1.1.5
 */
public class AliCloudManager {

    public static String getHostEnvName() {
        return "MACHINE";
    }

    public static String getLocalHostEnvValue() {
        return "host1";
    }

    public static String getAvailableZoneEnvName() {
        return "AZ";
    }

    public static String getLocalAvailableZoneValue() {
        return null;
    }

    public static String getRegionEnvName() {
        return "REGION";
    }

    public static String getLocalRegionValue() {
        return null;
    }

    public static Map<String, String> getEnvironmentInfo() {
        return new HashMap<String, String>(2);
    }
}
