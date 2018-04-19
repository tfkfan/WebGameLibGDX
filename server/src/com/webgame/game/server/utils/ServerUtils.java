package com.webgame.game.server.utils;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

import java.util.Collection;
import java.util.UUID;

public class ServerUtils {
    private static int second = 1000;
    private static int minute = second*60;

    public static final Vertx vertx = Vertx.vertx();

    private ServerUtils() {
    }

    public static void writeResponse(final ServerWebSocket webSocket, final Object obj, final JsonSerializer jsonSerializer) {
        if (obj == null || webSocket == null)
            return;
        synchronized (webSocket) {
            webSocket.writeFinalBinaryFrame(Buffer.buffer(jsonSerializer.serialize(obj)));
        }
    }

    public static void writeResponseToAll(Collection<ServerWebSocket> sessions, final Object obj, final JsonSerializer jsonSerializer) {
        for (ServerWebSocket session : sessions)
            writeResponse(session, obj, jsonSerializer);
    }

    public static void writeResponseToAllExcept(Collection<ServerWebSocket> sessions, final ServerWebSocket currentSession, final Object obj, final JsonSerializer jsonSerializer) {
        for (ServerWebSocket session : sessions) {
            if (session.equals(currentSession))
                continue;
            writeResponse(session, obj, jsonSerializer);
        }
    }

    public static String newUUID(){
        return UUID.randomUUID().toString();
    }

    public static long calcTime(int seconds, int minutes){
        return (long)(seconds*second + minutes*minute);
    }
}
