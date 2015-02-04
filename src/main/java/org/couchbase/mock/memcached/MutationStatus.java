package org.couchbase.mock.memcached;

import org.couchbase.mock.memcached.protocol.ErrorCode;

/**
 * @author Mark Nunberg
 *
 * This class encapsulates the coordinates and status of a mutation operation.
 * It is intended to be used as a simple container type for the command executors.
 */
public class MutationStatus {
    private final ErrorCode ec;
    private final VBucketCoordinates vbCoords;

    /**
     * Create a successful status object.
     * @param coords The coordinates reflecting the mutation
     */
    public MutationStatus(VBucketCoordinates coords) {
        ec = ErrorCode.SUCCESS;
        vbCoords = coords;
    }

    /**
     * Create a failed status object
     * @param code The failure status.
     */
    public MutationStatus(ErrorCode code) {
        ec = code;
        vbCoords = new BasicVBucketCoordinates(0, 0);
    }

    public ErrorCode getStatus() {
        return ec;
    }
    public VBucketCoordinates getCoords() {
        return vbCoords;
    }
}
