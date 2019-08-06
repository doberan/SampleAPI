package jp.doberan.sampleapiserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * API Service
 */
public class APIService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAPIService;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // API Stub
    private IAPIService.Stub mAPIService = new IAPIService.Stub() {
        private int number = -1;

        /**
         * Display log number
         * @param a display number
         * @return never true
         * @throws RemoteException disconnecting error
         */
        @Override
        public boolean logNumber(int a) throws RemoteException {
            if (a <= -1) { return false; }
            final AtomicInteger res = new AtomicInteger(a);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "number is " + res.get(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        /**
         * Set number
         * @param a setting number
         * @return never true
         * @throws RemoteException disconnecting error
         */
        @Override
        public boolean setNumber(int a) throws RemoteException {
            number = a;
            return true;
        }

        /**
         * Get number.
         * @return number
         * @throws RemoteException disconnecting error
         */
        @Override
        public synchronized int getNumber() throws RemoteException {
            return number;
        }
    };
}
