package com.example.childsecuritysystem;
import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class CallResult {
    public String Message;
    public String jsonString;
    public int StatusCode;

    public CallResult() {
        StatusCode = 201;
    }
}

public class WCFHandler {

    static String webUrl=Utility.baseUrl;

    public static String getMethodCall(String url1)
    {
        try{
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url=new URL(url1);
            //Log.i("GetMethod:",webUrl+functionName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String jsonData;

            if ((jsonData = bufferedReader.readLine()) != null) {
            }
            return jsonData;

        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (ProtocolException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    public static CallResult PutJsonResult(String functionName, String userJson)
    {
        CallResult callResult=new CallResult();
        callResult.StatusCode=204;

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpPut request = new HttpPut(webUrl+functionName);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");


        StringEntity entity = null;
        try {
            entity = new StringEntity(userJson.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentEncoding(new BasicHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, "application/json"));
        entity.setContentType("application/json");

        request.setEntity(entity);

// Send request to WCF service
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            callResult.StatusCode = code;
            if(code==200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String result = "";
                String jsonData = null;

                while ((jsonData = reader.readLine()) != null)
                    result += jsonData;
                callResult.jsonString = result;
            }
            else{
                callResult.Message = message;
                callResult.jsonString=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            callResult.jsonString = null;
            callResult.Message = e.getMessage();
        }
        return callResult;
    }
    public static CallResult PostJsonResult(String functionName, String userJson,String type)
    {
        CallResult callResult=new CallResult();
        callResult.StatusCode=201;

        if(type.equals(""))
            type="application/json";

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpPost request = new HttpPost(webUrl+functionName);
        request.setHeader("Accept", "*/*");
        request.setHeader("Content-type", type);


        StringEntity entity = null;
        try {
            entity = new StringEntity(userJson.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentEncoding(new BasicHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, type));
        entity.setContentType(type);

        request.setEntity(entity);

// Send request to WCF service
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            callResult.StatusCode = code;
            if(code==200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String result = "";
                String jsonData = null;

                while ((jsonData = reader.readLine()) != null)
                    result += jsonData;
                callResult.jsonString = result;
            }
            else{
                callResult.Message = message;
                callResult.jsonString=null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            callResult.jsonString = null;
            callResult.Message = e.getMessage();
        }
        return callResult;
    }
    public static CallResult GetJsonResult(String functionName)
    {
        CallResult callResult=new CallResult();
        callResult.StatusCode=201;

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpGet request = new HttpGet(webUrl+functionName);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        // Send request to WCF service
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            callResult.StatusCode = code;
            if(code==200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String result = "";
                String jsonData = null;

                while ((jsonData = reader.readLine()) != null)
                    result += jsonData;
                callResult.jsonString = result;
            }
            else{
                callResult.Message = message;
                callResult.jsonString=null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            callResult.jsonString = null;
            callResult.Message = e.getMessage();
        }
        return callResult;
    }
}
