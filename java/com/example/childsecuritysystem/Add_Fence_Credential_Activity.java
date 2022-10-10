package com.example.childsecuritysystem;

import static android.text.format.DateFormat.format;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.Calendar;

public class Add_Fence_Credential_Activity extends AppCompatActivity {

    EditText F_Name, StartTime, EndTime;
    // TextView SetMapPoints;
    MaterialButton AddFence;
    MaterialButton AddPrivateFence;
    MaterialButton AddMultiFence;

    /////////////////////////////////
    MaterialButton Addmale;
    MaterialButton Addfemale;
    MaterialButton AddAll;
    // String f_name, starttime, endtime;
    int S_Hour, S_Minute;
    int E_Hour, E_Minute;
    //String Status = "Public Fence";
    //String sta = "Private Fence";

//    String GenderM= "Male";
//    String GenerF= "Female";
//    String BothGender="All Parent";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fence_credential);
        F_Name = findViewById(R.id.Fence_name);
        StartTime = findViewById(R.id.S_time);
        EndTime = findViewById(R.id.E_time);
        //   SetMapPoints=findViewById(R.id.SetMap);
        AddPrivateFence = findViewById(R.id.btnAddFenceasprivate);
        AddFence = findViewById(R.id.btnAddFence);
        AddMultiFence=findViewById(R.id.btn_save_again_fence);
        Addmale=findViewById(R.id.btn_save_Male);
        Addfemale=findViewById(R.id.btn_save_Female);
        AddAll=findViewById(R.id.btn_save_All);

        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initialize time picker Dialogue
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Fence_Credential_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourDay, int minute) {
                                S_Hour = hourDay;
                                S_Minute = minute;

                                //initialize calender
                                Calendar calendar = Calendar.getInstance();

                                //set hour and minute
                                calendar.set(S_Hour, S_Minute);
                                StartTime.setText(format("hh:mm aa", calendar));

                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(S_Hour, S_Minute);
                timePickerDialog.show();
            }
        });
        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Fence_Credential_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int ehourofday, int eminute) {
                                E_Hour = ehourofday;
                                E_Minute = eminute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(E_Hour, E_Minute);
                                EndTime.setText(format("hh:mm aa", calendar));

                            }
                        }, 12, 0, true
                );
                timePickerDialog.updateTime(E_Hour, E_Minute);
                timePickerDialog.show();
            }
        });
        AddPrivateFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFence("Private");
            }
        });


        AddFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFence("Public" );
            }
        });
        AddMultiFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMultiFence("Public");
            }
        });

        Addmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFenceGender("Public","Male");
            }
        });
        Addfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFenceGender("public", "Female");
            }
        });
        AddAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFenceGender("Public","Both");
            }
        });

    }

    public void addFence(String status) {
        Fence fence = new Fence();
        fence.F_Name = F_Name.getText().toString();
        fence.StartTime = StartTime.getText().toString();
        fence.EndTime = EndTime.getText().toString();
        fence.U_ID = LOGINACTIVITY.UserData.U_ID;
        fence.FenceStatus = status;
        //fence.Gender=LOGINACTIVITY.UserData.Gender;

        Gson gson = new Gson();
        String jsonString = gson.toJson(fence);
        CallResult result = WCFHandler.PostJsonResult("User/AddFence", jsonString, "");
        if (result.StatusCode == 200) {
            fence = gson.fromJson(result.jsonString, Fence.class);
            Toast.makeText(getApplicationContext(), "Add Fence Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Add_Fence_Credential_Activity.this, Fence_Activity.class);
            //i=new Intent(Add_Fence_Credential_Activity.this,Check_Fence_Activity.class);
            i.putExtra("FenceId", fence.F_ID);
            startActivity(i);
        }
        else {
            Toast.makeText(getApplicationContext(), result.Message, Toast.LENGTH_SHORT).show();
        }

    }

    private void addMultiFence(String status) {
        Fence fence = new Fence();
        fence.F_Name = F_Name.getText().toString();
        fence.StartTime = StartTime.getText().toString();
        fence.EndTime = EndTime.getText().toString();
        fence.U_ID = LOGINACTIVITY.UserData.U_ID;
        fence.FenceStatus = status;
        Gson gson = new Gson();
        String jsonString = gson.toJson(fence);
        CallResult result = WCFHandler.PostJsonResult("User/AddFence", jsonString, "");
        if (result.StatusCode == 200) {
            fence = gson.fromJson(result.jsonString, Fence.class);
            Toast.makeText(getApplicationContext(), "Add MultiFence Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Add_Fence_Credential_Activity.this, Check_Fence_Activity.class);
            //i=new Intent(Add_Fence_Credential_Activity.this,Check_Fence_Activity.class);
            i.putExtra("FenceId", fence.F_ID);
            startActivity(i);
        }
        else {
            Toast.makeText(getApplicationContext(), result.Message, Toast.LENGTH_SHORT).show();
        }

    }
    public void addFenceGender(String status,String gender) {
        Fence fence = new Fence();
        fence.F_Name = F_Name.getText().toString();
        fence.StartTime = StartTime.getText().toString();
        fence.EndTime = EndTime.getText().toString();
        fence.U_ID = LOGINACTIVITY.UserData.U_ID;
        fence.FenceStatus = status;
        fence.Gender=gender;

        //fence.Gender=LOGINACTIVITY.UserData.Gender;

        Gson gson = new Gson();
        String jsonString = gson.toJson(fence);
        CallResult result = WCFHandler.PostJsonResult("User/AddFence", jsonString, "");
        if (result.StatusCode == 200) {
            fence = gson.fromJson(result.jsonString, Fence.class);
            Toast.makeText(getApplicationContext(), "Add Fence Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Add_Fence_Credential_Activity.this, Fence_Activity.class);
            //i=new Intent(Add_Fence_Credential_Activity.this,Check_Fence_Activity.class);
            i.putExtra("FenceId", fence.F_ID);
            startActivity(i);
        }
        else {
            Toast.makeText(getApplicationContext(), result.Message, Toast.LENGTH_SHORT).show();
        }

    }
}
