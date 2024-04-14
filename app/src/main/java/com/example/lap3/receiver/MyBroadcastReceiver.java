package com.example.lap3.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = (level / (float) scale) * 100;

                if (batteryPct < 20) {
                    // Hiển thị cảnh báo pin yếu
                    Toast.makeText(context, "Pin yếu! Nạp pin ngay.", Toast.LENGTH_LONG).show();
                }
            } else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                // Kiểm tra kết nối internet và hiển thị cảnh báo tương ứng
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connManager != null) {
                    boolean isConnected = connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isConnected();
                    if (isConnected) {
                        Toast.makeText(context, "Có mạng rồi!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Mất mạng rồi!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}
