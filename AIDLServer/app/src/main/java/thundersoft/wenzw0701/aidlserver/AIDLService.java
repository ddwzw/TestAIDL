package thundersoft.wenzw0701.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class AIDLService extends Service {

    private static final String TAG = "AIDLService";
    private thundersoft.wenzw0701.aidlserver.PrintManager.Stub printManager = new
            thundersoft.wenzw0701.aidlserver.PrintManager.Stub() {
                @Override
                public void mPrint(String str) throws RemoteException {
                    Log.e(TAG, "this is mPrint: " + str);
                }
            };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "AIDLServer is onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return printManager;
    }
}
