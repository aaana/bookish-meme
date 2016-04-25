package log;

import java.util.TimerTask;

/**
 * Created by tanjingru on 4/25/16.
 */
public class CompressTask extends TimerTask {
    @Override
    public void run() {
        try {
            Log.compress(Log.compressPath);
            Log.resetCompress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
