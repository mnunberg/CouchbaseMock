package org.couchbase.mock.memcached.protocol;

import java.net.ProtocolException;
import java.nio.ByteBuffer;

/**
 * Created by mnunberg on 2/4/15.
 */
public class BinaryObserveSeqnoCommand extends BinaryCommand {
    public BinaryObserveSeqnoCommand(ByteBuffer header) throws ProtocolException {
        super(header);
    }

    private long uuid = 0;

    @Override
    public void process() throws ProtocolException {
        super.process();
        bodyBuffer.position(0);
        uuid = bodyBuffer.getLong();
    }

    public long getUuid() {
        return uuid;
    }
}
