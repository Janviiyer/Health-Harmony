package com.example.dr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr.others.CustomDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView Registernow;
    CustomDialog pdDialog;
    String URL_LOGIN = "https://tdxrtech.000webhostapp.com/login.php";
    String lemail,lpass;
    EditText username,password;
    Button loginButton;
    String is_signed_in="";
    SharedPreferences mPreferences;
    String sharedprofFile="com.example.dr.registration_login";
    SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        is_signed_in = mPreferences.getString("issignedin","false");

        if(is_signed_in.equals("true"))
        {
            Intent i = new Intent(Login.this,Dashboard.class);
            startActivity(i);

            finish();
        }

        Registernow =(TextView)findViewById(R.id.logins);
        pdDialog = new CustomDialog(Login.this);
        pdDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        pdDialog.setTitle("Signing please wait...");
        pdDialog.setCanceledOnTouchOutside(false);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        username = (EditText) findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_passwrod);
        loginButton=(Button) findViewById(R.id.button_signin);
        Registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(Login.this,Registe.class);
                startActivity(register);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lemail=username.getText().toString().trim();
                lpass=password.getText().toString().trim();
                if(lemail.isEmpty()||lpass.isEmpty())
                {
                    Toast.makeText(Login.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                }else {
                    Login();
                }
            }
        });
    }

    private void Login()
    {
        pdDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            String id= jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");

                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Logged In  Success",Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();

                                preferencesEditor.putString("issignedin","true");
                                preferencesEditor.putString("SignedInUserID",id);
                                preferencesEditor.putString("SignedInName",name);
                                preferencesEditor.putString("SignedInusername",email);
                                preferencesEditor.apply();

                                Intent i = new Intent(Login.this,Dashboard.class);
                                i.putExtra("name",name);
                                startActivity(i);
                                finish();

                            }
                            if(success.equals("0")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }
                            if(success.equals("3")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();

                            pdDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Registration Error !2"+error,Toast.LENGTH_LONG).show();
                pdDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("email",lemail);
                params.put("pass",lpass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}