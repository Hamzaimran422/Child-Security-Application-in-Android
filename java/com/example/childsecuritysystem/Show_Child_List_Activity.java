package com.example.childsecuritysystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Show_Child_List_Activity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_child_list);

        listView=(ListView)findViewById(R.id.childlist);

        dataModels= new ArrayList<>();
        String url = Utility.baseUrl+"User/Allusers?pId="+LOGINACTIVITY.UserData.U_ID;
        CallResult result = WCFHandler.GetJsonResult("User/Allusers?pId="+LOGINACTIVITY.UserData.U_ID);
        if(result.StatusCode==200) {
            Gson gson = new Gson();
            Type dataContent = new TypeToken<ArrayList<DataModel>>(){}.getType();
            dataModels = gson.fromJson(result.jsonString,dataContent);
            adapter= new CustomAdapter(dataModels,getApplicationContext());

            listView.setAdapter(adapter);
        }
        else{
            Log.i("Response",result.Message);
            Toast.makeText(Show_Child_List_Activity.this,"Invalid"+result.Message ,Toast.LENGTH_SHORT).show();
        }

    }

}


