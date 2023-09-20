//Remove some local only parameters (#8291)

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
package org.apache.dubbo.rpc.cluster;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

import java.util.Map;

@SPI("default")
public interface ProviderURLMergeProcessor {

    /**
     * Merging the URL parameters of provider and consumer
     *
     * @param remoteUrl          providerUrl
     * @param localParametersMap consumer url parameters
     * @return
     */
    URL mergeUrl(URL remoteUrl, Map<String, String> localParametersMap);

    default Map<String, String> mergeLocalParams(Map<String, String> localMap) { return localMap; }

    default boolean accept(URL providerUrl, Map<String, String> localParametersMap) {
        return true;
    }
}