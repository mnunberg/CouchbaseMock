package org.couchbase.mock.memcached;

/**
 * @author Mark Nunberg
 *
 * Simple class encapsulating an immutable, static, set of coordinates.
 */
public class BasicVBucketCoordinates implements VBucketCoordinates {
    final private long uuid;
    final private long seqno;

    public BasicVBucketCoordinates(long uuid, long seqno) {
        this.uuid = uuid;
        this.seqno = seqno;
    }

    @Override
    public long getSeqno() {
        return seqno;
    }

    @Override
    public long getUuid() {
        return uuid;
    }
}
