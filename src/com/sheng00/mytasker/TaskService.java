package com.sheng00.mytasker;

import java.util.logging.Logger;
import android.R.bool;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class TaskService extends Service {

	private boolean isDebug = true;
	
	
	private BroadcastReceiver wifistateReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            if(currentNetworkInfo.isConnected()){
            	showTxt("Connected:" + currentNetworkInfo.getTypeName());
            	if(currentNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            		//todo: close mobile
            	}
            }else{
            	showTxt("Not Connected");
            }
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
		showTxt("TaskService onCreate");

		IntentFilter filters = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//		filters.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//		filters.addAction("android.net.wifi.STATE_CHANGE");
		registerReceiver(wifistateReceiver, filters);

		
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		showTxt("TaskService onBind.");
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		showTxt("TaskService onStartCommand");
		
		// If we get killed, after returning from here, restart
		return START_STICKY;
	}
	
	@Override
	public void onTaskRemoved(final Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
		showTxt("TaskService onTaskRemoved");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		unregisterReceiver(batteryReceiver);
//		// showTxt("on destroy");
//		mNM.cancel(NOTIFY_ID);
//		lastLevel = 0;
//		stopForeground(true);
		showTxt("TaskService onDestroy");
		// Restart service in 500 ms
		((AlarmManager) getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC, System.currentTimeMillis() + 500,
				PendingIntent.getService(this, 3, new Intent(this, TaskService.class), 0));
	}
	
	private void showTxt(String string) {
		if (!isDebug) {
			return;
		}
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}
