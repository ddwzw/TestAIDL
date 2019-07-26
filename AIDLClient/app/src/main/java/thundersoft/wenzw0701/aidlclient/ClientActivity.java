package thundersoft.wenzw0701.aidlclient;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import thundersoft.wenzw0701.aidlserver.PrintManager;

public class ClientActivity extends Activity {

    private PrintManager printManager = null;
    private static final String TAG = "ClientActivity";
    private boolean isConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Button button = findViewById(R.id.button_print);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printString("print request from client");
            }
        });
    }

    public void printString(String string) {
        if (!isConnect) {
            attemptToBindService();
            return;
        }

        if (printManager == null) {
            Log.e(TAG, "printManager is null");
            return;
        }

        try {
            printManager.mPrint(string);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        if (!isConnect) {
            attemptToBindService();
        }
    }

    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("thundersoft.wenzw0701.aidlserver");
        intent.setPackage("thundersoft.wenzw0701.aidlserver");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            printManager = PrintManager.Stub.asInterface(iBinder);
            isConnect = true;
            Log.e(TAG, "serviceConnection");

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };





}
