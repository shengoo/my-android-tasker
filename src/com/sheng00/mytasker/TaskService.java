package com.sheng00.mytasker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TaskService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "service started.", Toast.LENGTH_LONG).show();
		return null;
	}

}
