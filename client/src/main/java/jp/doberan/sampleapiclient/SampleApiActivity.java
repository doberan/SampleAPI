package jp.doberan.sampleapiclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jp.doberan.sampleapiserver.IAPIService;


public class SampleApiActivity extends Activity {
    private IAPIService mService;
    private Button mButton;
    private TextView mResultText;
    public static final String API_SERVICE_PACKAGE_NAME = "jp.doberan.sampleapiserver";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mButton == null) {
            mButton = findViewById(R.id.button);
            mButton.setOnClickListener(mButtonClickListener);
        }

        if(mResultText == null) {
            mResultText = findViewById(R.id.result);
        }

        if(mService == null) {
            serviceStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButton = null;
        mResultText = null;
        mService = null;
    }

    Button.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                mService.logNumber(12);
                mService.setNumber(6);
                int num = mService.getNumber();
                mResultText.setText(String.valueOf(num));
            } catch (RemoteException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void serviceStart() {
        if(mButton != null) {
            mButton.setEnabled(false);
        }
        Intent intent = new Intent(IAPIService.class.getName());
        intent.setPackage(API_SERVICE_PACKAGE_NAME);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAPIService.Stub.asInterface(service);
            mButton.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mButton.setEnabled(false);
            mService = null;
        }
    };
}
