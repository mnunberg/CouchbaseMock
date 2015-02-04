package org.couchbase.mock.memcached;

import java.nio.ByteBuffer;

/**
 * @author Mark Nunberg
 *
 * Utility class to optionally write mutation information to response packets.
 *
 * This is tied to a single connection, and will only write such information if the
 * client had sent a HELLO packet requesting this feature.
 */
public class MutationInfoWriter {
    private boolean enabled = false;

    /**
     * Get the length of extras required for mutation information.
     * @return The length of extras (0 if disabled)
     */
    public short extrasLength() {
        if (enabled) {
            return 16;
        } else {
            return 0;
        }
    }

    /**
     * Write the appropriate mutation information into the output buffers.
     * This method will do nothing if extra mutation information is not enabled.
     * @param bb The output buffer
     * @param coords The coordinates to write
     */
    public void write(ByteBuffer bb, VBucketCoordinates coords) {
        if (!enabled) {
            return;
        }
        bb.putLong(24, coords.getUuid());
        bb.putLong(32, coords.getSeqno());

    }

    /**
     * Sets the enabled flag
     * @param val Whether this is enabled
     * This should only be called from {@link org.couchbase.mock.memcached.MemcachedConnection}.
     */
    void setEnabled(boolean val) {
        enabled = val;
    }

}
