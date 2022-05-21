package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Spinner eduSpinner, countrySpinner, genderSpinner;
    private ArrayAdapter<CharSequence> eduAdapter;
    private ArrayAdapter<CharSequence> countryAdapter;
    private ArrayAdapter<CharSequence> genderAdapter;

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    private static final String KEY_PREF_NAME = "userData";

    private int pick = 100;
    private ImageView avatar_user;
    private EditText Edit_full_name, Edit_user_Name, Edit_email, Edit_password, Edit_Confirm_password;
    private TextView Text_select_Date;
    private Button send_data;
    private String encodimg;

    private String full_user_name, user_name, user_email, user_password, Confirm_password, date, edu, country, gender;
    private final Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        avatar_user = findViewById(R.id.pick_avatar);
        avatar_user.setImageResource(R.drawable.def_avatar);
        Edit_full_name = findViewById(R.id.full_user_name);
        Edit_user_Name = findViewById(R.id.user_name);
        Edit_email = findViewById(R.id.emil);
        Edit_password = findViewById(R.id.password);
        Edit_Confirm_password = findViewById(R.id.Edit_Confirm_password);
        Text_select_Date = findViewById(R.id.Select_date);
        send_data = findViewById(R.id.but_snad_data);
        eduSpinner = findViewById(R.id.eduSpinner);
        countrySpinner = findViewById(R.id.countrySpinner);
        genderSpinner = findViewById(R.id.genderSpinner);

        eduAdapter = ArrayAdapter.createFromResource(this, R.array.edu_list, android.R.layout.simple_spinner_item);
        countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_list, android.R.layout.simple_spinner_item);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_item);

        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eduSpinner.setAdapter(eduAdapter);
        countrySpinner.setAdapter(countryAdapter);
        genderSpinner.setAdapter(genderAdapter);

        avatar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission_photo();
            }
        });
        Text_select_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_date();
            }
        });
        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        findViewById(R.id.backBtnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.haveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        findViewById(R.id.conditions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, TermsAndConditionsActivity.class));
            }
        });
    }//end onCreate

    private void Register() {
        full_user_name = Edit_full_name.getText().toString();
        user_name = Edit_user_Name.getText().toString().toLowerCase().trim();
        user_email = Edit_email.getText().toString().trim();
        user_password = Edit_password.getText().toString().trim();
        Confirm_password = Edit_Confirm_password.getText().toString().trim();
        date = Text_select_Date.getText().toString().trim();
        edu = eduSpinner.getSelectedItem().toString();
        country = countrySpinner.getSelectedItem().toString();
        gender = genderSpinner.getSelectedItem().toString();
        String fullnameMsg, usernameMsg, emailMsg, passwordMsg, conPasswordMsg, dateMsg;
        if (shared_getData.getString("language","").equals("ar")) {
            fullnameMsg = "الرجاء كتابة اسمك الكامل";
            usernameMsg = "اسم المستخدم يجب ان يتكون من 4 احرف على الاقل.\nايضا, يجب ان لايحتوي على :\n= + / | \" \' : ; \\";
            emailMsg = "الرجاء كتابة البريد الالكتروني بشكل صحيح";
            passwordMsg = "يجب أن تكون كلمة المرور أكبر من 7 أحرف\n" +
                    "يجب أن تحتوي أيضًا على واحد من (حرف كبير / حرف صغير / أرقام)";
            conPasswordMsg = "يجب ان يتطابق مع كلمة المرور المدخله";
            dateMsg = "اختر يوم ميلادك";
        } else {
            fullnameMsg = "Write your full name";
            usernameMsg = "Username should be more than 3 characters\nAlso should not contain :\n! @ # $ % ^ & * ( )\n= + / | \" \' : ; \\";
            emailMsg = "Write the correct email format";
            passwordMsg = "Password should br grater than 7 characters\nAlso should consist of one of each (capital letter/ small letter/digits)";
            conPasswordMsg = "The same password must be entered";
            dateMsg = "Select your birthday";
        }
        if (full_user_name.isEmpty()) {
            showError(Edit_full_name, fullnameMsg);
        } else if (user_name.isEmpty() || user_name.length() < 3 || user_name.contains("!") || user_name.contains("@") || user_name.contains("#") || user_name.contains("$") || user_name.contains("%") || user_name.contains("^") || user_name.contains("&") || user_name.contains("*") || user_name.contains("(") || user_name.contains(")") || user_name.contains("=") || user_name.contains("+") || user_name.contains("/") || user_name.contains("|") || user_name.contains("\"") || user_name.contains("\'") || user_name.contains(";") || user_name.contains(":") || user_name.contains("\\")) {
            showError(Edit_user_Name, usernameMsg);
        } else if (user_email.isEmpty() || !user_email.contains("@") || !user_email.contains(".")) {
            showError(Edit_email, emailMsg);
        } else if (user_password.isEmpty() || user_password.length() < 8 || !(user_password.contains("A") || user_password.contains("B") || user_password.contains("C") || user_password.contains("D") || user_password.contains("E") || user_password.contains("F") || user_password.contains("G") || user_password.contains("H") || user_password.contains("I") || user_password.contains("J") || user_password.contains("K") || user_password.contains("L") || user_password.contains("M") || user_password.contains("N") || user_password.contains("O") || user_password.contains("P") || user_password.contains("Q") || user_password.contains("R") || user_password.contains("S") || user_password.contains("T") || user_password.contains("U") || user_password.contains("V") || user_password.contains("W") || user_password.contains("X") || user_password.contains("Y") || user_password.contains("Z")) || !(user_password.contains("a") || user_password.contains("b") || user_password.contains("c") || user_password.contains("d") || user_password.contains("e") || user_password.contains("f") || user_password.contains("g") || user_password.contains("h") || user_password.contains("i") || user_password.contains("j") || user_password.contains("k") || user_password.contains("l") || user_password.contains("m") || user_password.contains("n") || user_password.contains("o") || user_password.contains("p") || user_password.contains("q") || user_password.contains("r") || user_password.contains("s") || user_password.contains("t") || user_password.contains("u") || user_password.contains("v") || user_password.contains("w") || user_password.contains("x") || user_password.contains("y") || user_password.contains("z")) || !(user_password.contains("1") || user_password.contains("2") || user_password.contains("3") || user_password.contains("4") || user_password.contains("5") || user_password.contains("6") || user_password.contains("7") || user_password.contains("8") || user_password.contains("9"))) {
            showError(Edit_password, passwordMsg);
        } else if (Confirm_password.isEmpty() || !Confirm_password.equals(user_password)) {
            showError(Edit_Confirm_password, conPasswordMsg);
        } else if (date.isEmpty()) {
            Toast.makeText(this, dateMsg, Toast.LENGTH_LONG).show();
        } else if (edu.isEmpty()) {
            Toast.makeText(this, "*", Toast.LENGTH_LONG).show();
        } else if (country.isEmpty()) {
            Toast.makeText(this, "*", Toast.LENGTH_LONG).show();
        } else if (gender.isEmpty()) {
            Toast.makeText(this, "*", Toast.LENGTH_LONG).show();
        } else {
            Bitmap bitmap = ((BitmapDrawable) avatar_user.getDrawable()).getBitmap(); //يخذ الصوره الموجوده داخل image_View
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // خاص بحفظ البيانات في نظام الجافا
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // عمليت ضفط اقدر اتحكم في جودة الصور عن طريق تغير رقم 100 اذا قل الرقم كانت الصورة سيئة
            encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT); // تحويل الصوره الى نظام Base64 و String

            send_data.setEnabled(false);

            Response.Listener<String> responseLisener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String success = jsonObject.getString("success");
                        if (success.contains("pass")) {
                            Toast.makeText(RegisterActivity.this, "تم تسجيلك بنجاح", Toast.LENGTH_LONG).show(); //اظهار النص من صفحة php
                            editor = shared_getData.edit();
                            editor.putString("username", user_name); // تخزين القيمة في مفتاح
                            editor.putString("password", user_password);
                            editor.putString("imgCode", encodimg);
                            editor.putString("fullname", full_user_name);
                            editor.putString("email", user_email);
                            editor.putString("education", edu);
                            editor.putString("country", country);
                            editor.putString("gender", gender);
                            editor.apply();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "عذرا حدث خطأ لم يتم إرسال البيانات", Toast.LENGTH_LONG).show();
                            send_data.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            Send_Data_Register dataSend = new Send_Data_Register(full_user_name, user_name, user_email, user_password, encodimg, edu, country, gender, date, responseLisener); // ارسل البيانات
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(dataSend);
        }
    }

    //اظهار رسالة
    public RegisterActivity showError(EditText input, String text) {
        input.setError(text);
        input.requestFocus();
        return null;
    }

    //طلب اذن الوصول الى الصور
    public void permission_photo() {
        //هل الصلاحية تم الحصول عليها ام لا
        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // الصلاحية لم يتم الحصول عليها
            String[] Permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(RegisterActivity.this, Permissions, 0);
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
                    Toast.makeText(RegisterActivity.this, "تم اعطاء الصلاحية", Toast.LENGTH_LONG).show();
                    photo_viewer();
                } else {
                    Toast.makeText(RegisterActivity.this, "لا توجد صلاحية الدخول للصور", Toast.LENGTH_LONG).show();
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
            avatar_user.setImageURI(uri); // وضع الصورة في الفريم
        }
    }

    // طريقة اختيار التاريخ
    public void select_date() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = (month + 1) + "/" + day + "/" + year;
                Text_select_Date.setText(date);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        edu = adapterView.getItemAtPosition(i).toString();
        country = adapterView.getItemAtPosition(i).toString();
        gender = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}