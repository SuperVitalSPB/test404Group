package ru.supervital.test404group.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ru.supervital.test404group.activity.MainActivity;

import static java.lang.Math.random;

/**
 * Created by Vitaly Oantsa on 24.03.2017.
 */

public class PointsService extends Service {
    private static final String TAG = PointsService.class.getSimpleName();
    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");

        MyRun mr = new MyRun(startId, new Date(9999_9999_99L), Double.valueOf(100));
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {

        int startId;
        Date date;
        Double yVal;

        public MyRun(int startId, Date date, Double yVal) {
            this.date = date;
            this.startId = startId;
            this.yVal = yVal;
            Log.d(TAG, "MyRun#" + startId + " create");
        }

        public void run() {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
            Log.d(TAG, "MyRun#" + startId + " start, date = " + date);
            try {
                // сообщаем о старте задачи
                intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_START);
                sendBroadcast(intent);

                while (true) {
                    intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_WORK)
                            .putExtra(MainActivity.PARAM_TIME, date)
                            .putExtra(MainActivity.PARAM_YVAL, yVal+ Long.valueOf(new Random().nextInt()));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        void stop() {
            Log.d(TAG, "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }


}
