package com.example.childsecuritysystem;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class SmsLogs_Activity extends AppCompatActivity {
    TextView smslog;
    public ArrayList<SMSDetails> getAllSms(Context context)
    {

        ArrayList<SMSDetails> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, Telephony.Sms.DATE_SENT+" Desc");
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {

                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Date dateFormat = new Date(Long.valueOf(smsDate));
                    SMSDetails objDetails = new SMSDetails();
                    objDetails.body = body;



                    list.add(objDetails);
                    if(j==50)
                        break;
                    String type;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }


                    c.moveToNext();
                }
            }

            c.close();

        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return  list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_logs);
        smslog=findViewById(R.id.slog);
        ArrayList<SMSDetails>  list =  getAllSms(getApplicationContext());
        String msgs = "";
        for (SMSDetails obj: list) {
            msgs+=obj.body+"\n----------------------------------\n";
        }
        smslog.setText(msgs);
        int x=0;
    }
}



