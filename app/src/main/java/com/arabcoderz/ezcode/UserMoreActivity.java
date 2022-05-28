package com.arabcoderz.ezcode;

import static com.arabcoderz.ezcode.MainActivity.MainLink;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMoreActivity extends AppCompatActivity {

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    TextView langButtonTextMore, fullnameTextView, usernameTextView;
    CircleImageView avatarImage;
    private AlertDialog.Builder builder;
    String soon,msg, yes, no, logout, langMore;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more);

        String KEY_PREF_NAME = "userData";
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);

        builder = new AlertDialog.Builder(this);
        editor = shared_getData.edit();

        fullnameTextView = findViewById(R.id.fullnameMorePage);
        fullnameTextView.setText(shared_getData.getString("fullname", ""));

        usernameTextView = findViewById(R.id.usernameMorePage);
        usernameTextView.setText(shared_getData.getString("username", ""));

        avatarImage = findViewById(R.id.avatar_in_account);
        Picasso.get().load(MainActivity.MainLink + "avatar/" + shared_getData.getString("imgCode", "")).into(avatarImage);

        langButtonTextMore = findViewById(R.id.langButtonTextMore);

        if (MainActivity.langStr.equals("ar")) {
//            deleteMsg = "هل انت متأكد انك تريد حذف الحساب ؟";
            msg = "هل انت متأكد انك تريد تسجيل الخروج؟";
            yes = "نعم";
            no = "لا";
            logout = "تم تسجيل الخروج بنجاح";
            soon="ستتم اتاحة هذه الخدمة قريبا";
        } else {
//            deleteMsg = "Are you sure you want to delete your account?";
            msg = "Are you sure you want to log out?";
            yes = "Yes";
            no = "No";
            logout = "logout success";
            soon="This service will be available soon";
        }
        findViewById(R.id.deleteMyAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AccountActivity.this, ChangePasswordActivity.class));
                Toast.makeText(UserMoreActivity.this, soon, Toast.LENGTH_SHORT).show();
            }
        });
//        findViewById(R.id.deleteMyAccount).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                builder.setMessage(deleteMsg)
//                        .setCancelable(true)
//                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST, MainLink + "DeleteAccount.php",
//                                        response -> Toast.makeText(UserMoreActivity.this, "success", Toast.LENGTH_SHORT).show(),
//                                        error -> Toast.makeText(UserMoreActivity.this, "error", Toast.LENGTH_SHORT).show()) {
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        Map<String, String> parms = new HashMap<>();
//                                        parms.put("something", shared_getData.getString("id", ""));
//                                        return parms;
//                                    }
//                                };
//                                requestQueue = Volley.newRequestQueue(UserMoreActivity.this);
//                                requestQueue.add(stringRequest);
//                                editor = shared_getData.edit();
//                                editor.clear();
//                                editor.apply();
//                                finish();
//                            }
//                        })
//                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                            }
//                        }).show();
//            }
//        });

        findViewById(R.id.statsButtonMore).setOnClickListener(new View.OnClickListener() { // إحصائيات
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, StatsActivity.class));
            }
        });

        findViewById(R.id.But_LogOut).setOnClickListener(new View.OnClickListener() { // زر الخروج
            @Override
            public void onClick(View view) {
                builder.setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                langMore = MainActivity.langStr;
                                editor = shared_getData.edit();
                                editor.clear();
                                editor.apply();
                                Toast.makeText(UserMoreActivity.this, logout, Toast.LENGTH_SHORT).show();
                                editor.putString("language", langMore);
                                editor.apply();
                                startActivity(new Intent(UserMoreActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });

        findViewById(R.id.myArticles).setOnClickListener(new View.OnClickListener() { // مقالاتي
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, MyArticlesActivity.class));
            }
        });

        findViewById(R.id.langButtonMore).setOnClickListener(new View.OnClickListener() { // تغير اللغة
            @Override
            public void onClick(View view) {
                if (langButtonTextMore.getText().toString().equals("ENGLISH")) {
                    setApplicationLocale("");
                    MainActivity.langStr = "";
                } else {
                    setApplicationLocale("ar");
                    MainActivity.langStr = "ar";
                }
                editor.putString("language", MainActivity.langStr);
                editor.apply();
                finish();
                overridePendingTransition(5, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 5);
            }
        });

        findViewById(R.id.rankButtonMore).setOnClickListener(new View.OnClickListener() { // زر التصنيف
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, RankActivity.class));
            }
        });

        findViewById(R.id.accountButtonMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, AccountActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(), UserNewsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(), UserChallengesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.articles:
                        startActivity(new Intent(getApplicationContext(), UserArticlesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.more:
                        return true;
                }
                return false;
            }
        });
        getEducationInfo("account_education");
        getGenderInfo("account_gender");
        getCountryInfo("account_country");
    } // end onCreate

    void setApplicationLocale(String locale) {//هذي الميثود اللي نستخدمها عشان نغير لغة التطبيق
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

    void getEducationInfo(String something) {
        String url = MainActivity.MainLink + "stats.php?something=" + something;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            editor = shared_getData.edit();
                            JSONArray jsonArray = response.getJSONArray("query");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject resp = jsonArray.getJSONObject(i);
                                String edu = resp.getString("account_education");
                                switch (edu) {
                                    case "Less than secondary":
                                        editor.putString("less_than_secondary", resp.getString("number"));
                                        break;
                                    case "Secondary":
                                        editor.putString("secondary", resp.getString("number"));
                                        break;
                                    case "Intermediate diploma":
                                        editor.putString("intermediate_diploma", resp.getString("number"));
                                        break;
                                    case "Bachelor and Above":
                                        editor.putString("bachelor_and_above", resp.getString("number"));
                                        break;
                                    default:
                                        break;
                                }
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات المقالات
        requestQueue.add(jsonObjectRequest);
    }

    void getGenderInfo(String something) {
        String url = MainActivity.MainLink + "stats.php?something=" + something;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            editor = shared_getData.edit();
                            JSONArray jsonArray = response.getJSONArray("query");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject resp = jsonArray.getJSONObject(i);
                                String gen = resp.getString("account_gender");
                                switch (gen) {
                                    case "Male":
                                        editor.putString("male", resp.getString("number"));
                                        break;
                                    case "Female":
                                        editor.putString("female", resp.getString("number"));
                                        break;
                                    default:
                                        break;
                                }
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات المقالات
        requestQueue.add(jsonObjectRequest);
    }

    void getCountryInfo(String something) {
        String url = MainActivity.MainLink + "CountryStats.php?something=" + something;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            editor = shared_getData.edit();
                            JSONArray jsonArray = response.getJSONArray("query");
                            int allN = 0;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject resp = jsonArray.getJSONObject(i);

                                switch (i) {
                                    case 0:
                                        editor.putString("firstcountry_name", resp.getString("account_country"));
                                        editor.putString("firstcountry_number", resp.getString("number"));
                                        break;
                                    case 1:
                                        editor.putString("secondcountry_name", resp.getString("account_country"));
                                        editor.putString("secondcountry_number", resp.getString("number"));
                                        break;
                                    case 2:
                                        editor.putString("thirdcountry_name", resp.getString("account_country"));
                                        editor.putString("thirdcountry_number", resp.getString("number"));
                                        break;
                                    case 3:
                                        editor.putString("forthcountry_name", resp.getString("account_country"));
                                        editor.putString("forthcountry_number", resp.getString("number"));
                                        break;
                                    default:
                                        int n = Integer.parseInt(resp.getString("number"));
                                        allN = allN + n;
                                        editor.putString("othercountry_number", String.valueOf(allN));
                                        break;
                                }

                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات المقالات
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {// وضع الصوره في الاطار الفريم
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            avatarImage.setImageURI(uri); // وضع الصورة في الفريم
        }
    }

    @Override
    public void onBackPressed() {// تعطيل زر الرجوع حق الجوال
        return;
    }
}