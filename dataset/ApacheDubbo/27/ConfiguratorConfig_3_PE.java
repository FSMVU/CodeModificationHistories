

Optimize code for base practice for 3.x (#9830) ...

kevinw66
on Sat May 22 2021

chickenlj
on Mon Dec 17 2018

chickenlj
on Mon Dec 03 2018
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rpc.cluster.configurator.parser.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class ConfiguratorConfig {
    public static final String SCOPE_SERVICE = "service";
    public static final String SCOPE_APPLICATION = "application";
    public static final String CONFIG_VERSION_KEY = "configVersion";
    public static final String SCOPE_KEY = "scope";
    public static final String CONFIG_KEY = "key";
    public static final String ENABLED_KEY = "enabled";
    public static final String CONFIGS_KEY = "configs";

    private String configVersion;
    private String scope;
    private String key;
    private Boolean enabled = true;
    private List<ConfigItem> configs;

    @SuppressWarnings("unchecked")
    public static ConfiguratorConfig parseFromMap(Map<String, Object> map) {
        ConfiguratorConfig configuratorConfig = new ConfiguratorConfig();
        configuratorConfig.setConfigVersion((String) map.get(CONFIG_VERSION_KEY));
        configuratorConfig.setScope((String) map.get(SCOPE_KEY));
        configuratorConfig.setKey((String) map.get(CONFIG_KEY));

        Object enabled = map.get(ENABLED_KEY);
        if (enabled != null) {
            configuratorConfig.setEnabled(Boolean.parseBoolean(enabled.toString()));
        }

        Object configs = map.get(CONFIGS_KEY);
        if (configs != null && List.class.isAssignableFrom(configs.getClass())) {
            configuratorConfig.setConfigs(((List<Map<String, Object>>) configs).stream()
                    .map(ConfigItem::parseFromMap).collect(Collectors.toList()));
        }

        return configuratorConfig;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<ConfigItem> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigItem> configs) {
        this.configs = configs;
    }
}