package com.example.childsecuritysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LOGINACTIVITY extends AppCompatActivity {
    String U_Email;
    String Password;
    public static DataModel UserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        TextView Email = (TextView) findViewById(R.id.Email);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton Login = (MaterialButton) findViewById(R.id.btnlogin);
        TextView move = (TextView) findViewById(R.id.move);


        //admin and admin
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 U_Email = Email.getText().toString();
                 Password = password.getText().toString();
                 String url = Utility.baseUrl+"User/userLogin?email="+U_Email+"&password="+Password;
                RequestQueue queue = Volley.newRequestQueue(LOGINACTIVITY.this);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Gson gson = new Gson();
                                    UserData = gson.fromJson(response.toString(),DataModel.class);
                                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(getApplicationContext(),DashBoardActivity.class);
                                    startActivity(i);
                                } catch (Exception e) {
                                    String msg = e.getMessage();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LOGINACTIVITY.this,"Invalid Email or Password"+error.getMessage() ,Toast.LENGTH_SHORT).show();
                        String msg=error.getMessage();


                    }



            });
                request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(request);
//                Gson gson =new Gson();
//                User newuser=new User();
//                String userjson =gson.toJson(newuser);
//                String json= WCFHandler.PostJsonResult("userLogin" , userjson);
//                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//                requestQueue.start();
//                JSONObject obj=new JSONObject();
//                U_Email=Email.getText().toString();
//                Password=password.getText().toString();
//                if(U_Email.isEmpty()){
//                    Email.setError("Fill this Field First");
//                }
//                if(Password.isEmpty()){
//                    password.setError("Fill this Field First");
//                }
//                else
//                {
//                    WCFHandler objj =new WCFHandler();
//                    String functionurl="UserLogin";
//                    functionurl=functionurl + Email+"/";
//                    functionurl=functionurl + Password;
//                    try {
//                        JSONObject response=obj.getJSONObject(functionurl);
//                        JSONObject object=new JSONObject((Map) response);
//                        String data_res=obj.getString("UserLogin");
//                        if(data_res.equals("1"))
//                        {
//                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
//                            Intent i=new Intent(getApplicationContext(),DashBoardActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }


//                if (username.getText().toString().equals("Admin") && password.getText().toString().equals("Admin")) {
//                    Toast.makeText(LOGINACTIVITY.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    //Intent intent=new Intent(LOGINACTIVITY.this,ViewImageList.class);
//                    //startActivity(intent);
//                } else
//                    Toast.makeText(LOGINACTIVITY.this, "Login failed !!!!", Toast.LENGTH_SHORT).show();
        };
        });
    }

    public void move(View v)
    {
        Toast.makeText(LOGINACTIVITY.this,"Goto Signup Page", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(LOGINACTIVITY.this,SIGNUPACTIVITY.class);
                startActivity(intent);
    }
}
