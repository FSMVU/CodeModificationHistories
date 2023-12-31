//Group client projects under :client ...

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

import java.io.IOException;

/**
 * Exception thrown when an elasticsearch node responds to a request with a status code that indicates an error.
 * Holds the response that was returned.
 * Note that the response body gets passed in as a string and read eagerly, which means that the Response object
 * is expected to be closed and available only to read metadata like status line, request line, response headers.
 */
public class ResponseException extends IOException {

    private Response response;
    private final String responseBody;

    ResponseException(Response response) throws IOException {
        super(buildMessage(response));
    ResponseException(Response response, String responseBody) throws IOException {
        super(buildMessage(response,responseBody));
        this.response = response;
        this.responseBody = responseBody;
    }

    private static String buildMessage(Response response) {
        return response.getRequestLine().getMethod() + " " + response.getHost() + response.getRequestLine().getUri()
    private static String buildMessage(Response response, String responseBody) {
        String message = response.getRequestLine().getMethod() + " " + response.getHost() + response.getRequestLine().getUri()
                + ": " + response.getStatusLine().toString();
        if (responseBody != null) {
            message += "\n" + responseBody;
        }
        return message;
    }

    /**
     * Returns the {@link Response} that caused this exception to be thrown.
     * Expected to be used only to read metadata like status line, request line, response headers. The response body should
     * be retrieved using {@link #getResponseBody()}
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Returns the response body as a string or null if there wasn't any.
     * The body is eagerly consumed when an ResponseException gets created, and its corresponding Response
     * gets closed straightaway so this method is the only way to get back the response body that was returned.
     */
    public String getResponseBody() {
        return responseBody;
    }
}