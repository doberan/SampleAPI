package jp.doberan.sampleapiclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jp.doberan.sampleapiserver.IAPIService;


public class SampleApiActivity extends Activity {
    private IAPIService mService;
    private Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IAPIService.class.getName());
                intent.setPackage("jp.doberan.sampleapiserver");
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                mButton.setEnabled(false);
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAPIService.Stub.asInterface(service);
            mButton.setEnabled(true);
            try {
                mService.logNumber(12);
            } catch (RemoteException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mButton.setEnabled(false);
            mService = null;
        }
    };
}
