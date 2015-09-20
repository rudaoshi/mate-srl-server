package so.knowledge.mate.server;


import org.zeromq.ZMQ;


class Connection {
    private final ZMQ.Context context;
    private final ZMQ.Socket socket;
    private boolean stopRequested = false;

    public Connection(ZMQ.Context context,ZMQ.Socket socket) {
        if(context == null) {
            throw new NullPointerException("context");
        } else if(socket == null) {
            throw new NullPointerException("socket");
        } else {
            this.context = context;
            this.socket = socket;
        }
    }

    public void stop() {
        stopRequested = true;
    }

    public void reply( final byte[] msg) {
        socket.send(msg, 0);
    }

    public boolean isStopRequested() {
        return this.stopRequested;
    }
}
