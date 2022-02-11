package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    Spinner eduSpinner, countrySpinner, genderSpinner;
    EditText fullName, username, email, age, password, rePassword;
    ArrayAdapter<CharSequence> eduAdapter;
    ArrayAdapter<CharSequence> countryAdapter;
    ArrayAdapter<CharSequence> genderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //onBackPressed();
        findViewById(R.id.backBtnRegister).setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, MainActivity.class)));

        //هذي اكواد القائمة المنسدلة
        eduSpinner = (Spinner) findViewById(R.id.eduSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        eduAdapter = ArrayAdapter.createFromResource(this, R.array.edu_list, android.R.layout.simple_spinner_item);
        countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_list, android.R.layout.simple_spinner_item);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_item);

        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eduSpinner.setAdapter(eduAdapter);
        eduSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapter.getItem(i).toString();
                //هذا يجيب القيمة اللي اخترناها بعدين لمن نجيب كل القيم ان شاء الله بسوي له متغير
                Toast.makeText(RegisterActivity.this, eduAdapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapter.getItem(i).toString();
                //هذا يجيب القيمة اللي اخترناها بعدين لمن نجيب كل القيم ان شاء الله بسوي له متغير
                Toast.makeText(RegisterActivity.this, countryAdapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //adapter.getItem(i).toString();
                //هذا يجيب القيمة اللي اخترناها بعدين لمن نجيب كل القيم ان شاء الله بسوي له متغير
                Toast.makeText(RegisterActivity.this, genderAdapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }//end onCreate

}