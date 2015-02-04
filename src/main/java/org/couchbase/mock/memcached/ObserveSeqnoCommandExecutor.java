package org.couchbase.mock.memcached;

import org.couchbase.mock.memcached.protocol.*;

/**
 * Created by mnunberg on 2/4/15.
 */
public class ObserveSeqnoCommandExecutor implements CommandExecutor {
    @Override
    public void execute(BinaryCommand cmd, MemcachedServer server, MemcachedConnection client) {
        Storage ss = server.getStorage();

        BinaryObserveSeqnoCommand ocmd = (BinaryObserveSeqnoCommand)cmd;
        VBucketStore cacheStore = ss.getCache(ocmd.getVBucketId());

        // Coordinates for the request
        VBucketCoordinates coordRequest = cacheStore.findCoords(ocmd.getVBucketId(), ocmd.getUuid());
        // Most recent coordinates
        VBucketCoordinates coordCurr = cacheStore.getCurrentCoords(ocmd.getVBucketId());

        if (coordRequest == null) {
            // No such coordinates!
            client.sendResponse(new BinaryResponse(cmd, ErrorCode.EINTERNAL));
            return;
        }

        long seqnoDisk = ss.getPersistedSeqno(cmd.getVBucketId());
        long seqnoCache = coordRequest.getSeqno();
        System.err.printf("[%d] MEM=%d. DISK=%d%n", server.getPort(), seqnoCache, seqnoDisk);

        if (coordRequest.getUuid() != coordCurr.getUuid()) {
            // Failover:
            client.sendResponse(new BinaryObserveSeqnoResponse(ocmd, coordCurr, coordRequest, seqnoDisk));
        } else {
            client.sendResponse(new BinaryObserveSeqnoResponse(ocmd, seqnoCache, seqnoDisk));
        }
    }
}
