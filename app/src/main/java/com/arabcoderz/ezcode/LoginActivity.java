package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private String username = "", password = "";

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    private static final String KEY_PREF_NAME = "userData";
    RequestQueue requestQueue;

    private Button but_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.login_user_name);
        etPassword = findViewById(R.id.login_password);

        AutoLogin();

        findViewById(R.id.backBtnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.dontHaveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        findViewById(R.id.forgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        but_login = findViewById(R.id.login_btn);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }//onCreate

    private void AutoLogin() {
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)

        etUsername.setText(shared_getData.getString("username", "")); // طريقة استدعاء القيمة عن طريقة المفتاح
        etPassword.setText(shared_getData.getString("password", ""));

        if (!shared_getData.getString("username", "").equals("")){
            Login();
        }else {
            Toast.makeText(LoginActivity.this, "enter username", Toast.LENGTH_SHORT).show();
        }

    }

    void Login() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (username.isEmpty()) {
            new RegisterActivity().showError(etUsername, "enter username");
        }
        else if (password.isEmpty()) {
            new RegisterActivity().showError(etPassword, "enter password");
        } else {
            but_login.setEnabled(false);
            Response.Listener<String> respListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                            GetUserInfo(username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "username or password", Toast.LENGTH_SHORT).show();
                            but_login.setEnabled(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };
            data_check data_check = new data_check(username, password, respListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(data_check);
        }
    }

    void GetUserInfo(String username) {
        String accountInfoUrl = MainActivity.MainLink + "account_info.php?username="+ username;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, accountInfoUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
                            editor = shared_getData.edit();
                            JSONArray jsonArray = response.getJSONArray("allaccounts");
                            JSONObject resp = jsonArray.getJSONObject(0);
                            if (resp.getString("account_username").equals(username)) {
                                editor.putString("id", resp.getString("account_id"));
                                editor.putString("fullname", resp.getString("account_fullname"));
                                editor.putString("username", username);
                                editor.putString("email", resp.getString("account_email"));
                                editor.putString("password", password);
                                editor.putString("imgCode", resp.getString("account_avatar_code"));
                                editor.putString("education", resp.getString("account_education"));
                                editor.putString("country", resp.getString("account_country"));
                                editor.putString("gender", resp.getString("account_gender"));
                                editor.putString("birthday", resp.getString("account_registration_date"));
                                editor.putString("registerDate", resp.getString("register_date"));
                                editor.putString("language", MainActivity.langStr);
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات المقالات
        requestQueue.add(jsonObjectRequest);
    }
}