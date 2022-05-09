package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Locale;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserMoreActivity extends AppCompatActivity {

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    private static String KEY_PREF_NAME = "userData";
    TextView langButtonTextMore, fullnameTextView, usernameTextView;
    ImageView avatarImage;
    private AlertDialog.Builder builder;
    private String msg, yes, no;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        builder = new AlertDialog.Builder(this);
        editor = shared_getData.edit();
        fullnameTextView = findViewById(R.id.fullnameMorePage);
        usernameTextView = findViewById(R.id.usernameMorePage);
        avatarImage = findViewById(R.id.avatar_in_account);
        Picasso.get().load(MainActivity.MainLink + "avatar/" + shared_getData.getString("imgCode", "")).into(avatarImage);
        fullnameTextView.setText(shared_getData.getString("fullname", ""));
        usernameTextView.setText(shared_getData.getString("username", ""));
        langButtonTextMore = findViewById(R.id.langButtonTextMore);
        if (MainActivity.langStr.equals("ar")) {
            msg = "هل انت متأكد انك تريد تسجيل الخروج؟";
            yes = "نعم";
            no = "لا";
        } else {
            msg = "Are you sure you want to log out?";
            yes = "Yes";
            no = "No";
        }
        findViewById(R.id.statsButtonMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, StatsActivity.class));
            }
        });
        findViewById(R.id.But_LogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editor = shared_getData.edit();
                                editor.clear();
                                editor.apply();
                                Toast.makeText(UserMoreActivity.this, "logout success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserMoreActivity.this, MainActivity.class);
                                startActivity(intent);
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
        findViewById(R.id.myArticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMoreActivity.this, MyArticlesActivity.class));
            }
        });
        findViewById(R.id.langButtonMore).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.rankButtonMore).setOnClickListener(new View.OnClickListener() {
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
    }

    void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }//هذي الميثود اللي نستخدمها عشان نغير لغة التطبيق

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
        String url = MainActivity.MainLink + "country_stats.php?something=" + something;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            editor = shared_getData.edit();
                            JSONArray jsonArray = response.getJSONArray("query");
                            int allN=0;
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

}