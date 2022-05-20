package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

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
    private String msg,yes,no, newImg;
    private SharedPreferences shared_getData;
    private int pick = 100;
    private static final String KEY_PREF_NAME = "userData";
    private Button butUpdate;

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

        accountAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission_photo();
            }
        });
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)
        String a = (shared_getData.getString("imgCode", "")); // طريقة استدعاء القيمة عن طريقة المفتاح

        butUpdate = findViewById(R.id.updateAccountInfo);
        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                update();
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
//        accountPassword.setText(shared_getData.getString("password", ""));
    }// end OnCreate

    private void update(){
        String newPassword = accountPassword.getText().toString();
        butUpdate.setEnabled(false);
        if (shared_getData.getString("password","").equals(newPassword)){
            Bitmap bitmap = ((BitmapDrawable) accountAvatar.getDrawable()).getBitmap(); //يخذ الصوره الموجوده داخل image_View
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // خاص بحفظ البيانات في نظام الجافا
            bitmap.compress(Bitmap.CompressFormat.JPEG, pick, byteArrayOutputStream); // عمليت ضفط اقدر اتحكم في جودة الصور عن طريق تغير رقم 100 اذا قل الرقم كانت الصورة سيئة
            newImg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT); // تحويل الصوره الى نظام Base64 و String

            Response.Listener<String> responseLisener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String success = jsonObject.getString("success");
                        if (success.contains("Upd_ok")) {
                            Toast.makeText(AccountActivity.this, "update successful", Toast.LENGTH_LONG).show();
                        }else if (success.contains("Error")){
                            Toast.makeText(AccountActivity.this, "update not successful", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)
            String nameImg = (shared_getData.getString("imgCode", "")); // طريقة استدعاء القيمة عن طريقة المفتاح

            SendDateUpdate dataSend = new SendDateUpdate(newImg, nameImg, responseLisener); // ارسل البيانات
            RequestQueue queue = Volley.newRequestQueue(AccountActivity.this);
            queue.add(dataSend);
            Intent intent = new Intent(AccountActivity.this, UserMoreActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(AccountActivity.this, "password error", Toast.LENGTH_SHORT).show();
            butUpdate.setEnabled(true);
        }
    }
    //طلب اذن الوصول الى الصور
    public void permission_photo() {
        //هل الصلاحية تم الحصول عليها ام لا
        if (ActivityCompat.checkSelfPermission(AccountActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // الصلاحية لم يتم الحصول عليها
            String[] Permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(AccountActivity.this, Permissions, 0);
        } else {
            photo_viewer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AccountActivity.this, "تم اعطاء الصلاحية", Toast.LENGTH_LONG).show();
                    photo_viewer();
                } else {
                    Toast.makeText(AccountActivity.this, "لا توجد صلاحية الدخول للصور", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // طريقة فتح مستعرض الصور
    public void photo_viewer() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "title"), pick);
    }

    // وضع الصوره في الاطار الفريم
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            accountAvatar.setImageURI(uri); // وضع الصورة في الفريم
        }
    }
}