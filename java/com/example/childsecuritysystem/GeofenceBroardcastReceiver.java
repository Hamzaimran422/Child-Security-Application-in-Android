package com.example.childsecuritysystem;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");

        //Message Broad Cast in app
        // Toast.makeText(context,"Geofencing Triggered.....", LENGTH_SHORT).show();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError())
        {
            Log.d(TAG, "onReceive: Error receiving geofence event....");
            return;
        }
        List<Geofence> geofenceList =geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList)
        {
            Log.d(TAG, "onReceive: " + geofence.getRequestId()   );
        }
        // Location location=geofencingEvent.getTriggeringGeofences();

        int transitionType = geofencingEvent.getGeofenceTransition();
        switch (transitionType)
        {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                makeText(context, "Geofence Transition Enter", LENGTH_SHORT).show();
                break;

            case Geofence.GEOFENCE_TRANSITION_DWELL:
                makeText(context, "Geofence Transition Dwell", LENGTH_SHORT).show();
                break;

            case Geofence.GEOFENCE_TRANSITION_EXIT:
                makeText(context, "Geofence Transition Exit", LENGTH_SHORT).show();
                break;

        }

    }
}

//package com.example.childsecuritysystem;
//
//public class GeofenceBroardcastReceiver {
//}
