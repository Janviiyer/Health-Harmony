package com.example.dr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr.others.CustomDialog;
import com.example.dr.others.List_data;
import com.example.dr.others.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {


    public static final String URI_DATA="https://tdxrtech.000webhostapp.com/android_healthupdates.php";
    // This string will hold the results
    private ArrayList<List_data> list_data;
    private GridView gridView;
    MyAdapter adapter;
    private CustomDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gridView=(GridView)findViewById(R.id.gridView);
        TextView user = (TextView) findViewById(R.id.users);
        TextView date = (TextView) findViewById(R.id.get_date);
        TextView time = (TextView) findViewById(R.id.get_time);
        list_data=new ArrayList<>();
        progressDialog = new CustomDialog(Dashboard.this);
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setCanceledOnTouchOutside(false);



        Intent i = getIntent();
        String u = i.getStringExtra("name");
        user.setText(u);


        String currentDate = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());
        date.setText(currentDate);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        time.setText(currentTime);

        // adapter=new MyAdapter(getApplicationContext(),list_data);
        getData();
    }

    private void getData() {
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, URI_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                list_data.clear();
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
                    adapter=new MyAdapter(getApplicationContext(),R.layout.list_item,list_data);
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

    public void back_aero(View view) {

       SetDilogue();

    }

    public void SetDilogue(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetDilogue();
        finish();
    }

    public void open_stress(View view) {

        Intent i = new Intent(Dashboard.this,Stress_level.class);
        startActivity(i);

    }

    public void open_health(View view) {

        Intent i2 = new Intent(Dashboard.this,Health_status.class);
        startActivity(i2);

    }
}

