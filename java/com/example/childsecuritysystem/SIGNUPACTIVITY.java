package com.example.childsecuritysystem;

import static com.android.volley.Request.Method.POST;

import android.content.Intent;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class SIGNUPACTIVITY extends AppCompatActivity {
    EditText UserName,Role,Email,Phone_No,Password,Gender;
    MaterialButton signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);


        UserName = (EditText) findViewById(R.id.username);
        Role=(EditText) findViewById(R.id.Role);
        Gender=(EditText)findViewById(R.id.Gender);
        Email = (EditText) findViewById(R.id.Email);
        Phone_No = (EditText) findViewById(R.id.Phoneno);
        Password = (EditText) findViewById(R.id.password);
        TextView back = (TextView) findViewById(R.id.backtosignin);
        MaterialButton signup = (MaterialButton) findViewById(R.id.btnSignup);

        signup.setOnClickListener(new OnClickListener() {


            // @Override
//                      public void onClick(View view) {

//            }

            private Object Volley;

            public  void onClick(View view) {
//                RequestQueue requestQueue= Volley.new RequestQueue(getApplicationContext());
                RequestQueue rq = new RequestQueue(getApplicationContext());

                rq.startTiming();
                JSONObject obj=new JSONObject();

                try {
                    obj.put("U_Name",UserName.getText().toString());
                    obj.put("Role",Role.getText().toString());
                    obj.put("Gender",Gender.getText().toString());
                    obj.put("U_Email",Email.getText().toString());
                    obj.put("C_Phone_No",Phone_No.getText().toString());
                    obj.put("Password",Password.getText().toString());


                    String url = Utility.baseUrl+"User/Adduser";
                    JsonObjectRequest jor=new JsonObjectRequest(
                            POST, url, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "SignUp SuccessFully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                    //RequestQueue queue =Volley.newRequestQueue(SIGNUPACTIVITY.this);
                    //queue.startTiming();
                    com.android.volley.RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(SIGNUPACTIVITY.this);
                    requestQueue.add(jor);



                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SIGNUPACTIVITY.this,"Saved", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    public void back(View v)
    {
        Toast.makeText(SIGNUPACTIVITY.this,"Goto Login Page", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(SIGNUPACTIVITY.this,LOGINACTIVITY.class);
        startActivity(intent);
    }

}