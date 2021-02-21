package com.example.dr;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr.others.CustomDialog;
import com.example.dr.others.Custom_toast;
import com.example.dr.others.List_data;
import com.example.dr.others.MyAdapter;
import com.example.dr.others.MyAdapterSec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Health_status extends AppCompatActivity {

    public static final String URI_DATA="https://tdxrtech.000webhostapp.com/android_healthupdates.php";

    private ArrayList<List_data> list_data;
    private GridView gridView;
    MyAdapterSec adapter;
    private CustomDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status);

        gridView = findViewById(R.id.grid);
        list_data=new ArrayList<>();
        progressDialog = new CustomDialog(Health_status.this);
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),Custom_toast.class);
                intent.putExtra("name",list_data.get(i).getName());
                intent.putExtra("value",list_data.get(i).getValue());
                startActivity(intent);
            }
        });

        getData();

    }
    public void back_aero(View view) {
    }


    private void getData() {
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, URI_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("response");
                    for (int i=0; i<array.length(); i++){
                        JSONObject ob=array.getJSONObject(i);
                        List_data listData=new List_data(
                                ob.getString("name"),
                                ob.getString("img"),
                                ob.getString("value"));
                        list_data.add(listData);
                    }
                    adapter=new MyAdapterSec(getApplicationContext(),R.layout.list_item_sec,list_data);
                    gridView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}