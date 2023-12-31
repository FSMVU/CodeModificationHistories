//HLRC support for string_stats (#52163) ...


/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.client.analytics;

import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryRewriteContext;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregatorFactories.Builder;
import org.elasticsearch.search.aggregations.AggregatorFactory;
import org.elasticsearch.search.aggregations.support.CoreValuesSourceType;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.aggregations.support.ValuesSource;
import org.elasticsearch.search.aggregations.support.ValuesSource.Bytes;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregatorFactory;
import org.elasticsearch.search.aggregations.support.ValuesSourceConfig;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Builds the {@code string_stats} aggregation request.
 * <p>
 * NOTE: This extends {@linkplain AbstractAggregationBuilder} for compatibility
 * with {@link SearchSourceBuilder#aggregation(AggregationBuilder)} but it
 * doesn't support any "server" side things like
 * {@linkplain Writeable#writeTo(StreamOutput)},
 * {@linkplain AggregationBuilder#rewrite(QueryRewriteContext)}, or
 * {@linkplain AbstractAggregationBuilder#build(QueryShardContext, AggregatorFactory)}.
 */
public class StringStatsAggregationBuilder extends ValuesSourceAggregationBuilder<ValuesSource.Bytes, StringStatsAggregationBuilder> {
    public static final String NAME = "string_stats";
    private static final ParseField SHOW_DISTRIBUTION_FIELD = new ParseField("show_distribution");

    private boolean showDistribution = false;

    public StringStatsAggregationBuilder(String name) {
        super(name, CoreValuesSourceType.BYTES, ValueType.STRING);
    }

    /**
     * Compute the distribution of each character. Disabled by default.
     * @return this for chaining
     */
    public StringStatsAggregationBuilder showDistribution(boolean showDistribution) {
        this.showDistribution = showDistribution;
        return this;
    }

    @Override

    public String getType() {
        return NAME;
    }

    @Override
    public XContentBuilder doXContentBody(XContentBuilder builder, Params params) throws IOException {
        return builder.field(StringStatsAggregationBuilder.SHOW_DISTRIBUTION_FIELD.getPreferredName(), showDistribution);
    }

    @Override
    protected void innerWriteTo(StreamOutput out) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ValuesSourceAggregatorFactory<Bytes> innerBuild(QueryShardContext queryShardContext, ValuesSourceConfig<Bytes> config,
            AggregatorFactory parent, Builder subFactoriesBuilder) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected AggregationBuilder shallowCopy(Builder factoriesBuilder, Map<String, Object> metaData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), showDistribution);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (false == super.equals(obj)) {
            return false;
        }
        StringStatsAggregationBuilder other = (StringStatsAggregationBuilder) obj;
        return showDistribution == other.showDistribution;
    }
}