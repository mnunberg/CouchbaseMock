package org.couchbase.mock.memcached;

/**
 * @author Mark Nunberg
 *
 * Interface representing a state of a vBucket. This may be considered to be
 * the 'coordinates' of a given mutation.
 */
public interface VBucketCoordinates {
    public long getSeqno();
    public long getUuid();
}
