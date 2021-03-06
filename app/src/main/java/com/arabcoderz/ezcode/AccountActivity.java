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
    private AlertDialog.Builder builder;
    private EditText accountFullName, accountUsername, accountEmail, accountPassword;

    private String msg;
    private String yes;
    private String no;


    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";
    private Button butUpdate;
    //نهاية تعريف القيمة  (end value definition)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        String soon;
        if (MainActivity.langStr.equals("ar")){
            soon="ستتم اتاحة هذه الخدمة قريبا";
        }else{
            soon="This service will be available soon";
        }
        findViewById(R.id.changePasswordMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AccountActivity.this, ChangePasswordActivity.class));
                Toast.makeText(AccountActivity.this, soon, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.backBtnAccount).setOnClickListener(new View.OnClickListener() { // الرجوع للخلف عن طريقة الزر
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, UserMoreActivity.class));
            }
        });

        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)

        //نعريف الحقول و ربطه
        accountAvatar = findViewById(R.id.pick_avatar_account);
        accountFullName = findViewById(R.id.full_user_name_account);
        accountUsername = findViewById(R.id.user_name_account);
        accountEmail = findViewById(R.id.emil_account);

        TextView accountAge = findViewById(R.id.Select_date_account);
        Spinner accountEdu = findViewById(R.id.eduSpinner_account);
        Spinner accountGender = findViewById(R.id.genderSpinner_account);
        Spinner accountCountry = findViewById(R.id.countrySpinner_account);

        accountPassword = findViewById(R.id.password_account);
        builder = new AlertDialog.Builder(this);

        // اضافة شرح
        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter.createFromResource(this, R.array.edu_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_item);

        // اضافة شرح
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountEdu.setAdapter(eduAdapter);
        accountCountry.setAdapter(countryAdapter);
        accountGender.setAdapter(genderAdapter);

        // شرط التحقق من لغة التطبيق
        if (MainActivity.langStr.equals("ar")) {
            msg = "هل تريد تحديث الملف الشخصي الخاص بك؟";
            yes = "نعم";
            no = "لا";
        } else {
            msg = "Do you want to update your profile?";
            yes = "Yes";
            no = "No";
        }

        accountAvatar.setOnClickListener(new View.OnClickListener() { // التحقق من اعطاء صلحيت الوصول الى الصور
            @Override
            public void onClick(View view) {
                permission_photo();
            }
        });

        butUpdate = findViewById(R.id.updateAccountInfo);
        butUpdate.setOnClickListener(new View.OnClickListener() { // زر تحديث البيانات
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
        //--------------------------------------*-------------------------------------*
        Picasso.get().load(MainActivity.MainLink + "avatar/" + shared_getData.getString("imgCode", "")).into(accountAvatar);
        accountFullName.setText(shared_getData.getString("fullname", ""));
        accountUsername.setText(shared_getData.getString("username", ""));
        accountEmail.setText(shared_getData.getString("email", ""));
        accountAge.setText(shared_getData.getString("birthday", ""));
    }// end OnCreate

    public void permission_photo() {//طلب اذن الوصول الى الصور
        if (ActivityCompat.checkSelfPermission(AccountActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //هل الصلاحية تم الحصول عليها ام لا
            String[] Permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}; // الصلاحية لم يتم الحصول عليها
            ActivityCompat.requestPermissions(AccountActivity.this, Permissions, 0);
        } else {
            photo_viewer();
        }
    }//end permission_photo

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
    }// end onRequestPermissionsResult

    private void update() { // تحديث البيانات
        String newPassword = accountPassword.getText().toString();
        String full_user_name = accountFullName.getText().toString();
        String user_name = accountUsername.getText().toString().toLowerCase().trim();
        String user_email = accountEmail.getText().toString().trim();
        String fullnameMsg, usernameMsg, emailMsg, passwordMsg, successUpdate, failUpdate;

        if (shared_getData.getString("language", "").equals("ar")) { // التحقق من لغة التطبيق
            fullnameMsg = "الرجاء كتابة اسمك الكامل";
            usernameMsg = "اسم المستخدم يجب ان يتكون من 4 احرف على الاقل.\nايضا, يجب ان لايحتوي على :\n= + / | \" \' : ; \\";
            emailMsg = "الرجاء كتابة البريد الالكتروني بشكل صحيح";
            passwordMsg = "كلمة المرور خاطئة";
            successUpdate = "تم تحديث البيانات بنجاح";
            failUpdate = "حدث خطأ اثناء تحديث البيانات";
        } else {
            fullnameMsg = "Write your full name";
            usernameMsg = "Username should be more than 3 characters\nAlso should not contain :\n! @ # $ % ^ & * ( )\n= + / | \" \' : ; \\";
            emailMsg = "Write the correct email format";
            passwordMsg = "Incorrect password";
            successUpdate = "Data has been updated successfully";
            failUpdate = "An error occurred while updating the data";
        }

        // التحقق من تحقيق الشروط المطلوبه
        if (full_user_name.isEmpty()) {
            new RegisterActivity().showError(accountFullName, fullnameMsg);
        } else if (user_name.isEmpty() || user_name.length() < 3 || user_name.contains("!") || user_name.contains("@") || user_name.contains("#") || user_name.contains("$") || user_name.contains("%") || user_name.contains("^") || user_name.contains("&") || user_name.contains("*") || user_name.contains("(") || user_name.contains(")") || user_name.contains("=") || user_name.contains("+") || user_name.contains("/") || user_name.contains("|") || user_name.contains("\"") || user_name.contains("\'") || user_name.contains(";") || user_name.contains(":") || user_name.contains("\\")) {
            new RegisterActivity().showError(accountUsername, usernameMsg);
        } else if (user_email.isEmpty() || !user_email.contains("@") || !user_email.contains(".")) {
            new RegisterActivity().showError(accountEmail, emailMsg);
        } else {
            if (shared_getData.getString("password", "").equals(newPassword)) { // اذا كان يساوي نفس كلة السر يتم ارسال البيانات
                butUpdate.setEnabled(false); // اغلاق الزر التحديث
                Bitmap bitmap = ((BitmapDrawable) accountAvatar.getDrawable()).getBitmap(); //يخذ الصوره الموجوده داخل accountAvatar
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // خاص بحفظ البيانات في نظام الجافا
                bitmap.compress(Bitmap.CompressFormat.JPEG, MainActivity.pickImage, byteArrayOutputStream); // عمليت ضفط اقدر اتحكم في جودة الصور عن طريق تغير رقم pick اذا قل الرقم كانت الصورة سيئة
                String newImg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT); // تحويل الصوره الى نظام Base64 و String

                Response.Listener<String> responseLisener = new Response.Listener<String>() { // ارسال البيانات عن طريق جيسون و التحقق اذا تم العملة التحديث او لا
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String success = jsonObject.getString("success");
                            if (success.contains("Upd_ok")) { // تم عملية التحديث
                                Toast.makeText(AccountActivity.this, successUpdate, Toast.LENGTH_LONG).show();
                            } else if (success.contains("Error")) {// لم تتم عملية التحديث
                                Toast.makeText(AccountActivity.this, failUpdate, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                SendDateUpdate dataSend = new SendDateUpdate(newImg, shared_getData.getString("imgCode", ""), responseLisener); // ارسل البيانات
                RequestQueue queue = Volley.newRequestQueue(AccountActivity.this);
                queue.add(dataSend);
                startActivity(new Intent(AccountActivity.this, UserMoreActivity.class));
            } else {
                new RegisterActivity().showError(accountPassword, passwordMsg); // اظهار رسالة الحطأ
                butUpdate.setEnabled(true); // اعادة تشغيل الزر
            }
        }
    }// end update

    public void photo_viewer() {// طريقة فتح مستعرض الصور
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "title"), MainActivity.pickImage);
    }//end photo_viewer

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // وضع الصوره في الاطار الفريم
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            accountAvatar.setImageURI(uri); // وضع الصورة في الفريم
        }
    }//end onActivityResult

    @Override
    public void onBackPressed() { // زر الرجوع من الجوال
        startActivity(new Intent(AccountActivity.this, UserMoreActivity.class));
    }//end onBackPressed
}