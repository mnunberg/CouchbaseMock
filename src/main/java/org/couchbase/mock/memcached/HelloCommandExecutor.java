package org.couchbase.mock.memcached;

import org.couchbase.mock.Bucket;
import org.couchbase.mock.memcached.protocol.*;

/**
 * Created by mnunberg on 2/4/15.
 */
public class HelloCommandExecutor implements CommandExecutor {
    @Override
    public void execute(BinaryCommand cmd, MemcachedServer server, MemcachedConnection client) {
        if (server.getBucket().getType() != Bucket.BucketType.COUCHBASE) {
            client.sendResponse(new BinaryResponse(cmd, ErrorCode.NOT_SUPPORTED));
        }

        BinaryHelloCommand hcmd = (BinaryHelloCommand) cmd;
        // Get the features
        client.setSupportedFeatures(hcmd.getFeatures());
        boolean[] featuresSparse = client.getSupportedFeatures();
        // Get length of required array
        int numFeatures = 0;
        for (boolean b : featuresSparse) {
            if (b) {
                numFeatures++;
            }
        }
        int outIndex = 0;
        int[] featuresArray = new int[numFeatures];
        for (int i = 0; i < featuresSparse.length; i++) {
            if (featuresSparse[i]) {
                featuresArray[outIndex++] = i;
            }
        }
        client.sendResponse(new BinaryHelloResponse(hcmd, featuresArray));
    }
}
