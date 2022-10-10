package com.example.childsecuritysystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;


public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //////////////////////////////*****   Initialization   *****////////////////////////////////////////////
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    //Button Logout;
    MaterialButton LiveLocationHistory;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_dash_board);
/////////////////////////////////***************** Get Ids      *******************/////////////////////////////
         drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_bar);
         toolbar = findViewById(R.id.tool_bar);
         MaterialButton readcalllogs = findViewById(R.id.btncalllogs);
         MaterialButton readsmslogs = findViewById(R.id.btnsmslogs);
         MaterialButton Fence = findViewById(R.id.btnfence);
         MaterialButton Speed = findViewById(R.id.btnspeed);
         MaterialButton wifiusage = findViewById(R.id.btnwifi);
         LiveLocationHistory =findViewById(R.id.btnLocationhistory);
         Speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashBoardActivity.this,"Goto Speed ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashBoardActivity.this,Speed_Activity.class);
                startActivity(intent);
            }
        });
        Fence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashBoardActivity.this,"Goto Fence List ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashBoardActivity.this,ShowFenceList_Activity.class);
                startActivity(intent);

            }
        });
         readcalllogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashBoardActivity.this,"Goto Read CallLogs", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashBoardActivity.this,ReadCallLogs_Activity.class);
                startActivity(intent);

            }
        });
        readsmslogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashBoardActivity.this,"Goto Read SmsLogs", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashBoardActivity.this,SmsLogs_Activity.class);
                startActivity(intent);
             }
        });
        wifiusage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashBoardActivity.this,"Goto Wifi Logs", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DashBoardActivity.this,Wifi_Activity.class);
                startActivity(intent);
            }
        });
         LiveLocationHistory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(DashBoardActivity.this, "Goto Location History ", Toast.LENGTH_SHORT).show();
                 Intent i = new Intent(DashBoardActivity.this, Show_Child_Live_Location_Activity.class);
                 startActivity(i);
             }
         });

        setSupportActionBar();
     }

    private void setSupportActionBar() {
         // Hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.logout).setVisible(false);
        menu.findItem(R.id.home).setVisible(false);
        //                                                //

         navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    ///////////////////////******   Swap back code   ***********///////////////////////////////////////

    @Override
    public void onBackPressed() {
         if(drawerLayout.isDrawerOpen(GravityCompat.START))
         {
             drawerLayout.closeDrawer(GravityCompat.START);
         }
         else {
             super.onBackPressed();
         }
    }
    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Intent intent1=new Intent(DashBoardActivity.this,DashBoardActivity.class);
                startActivity(intent1);
                break;
            case R.id.addchild:
                Intent intent2=new Intent(DashBoardActivity.this,Add_Child_Activity.class);
                startActivity(intent2);
                break;
//            case R.id.deletechild:
//                Intent intent3=new Intent(DashBoardActivity.this,Delete_Child_Activity.class);
//                startActivity(intent3);
//                break;
            case R.id.showchildlist:
                Intent intent4=new Intent(DashBoardActivity.this,Show_Child_List_Activity.class);
                startActivity(intent4);
                break;
            case R.id.createfence:
                Intent intent5=new Intent(DashBoardActivity.this,Add_Fence_Credential_Activity.class);
                startActivity(intent5);
                break;
            case R.id.ShowFenceList:
                Intent intent6=new Intent(DashBoardActivity.this,ShowFenceList_Activity.class);
                startActivity(intent6);
                break;
//            case R.id.showprivatefence:
//                Intent intent7= new Intent(DashBoardActivity.this,ShowPublicFenceList_Activity.class);
//                startActivity(intent7);
//                break;
//            case R.id.deletefence:
//                Intent intent4=new Intent(DashBoardActivity.this,);
//                startActivity(intent4);
            case R.id.logout:
                 finish();
                 System.exit(0);
//                Intent intent3=new Intent(DashBoardActivity.this,SIGNUPACTIVITY.class);
//                startActivity(intent3);
                break;


        }
        return true;
    }

}