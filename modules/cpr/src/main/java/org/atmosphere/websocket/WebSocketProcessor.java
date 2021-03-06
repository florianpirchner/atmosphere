/*
 * Copyright 2012 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.websocket;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.websocket.WebSocketEventListener.WebSocketEvent;

import java.io.IOException;

/**
 * Atmosphere's WebSocket Support implementation. The default behavior is implemented in {@link DefaultWebSocketProcessor}.
 * This class is targeted at framework developer as it requires Atmosphere's internal knowledge.
 * <br/>
 * This class can also be used to implement the JSR 345 recommendation.
 *
 * @author Jeanfrancois Arcand
 */
public interface WebSocketProcessor {
    /**
     * Register a {@link WebSocketHandler}
     *
     * @param path             the URI mapping the WebSocketHandler
     * @param webSockethandler an instance of {@link WebSocketHandler}
     * @return this
     */
    WebSocketProcessor registerWebSocketHandler(String path, WebSocketHandler webSockethandler);

    /**
     * Invoked when a WebSocket gets opened by the underlying container
     *
     * @param request
     * @throws IOException
     */
    void open(WebSocket webSocket, final AtmosphereRequest request) throws IOException;

    /**
     * Invoked when a WebSocket message gets received from the underlying container
     *
     * @param webSocketMessage
     */
    void invokeWebSocketProtocol(WebSocket webSocket, String webSocketMessage);

    /**
     * Invoked when a WebSocket message gets received from the underlying container
     *
     * @param data
     */
    void invokeWebSocketProtocol(WebSocket webSocket, byte[] data, int offset, int length);

    /**
     * Invked when the WebServer is closing the native WebSocket
     *
     * @param closeCode
     */
    public void close(WebSocket webSocket, int closeCode);

    /**
     * Notify all {@link WebSocketEventListener}
     *
     * @param webSocketEvent
     */
    void notifyListener(WebSocket webSocket, WebSocketEvent webSocketEvent);


    /**
     * Destroy all resources associated with this class.
     */
    void destroy();

    /**
     * An exception that can be used to flag problems with the WebSocket processing.
     */
    public final static class WebSocketException extends Exception {

        private final AtmosphereResponse r;

        public WebSocketException(String s, AtmosphereResponse r) {
            super(s);
            this.r = r;
        }

        public WebSocketException(Throwable throwable, AtmosphereResponse r) {
            super(throwable);
            this.r = r;
        }

        public AtmosphereResponse response() {
            return r;
        }
    }
}
