package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
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

    private int pick = 100;
    private ImageView avatar_user;
    private EditText Edit_full_name, Edit_user_Name, Edit_email, Edit_password, Edit_Confirm_password;
    private TextView Text_select_Date;
    Button snad_data;
    private String encodimg;

    private String full_user_name, user_name, user_email, user_password, Confirm_password, date, edu, country, gender;
    private final Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private SharedPreferences shared_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatar_user = findViewById(R.id.pick_avatar);
        avatar_user.setImageResource(R.drawable.def_avatar);
        Edit_full_name = findViewById(R.id.full_user_name);
        Edit_user_Name = findViewById(R.id.user_name);
        Edit_email = findViewById(R.id.emil);
        Edit_password = findViewById(R.id.password);
        Edit_Confirm_password = findViewById(R.id.Edit_Confirm_password);
        Text_select_Date = findViewById(R.id.Select_date);
        snad_data = findViewById(R.id.but_snad_data);

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

        snad_data.setOnClickListener(new View.OnClickListener() {
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
        user_name = Edit_user_Name.getText().toString().trim();
        user_email = Edit_email.getText().toString().trim();
        user_password = Edit_password.getText().toString().trim();
        Confirm_password = Edit_Confirm_password.getText().toString().trim();
        date = Text_select_Date.getText().toString().trim();
        edu = eduSpinner.getSelectedItem().toString();
        country = countrySpinner.getSelectedItem().toString();
        gender = genderSpinner.getSelectedItem().toString();

        if (full_user_name.isEmpty()) {
            showError(Edit_full_name, "يجب كتابة اسمك الكامل");
        } else if (user_name.isEmpty() || user_name.length() < 3) {
            showError(Edit_user_Name, "يجب ان يكون اسم المستخدم اكثر من 3 أحرف");
        } else if (user_email.isEmpty() || !user_email.contains("@")) {
            showError(Edit_email, "الرجاء كتابة الايمل بالصيغه الصحيح");
        } else if (user_password.isEmpty() || user_password.length() < 8) {
            showError(Edit_password, "اكثر من 8");
        } else if (Confirm_password.isEmpty() || !Confirm_password.equals(user_password)) {
            showError(Edit_Confirm_password, "يجب كتابة نفس الباسورد يا حمار");
        } else if (date.isEmpty()) {
            Toast.makeText(this, "حط التاريخ يا جحش", Toast.LENGTH_LONG).show();
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

            snad_data.setEnabled(false);

            Response.Listener<String> responseLisener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String success = jsonObject.getString("success");
                        //   String UserKey = jsonObject.getString("UserKey");

                        // Log.d("UserKey================>",UserKey);

                        if (success.contains("ok")) {
                            Toast.makeText(RegisterActivity.this, "تم تسجيلك بنجاح", Toast.LENGTH_LONG).show(); //اظهار النص من صفحة php
                            //SharedPreferences.Editor editor = shared_save.edit();
                        } else {
                            Toast.makeText(RegisterActivity.this, "عذرا حدث خطأ لم يتم إرسال البيانات", Toast.LENGTH_SHORT).show();
                            snad_data.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Send_Data_Register dataSend = new Send_Data_Register(full_user_name, user_name, user_email, user_password, encodimg, date, edu, country, gender, responseLisener); // ارسل البيانات
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(dataSend);
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }


    //اظهار رسالة
    private void showError(EditText input, String text) {
        input.setError(text);
        input.requestFocus();
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




/*
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
 */