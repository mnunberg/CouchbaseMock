package org.couchbase.mock.memcached.protocol;

import org.couchbase.mock.control.MockCommand;
import org.couchbase.mock.memcached.client.MemcachedClient;

/**
 * Created by mnunberg on 2/4/15.
 */
public class BinaryHelloResponse extends BinaryResponse {
    public BinaryHelloResponse(BinaryHelloCommand cmd, int[] supported) {
        super(cmd, ErrorCode.SUCCESS, 0, 0, supported.length * 2, 0);
        for (int i = 0; i < supported.length; i++) {
            buffer.putShort(24 + (i * 2), (short)supported[i]);
        }
    }
}
