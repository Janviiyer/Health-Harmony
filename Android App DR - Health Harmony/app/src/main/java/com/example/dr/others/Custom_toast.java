package com.example.dr.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dr.Dashboard;
import com.example.dr.R;
import java.util.ArrayList;

public class Custom_toast extends AppCompatActivity {

    TextView tname,tvalue,t_result;
    private ArrayList<List_data> list_data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_toast_glucose);

        list_data=new ArrayList<>();

        String name = getIntent().getStringExtra("name");
        String value = getIntent().getStringExtra("value");

        tname = findViewById(R.id.text_name);
        tvalue = findViewById(R.id.text_value);
        t_result = findViewById(R.id.text_result);

        int i_value = Integer.parseInt(value);


        switch (name){
            case "Glucose":

                    tname.setText(name+":");
                    tvalue.setText(value);

                if (i_value<=140) {

                    t_result.setText("Normal");
                }
                else if (i_value >= 140 && i_value <= 199){
                    t_result.setText("Prediabetic");
                }
                else if (i_value>=200){
                    t_result.setText("Diabetic");
                }

                break;
            case "Respiration":

                tname.setText(name+":");
                tvalue.setText(value);

                if (i_value >= 12 && i_value <= 16) {

                    t_result.setText("Normal");
                }
                else if (i_value < 12){
                    t_result.setText("Sign of Bronchitis");
                }
                break;

            case "Blood Pressure":

                tname.setText(name+":");
                tvalue.setText(value);

                if (i_value >= 120) {

                    t_result.setText("CHD");
                }
                else if (i_value <= 120){
                    t_result.setText("Normal");
                }
                break;

        case "Heart Rate":

        tname.setText(name+":");
        tvalue.setText(value);

        if (i_value >= 80) {

            t_result.setText("CHD");
        }
        else if (i_value <= 80){
            t_result.setText("Normal");
        }
        break;

            case "Cholestrol":

                tname.setText(name+":");
                tvalue.setText(value);

                if (i_value >= 200 && i_value <= 125) {

                    t_result.setText("CHD");
                }
                else {
                    t_result.setText("Normal");
                }
                break;

            case "Oxygen":

                tname.setText(name+":");
                tvalue.setText(value);

                if (i_value < 96) {

                    t_result.setText("Hypoxemia");
                }
                else {
                    t_result.setText("Normal");
                }
                break;

        }
 }

    public void back_aeros(View view) {
        Intent intent = new Intent(Custom_toast.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
