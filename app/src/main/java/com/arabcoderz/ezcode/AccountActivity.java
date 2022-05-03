package com.arabcoderz.ezcode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.DialogInterface;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private CircleImageView accountAvatar;
    private TextView accountAge;
    private Spinner accountEdu, accountGender, accountCountry;
    private ArrayAdapter<CharSequence> eduAdapter;
    private ArrayAdapter<CharSequence> countryAdapter;
    private ArrayAdapter<CharSequence> genderAdapter;
    private AlertDialog.Builder builder;
    private EditText accountFullName, accountUsername, accountEmail, accountPassword;
    private String msg,yes,no;
    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        findViewById(R.id.backBtnAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, UserMoreActivity.class));
            }
        });
        accountAvatar = findViewById(R.id.pick_avatar_account);
        accountFullName = findViewById(R.id.full_user_name_account);
        accountUsername = findViewById(R.id.user_name_account);
        accountEmail = findViewById(R.id.emil_account);
        accountAge = findViewById(R.id.Select_date_account);
        accountEdu = findViewById(R.id.eduSpinner_account);
        accountGender = findViewById(R.id.genderSpinner_account);
        accountCountry = findViewById(R.id.countrySpinner_account);
        accountPassword = findViewById(R.id.password_account);
        builder = new AlertDialog.Builder(this);

        eduAdapter = ArrayAdapter.createFromResource(this, R.array.edu_list, android.R.layout.simple_spinner_item);
        countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_list, android.R.layout.simple_spinner_item);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_item);

        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountEdu.setAdapter(eduAdapter);
        accountCountry.setAdapter(countryAdapter);
        accountGender.setAdapter(genderAdapter);
        if(MainActivity.langStr.equals("ar")){
            msg="هل تريد تحديث الملف الشخصي الخاص بك؟";
            yes="نعم";
            no="لا";
        }else{
            msg="Do you want to update your profile?";
            yes="Yes";
            no="No";
        }
        findViewById(R.id.updateAccountInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AccountActivity.this, UserMoreActivity.class));
                            }
                        })
                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
//        //--------------------------------------*-------------------------------------*
        Picasso.get().load(MainActivity.MainLink + "avatar/" + shared_getData.getString("imgCode", "")).into(accountAvatar);
        accountFullName.setText(shared_getData.getString("fullname", ""));
        accountUsername.setText(shared_getData.getString("username", ""));
        accountEmail.setText(shared_getData.getString("email", ""));
        accountAge.setText(shared_getData.getString("birthday", ""));
//        accountEdu.setAdapter(shared_getData.getString("education", ""));
//        accountGender.setText(shared_getData.getString("gender", ""));
//        accountCountry.setText(shared_getData.getString("country", ""));
        accountPassword.setText(shared_getData.getString("password", ""));
    }
}