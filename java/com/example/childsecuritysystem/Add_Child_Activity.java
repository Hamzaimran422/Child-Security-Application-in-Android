package com.example.childsecuritysystem;

import static com.android.volley.Request.Method.POST;

import android.net.http.RequestQueue;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Add_Child_Activity extends AppCompatActivity {
    EditText UserName, Role, Phone_No, Password;
    MaterialButton Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);


        UserName = (EditText) findViewById(R.id.username_child);
        Role = (EditText) findViewById(R.id.Role);
        Phone_No = (EditText) findViewById(R.id.Phoneno_child);
        Password = (EditText) findViewById(R.id.password_child);
        MaterialButton Add =(MaterialButton) findViewById(R.id.btnAddChild);

        Add.setOnClickListener(new OnClickListener() {


            // @Override
//                      public void onClick(View view) {

//            }

            private Object Volley;

            public void onClick(View view) {
//                RequestQueue requestQueue= Volley.new RequestQueue(getApplicationContext());
                RequestQueue rq = new RequestQueue(getApplicationContext());

                rq.startTiming();
                JSONObject obj = new JSONObject();

                try {
                    obj.put("U_Name", UserName.getText().toString());
                    obj.put("Role", Role.getText().toString());
                    //obj.put("U_Email",Email.getText().toString());
                    obj.put("C_Phone_No", Phone_No.getText().toString());
                    obj.put("Password", Password.getText().toString());
                    obj.put("ParentId", LOGINACTIVITY.UserData.U_ID);

                    String url = Utility.baseUrl + "User/Adduser";
                    JsonObjectRequest jor = new JsonObjectRequest(
                            POST, url, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Add Child SuccessFully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                    //RequestQueue queue =Volley.newRequestQueue(SIGNUPACTIVITY.this);
                    //queue.startTiming();
                    com.android.volley.RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(Add_Child_Activity.this);
                    requestQueue.add(jor);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}







////////////////////////////////////***********************************//////////////////////////////////////////////******
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SIGNUPACTIVITY.this,"Saved", Toast.LENGTH_SHORT).show();
//            }
//        });



//    public void back(View v)
//    {
//        Toast.makeText(SIGNUPACTIVITY.this,"Goto Login Page", Toast.LENGTH_SHORT).show();
//        Intent intent=new Intent(SIGNUPACTIVITY.this,LOGINACTIVITY.class);
//        startActivity(intent);
//    }


//package com.example.childsecuritysystem;
//
//import static com.android.volley.Request.Method.POST;
//
//import android.net.http.RequestQueue;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.google.android.material.button.MaterialButton;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Add_Child_Activity extends AppCompatActivity {
//
//    EditText U_Name,Role,U_Phone_No,Password;
//    //String c_name,role,c_phone_no,c_password;
//    MaterialButton btnaddchild;
//    //String url="http://192.168.10.5/ChildSecuritySystemAPI/api/User/Adduser";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_child);
//        U_Name=findViewById(R.id.username_child);
//        Role=findViewById(R.id.Role);
//        U_Phone_No=findViewById(R.id.Phoneno_child);
//        Password=findViewById(R.id.password_child);
//        btnaddchild=findViewById(R.id.btnAddChild);
//
//        btnaddchild.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public  void onClick(View view) {
//              RequestQueue rq=new RequestQueue(getApplicationContext());
//                //android.net.http.RequestQueue rq = new RequestQueue(getApplicationContext());
//
//                rq.startTiming();
//                JSONObject obj=new JSONObject();
//
//                try {
//                    obj.put("U_Name",U_Name.getText().toString());
//                    obj.putOpt("Role",Role.getText().toString());
//                    obj.put("C_Phone_No",U_Phone_No.getText().toString());
//                    obj.put("Password", Password.getText().toString());
//
//
//                   // String url="http://192.168.182.194/ChildSecuritySystemAPI/api/User/Adduser";
//                    String url=Utility.baseUrl+"User/Adduser";
//                   // String url="http://169.254.216.155/ChildSecuritySystemAPI/api/User/Adduser";
//
//                    JsonObjectRequest jor=new JsonObjectRequest(
//                            POST, url, obj, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Toast.makeText(getApplicationContext(), "Add Child Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    );
//
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//         /////////////          Another Method //////////////////////////
////            public void onClick(View view) {
////                c_name=C_Name.getText().toString().trim();
////                c_phone_no=C_Phone_no.getText().toString().trim();
////                c_password=C_Password.getText().toString().trim();
////
////                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        Toast.makeText(Add_Child_Activity.this, response, Toast.LENGTH_SHORT).show();
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(Add_Child_Activity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
////                    }
////                })
////                {
////                    @Override
////                    protected Map<String , String> getParams() throws AuthFailureError{
////                        Map<String, String> para= new HashMap<String , String>();
////                        para.put("C_Name",c_name);
////                        para.put("Role","role");
////                        para.put("C_Phone_no" , c_phone_no);
////                        para.put("C_Password" , c_password);
////                        return para;
////                    }
////                };
////                RequestQueue requestQueue= Volley.newRequestQueue(Add_Child_Activity.this);
////                requestQueue.add(rq);
////
////
////            }
////        });
//    }
//}