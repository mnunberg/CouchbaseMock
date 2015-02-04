package org.couchbase.mock.memcached;

import java.util.concurrent.atomic.AtomicLong;

public class StorageVBucketCoordinates implements VBucketCoordinates {
    private AtomicLong seqno;
    private final long uuid;

    @Override
    public long getSeqno() {
        return seqno.get();
    }

    public long incrSeqno() {
        return seqno.incrementAndGet();
    }

    @Override
    public long getUuid() {
        return uuid;
    }

    void seekSeqno(long at) {
        seqno.set(at);
    }

    public StorageVBucketCoordinates(long id) {
        seqno = new AtomicLong();
        uuid = id;
    }

    public StorageVBucketCoordinates(VBucketCoordinates other) {
        seqno = new AtomicLong(other.getSeqno());
        uuid = other.getUuid();
    }
}
