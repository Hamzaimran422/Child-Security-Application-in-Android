package com.example.childsecuritysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class Delete_Child_Activity extends AppCompatActivity {

    EditText U_Name;
    MaterialButton btndeletechild;
    String delete_child;
    //String url = "http://192.168.225.194/ChildSecuritySystemAPI/api/User/DeleteUser";
    // String url="http://169.254.216.155/ChildSecuritySystemAPI/api/User/DeleteUser";
    String url=Utility.baseUrl+"User/DeleteUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_child);

        U_Name = (EditText) findViewById(R.id.username_delete_child);
        btndeletechild = (MaterialButton) findViewById(R.id.btnDeleteChild);

        btndeletechild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_child = U_Name.getText().toString().trim();
                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Delete_Child_Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Delete_Child_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> para = new HashMap<String, String>();
                        para.put("U_Name", delete_child);
                        return para;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Delete_Child_Activity.this);
                requestQueue.add(rq);


            }
        });

    }
}