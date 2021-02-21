package com.example.dr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr.others.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registe extends AppCompatActivity {

    Button signup;
    String sname,susername,semail,spassword,sconfmpass,sdob,sheight,sweight,sgender;
    EditText f_name,username,email,pass,confm_pass,dob,height,weight,gender;
    private CustomDialog progressDialog;
    String URL_REGISTER = "https://tdxrtech.000webhostapp.com/android_registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        f_name = (EditText) findViewById(R.id.full_name);
        username = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        confm_pass = (EditText) findViewById(R.id.conf_password);
        dob = (EditText) findViewById(R.id.DOB);
        height = (EditText) findViewById(R.id.hight);
        weight = (EditText) findViewById(R.id.weight);
        gender = (EditText) findViewById(R.id.gender);

        progressDialog = new CustomDialog(Registe.this);
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        progressDialog.setTitle("Registering please wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        signup = (Button) findViewById(R.id.btn_signu);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=f_name.getText().toString().trim();
                susername=username.getText().toString().trim();
                semail=email.getText().toString().trim();
                spassword=pass.getText().toString().trim();
                sconfmpass=pass.getText().toString().trim();
                sdob=dob.getText().toString().trim();
                sheight=height.getText().toString().trim();
                sweight=weight.getText().toString().trim();
                sgender=gender.getText().toString().trim();

                if(sname.isEmpty()||susername.isEmpty()||spassword.isEmpty())
                {
                    Toast.makeText(Registe.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                }
                else{
                    Register();
                }
            }
        });
    }

    private void Register()
    {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Intent login = new Intent(Registe.this,Dashboard.class);
                                login.putExtra("name",sname);
                                startActivity(login);
                                finish();
                            }
                            if(success.equals("0")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                            if(success.equals("3")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Registration Error !2"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("name",sname);
                params.put("user",susername);
                params.put("email",semail);
                params.put("pass",spassword);
                params.put("confpass",sconfmpass);
                params.put("dob",sdob);
                params.put("hight",sheight);
                params.put("weight",sweight);
                params.put("gender",sgender);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

