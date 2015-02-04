package org.couchbase.mock.memcached.protocol;

import org.couchbase.mock.memcached.VBucketCoordinates;

/**
 * Created by mnunberg on 2/4/15.
 */
public class BinaryObserveSeqnoResponse extends BinaryResponse {
    // Base is:
    // Format   (1)  = 1
    // VBID     (2)  = 3
    // UUID     (8)  = 11
    // SEQ(C)   (8)  = 19
    // SEQ(D)   (8)  = 27

    static final int REPLY_LENGTH_NORMAL = 27;
    static final int REPLY_LENGTH_FAILOVER = REPLY_LENGTH_NORMAL + 16;
    static final int COMMON_OFFSET = 25;

    private void writeInfoCommon(short vbid, long uuid, long seqCache, long seqDisk) {
        buffer.position(COMMON_OFFSET);
        buffer.putShort(vbid);
        buffer.putLong(uuid);
        buffer.putLong(seqDisk);
        buffer.putLong(seqCache);
        buffer.rewind();
    }

    public BinaryObserveSeqnoResponse(BinaryObserveSeqnoCommand cmd, long seqCache, long seqDisk) {
        super(cmd, ErrorCode.SUCCESS, 0, 0, REPLY_LENGTH_NORMAL, 0);
        writeInfoCommon(cmd.getVBucketId(), cmd.getUuid(), seqCache, seqDisk);
        buffer.put(24, (byte)0x00);
        buffer.rewind();
    }

    public BinaryObserveSeqnoResponse(
            BinaryObserveSeqnoCommand cmd, VBucketCoordinates coordCur, VBucketCoordinates coordOld, long seqDisk) {

        super(cmd, ErrorCode.SUCCESS, 0, 0, REPLY_LENGTH_FAILOVER, 0);
        writeInfoCommon(cmd.getVBucketId(), coordCur.getUuid(), coordCur.getSeqno(), seqDisk);
        buffer.putLong(coordOld.getUuid());
        buffer.putLong(coordOld.getSeqno());
        buffer.putLong(24, (byte)0x01);
        buffer.rewind();
    }
}
