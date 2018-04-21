package com.webgame.client.ws;

public abstract class JsonWebSocket extends AbstractWebSocketAdapter {
    @Override
    public void send(final String message) {
        sendPacket(message);
    }

    @Override
    public void send(Object message) {
        sendPacket(message);
    }

    @Override
    public void send(final String[] message) {
        sendPacket(message);
    }

    private void sendPacket(final Object packet) {
        final long start = System.currentTimeMillis();
        final byte[] serialized = getSocket().getSerializer().serialize(packet);
        final long time = System.currentTimeMillis() - start;
        //Gdx.app.log("JSON", "Serialized packet in " + time + " millies.");
        getSocket().send(serialized);
    }
}
