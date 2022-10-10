package com.example.childsecuritysystem;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Wifi_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);


        TextView wifi = findViewById(R.id.wifi);
        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        StringBuilder sb = new StringBuilder();

        for (ApplicationInfo packageInfo : packages) {

            String name =  packageInfo.packageName;
            long received = android.net.TrafficStats.getUidRxBytes(packageInfo.uid);
            long sent = android.net.TrafficStats.getUidTxBytes(packageInfo.uid);
            sb.append(name+" - "+ "Received:"+received+", Sent:"+sent);
            sb.append("\r\n");

        }
wifi.setText(sb.toString());

    }
}