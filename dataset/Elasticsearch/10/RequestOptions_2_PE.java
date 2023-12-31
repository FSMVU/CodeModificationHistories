//Node selector per client rather than per request (#31471) ...

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

package org.elasticsearch.client;

import org.apache.http.message.BasicHeader;
import org.apache.http.Header;
import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


import java.util.ArrayList;

/**
 * The portion of an HTTP request to Elasticsearch that can be
 * manipulated without changing Elasticsearch's behavior.
 */
public final class RequestOptions {
    /**
     * Default request options.
     */
    public static final RequestOptions DEFAULT = new Builder(
            Collections.<Header>emptyList(), HeapBufferedResponseConsumerFactory.DEFAULT, null).build();
            Collections.<Header>emptyList(), HeapBufferedResponseConsumerFactory.DEFAULT).build();
        Collections.<Header>emptyList(), NodeSelector.ANY,
        HeapBufferedResponseConsumerFactory.DEFAULT).build();

    private final List<Header> headers;
    private final NodeSelector nodeSelector;
    private final HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory;
    private final WarningsHandler warningsHandler;

    private RequestOptions(Builder builder) {
        this.headers = Collections.unmodifiableList(new ArrayList<>(builder.headers));
        this.nodeSelector = builder.nodeSelector;
        this.httpAsyncResponseConsumerFactory = builder.httpAsyncResponseConsumerFactory;
        this.warningsHandler = builder.warningsHandler;
    }

    /**
     * Create a builder that contains these options but can be modified.
     */
    public Builder toBuilder() {
        return new Builder(headers, httpAsyncResponseConsumerFactory, warningsHandler);
        return new Builder(headers, httpAsyncResponseConsumerFactory);
        Builder builder = new Builder(headers, nodeSelector, httpAsyncResponseConsumerFactory);
        return builder;
    }

    /**
     * Headers to attach to the request.
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * The selector that chooses which nodes are valid destinations for
     * {@link Request}s with these options.
     */
    public NodeSelector getNodeSelector() {
        return nodeSelector;
    }

    /**
     * The {@link HttpAsyncResponseConsumerFactory} used to create one
     * {@link HttpAsyncResponseConsumer} callback per retry. Controls how the
     * response body gets streamed from a non-blocking HTTP connection on the
     * client side.
     */
    public HttpAsyncResponseConsumerFactory getHttpAsyncResponseConsumerFactory() {
        return httpAsyncResponseConsumerFactory;
    }

    /**
     * How this request should handle warnings. If null (the default) then
     * this request will default to the behavior dictacted by
     * {@link RestClientBuilder#setStrictDeprecationMode}.
     * <p>
     * This can be set to {@link WarningsHandler#PERMISSIVE} if the client
     * should ignore all warnings which is the same behavior as setting
     * strictDeprecationMode to true. It can be set to
     * {@link WarningsHandler#STRICT} if the client should fail if there are
     * any warnings which is the same behavior as settings
     * strictDeprecationMode to false.
     * <p>
     * It can also be set to a custom implementation of
     * {@linkplain WarningsHandler} to permit only certain warnings or to
     * fail the request if the warnings returned don't
     * <strong>exactly</strong> match some set.
     */
    public WarningsHandler getWarningsHandler() {
        return warningsHandler;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("RequestOptions{");
        boolean comma = false;
        if (headers.size() > 0) {
            b.append("headers=");
            comma = true;
            b.append(", headers=");
            for (int h = 0; h < headers.size(); h++) {
                if (h != 0) {
                    b.append(',');
                }
                b.append(headers.get(h).toString());
            }
        }
        if (nodeSelector != NodeSelector.ANY) {
            b.append(", nodeSelector=").append(nodeSelector);
        }
        if (httpAsyncResponseConsumerFactory != HttpAsyncResponseConsumerFactory.DEFAULT) {
            if (comma) b.append(", ");
            comma = true;
            b.append("consumerFactory=").append(httpAsyncResponseConsumerFactory);
            b.append(", consumerFactory=").append(httpAsyncResponseConsumerFactory);
        }
        if (warningsHandler != null) {
            if (comma) b.append(", ");
            comma = true;
            b.append("warningsHandler=").append(warningsHandler);
        }
        return b.append('}').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        RequestOptions other = (RequestOptions) obj;
        return headers.equals(other.headers)
                && nodeSelector.equals(other.nodeSelector)
                && httpAsyncResponseConsumerFactory.equals(other.httpAsyncResponseConsumerFactory)
                && Objects.equals(warningsHandler, other.warningsHandler);
                && httpAsyncResponseConsumerFactory.equals(other.httpAsyncResponseConsumerFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, httpAsyncResponseConsumerFactory, warningsHandler);
        return Objects.hash(headers, httpAsyncResponseConsumerFactory);
        return Objects.hash(headers, nodeSelector, httpAsyncResponseConsumerFactory);
    }

    /**
     * Builds {@link RequestOptions}. Get one by calling
     * {@link RequestOptions#toBuilder} on {@link RequestOptions#DEFAULT} or
     * any other {@linkplain RequestOptions}.
     */
    public static class Builder {
        private final List<Header> headers;
        private NodeSelector nodeSelector;
        private HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory;
        private WarningsHandler warningsHandler;

        private Builder(List<Header> headers, HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory,
                WarningsHandler warningsHandler) {
        private Builder(List<Header> headers, HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory) {
        private Builder(List<Header> headers, NodeSelector nodeSelector,
                HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory) {
            this.headers = new ArrayList<>(headers);
            this.nodeSelector = nodeSelector;
            this.httpAsyncResponseConsumerFactory = httpAsyncResponseConsumerFactory;
            this.warningsHandler = warningsHandler;
        }

        /**
         * Build the {@linkplain RequestOptions}.
         */
        public RequestOptions build() {
            return new RequestOptions(this);
        }

        /**
         * Add the provided header to the request.
         */
        public void addHeader(String name, String value) {
            Objects.requireNonNull(name, "header name cannot be null");
            Objects.requireNonNull(value, "header value cannot be null");
            this.headers.add(new ReqHeader(name, value));
        }

        /**
         * Configure the selector that chooses which nodes are valid
         * destinations for {@link Request}s with these options
         */
        public void setNodeSelector(NodeSelector nodeSelector) {
            this.nodeSelector = Objects.requireNonNull(nodeSelector, "nodeSelector cannot be null");
        }

        /**
         * Set the {@link HttpAsyncResponseConsumerFactory} used to create one
         * {@link HttpAsyncResponseConsumer} callback per retry. Controls how the
         * response body gets streamed from a non-blocking HTTP connection on the
         * client side.
         */
        public void setHttpAsyncResponseConsumerFactory(HttpAsyncResponseConsumerFactory httpAsyncResponseConsumerFactory) {
            this.httpAsyncResponseConsumerFactory =
                    Objects.requireNonNull(httpAsyncResponseConsumerFactory, "httpAsyncResponseConsumerFactory cannot be null");
        }

        /**
         * How this request should handle warnings. If null (the default) then
         * this request will default to the behavior dictacted by
         * {@link RestClientBuilder#setStrictDeprecationMode}.
         * <p>
         * This can be set to {@link WarningsHandler#PERMISSIVE} if the client
         * should ignore all warnings which is the same behavior as setting
         * strictDeprecationMode to true. It can be set to
         * {@link WarningsHandler#STRICT} if the client should fail if there are
         * any warnings which is the same behavior as settings
         * strictDeprecationMode to false.
         * <p>
         * It can also be set to a custom implementation of
         * {@linkplain WarningsHandler} to permit only certain warnings or to
         * fail the request if the warnings returned don't
         * <strong>exactly</strong> match some set.
         */
        public void setWarningsHandler(WarningsHandler warningsHandler) {
            this.warningsHandler = warningsHandler;
        }
    }

    /**
     * Custom implementation of {@link BasicHeader} that overrides equals and
     * hashCode so it is easier to test equality of {@link RequestOptions}.
     */
    static final class ReqHeader extends BasicHeader {

        ReqHeader(String name, String value) {
            super(name, value);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof ReqHeader) {
                Header otherHeader = (Header) other;
                return Objects.equals(getName(), otherHeader.getName()) &&
                        Objects.equals(getValue(), otherHeader.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getValue());
        }
    }
}