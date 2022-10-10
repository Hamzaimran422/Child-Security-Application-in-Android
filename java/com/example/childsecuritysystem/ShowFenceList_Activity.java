package com.example.childsecuritysystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShowFenceList_Activity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static FenceCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fence_list);

        listView=(ListView)findViewById(R.id.showfencelist);

        dataModels= new ArrayList<>();
       String url = "User/Showfencelist?fId="+LOGINACTIVITY.UserData.U_ID +"&gender="+LOGINACTIVITY.UserData.Gender ;
       //String url = "User/Showfencelist?fId="+LOGINACTIVITY.UserData.U_ID;
        CallResult result = WCFHandler.GetJsonResult(url);
        if(result.StatusCode==200) {
            Gson gson = new Gson();
            Type dataContent = new TypeToken<ArrayList<DataModel>>(){}.getType();
            dataModels = gson.fromJson(result.jsonString,dataContent);
            adapter= new FenceCustomAdapter(dataModels,getApplicationContext());

            listView.setAdapter(adapter);
        }
        else{
            Log.i("Response",result.Message);
            Toast.makeText(ShowFenceList_Activity.this,"Invalid"+result.Message ,Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int id = dataModels.get(i).F_ID;
               CallResult result = WCFHandler.GetJsonResult("User/ShowPointsonmap?pId="+id);
                ArrayList<LatLng> points = new ArrayList<>();
                if(result.StatusCode==200){
                    Gson gson = new Gson();
                    Type t = new TypeToken<ArrayList<LatLng>>(){}.getType();
                    points = gson.fromJson(result.jsonString,t);
                }


                Intent intent=new Intent(ShowFenceList_Activity.this,Check_Fence_Activity.class);
                Check_Fence_Activity.points = points;
                startActivity(intent);

            }
        });

    }

}




