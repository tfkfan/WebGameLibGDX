package com.webgame.server_app.utils;

import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.common.server.entities.Player;
import com.webgame.common.server.entities.Skill;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

import java.util.Collection;
import java.util.UUID;

public class ServerUtils {
    private static int second = 1000;
    private static int minute = second * 60;
    protected static final float dl = 0.15f;

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

    public static void getPlayerDamaged(Player damagedPlayer, Skill skill) {
        Integer damage = skill.getDamage();
        if (damagedPlayer.getHealthPoints() > 0)
            damagedPlayer.setHealthPoints(damagedPlayer.getHealthPoints() - damage);
        else
            damagedPlayer.setHealthPoints(0);
    }

    public static boolean isCollided(Vector2 skillPos, Vector2 target) {
        if (skillPos.x >= target.x - dl && skillPos.x <= target.x + dl &&
                skillPos.y >= target.y - dl && skillPos.y <= target.y + dl)
            return true;
        return false;
    }

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }

    public static long calcTime(int seconds, int minutes) {
        return (long) (seconds * second + minutes * minute);
    }
}
