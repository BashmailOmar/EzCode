package com.arabcoderz.ezcode;

import static com.arabcoderz.ezcode.MainActivity.MainLink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

public class UserHomeActivity extends AppCompatActivity {
    static String newsUrl = MainLink + "newsHome.php";
    static String challengesUrl = MainLink + "challenges.php";
    static String articlesUrl = MainLink + "articles.php";

    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userKEY";

    String link;
    TextView firstNewsTextView, firstArticleTextView, firstChallengesTitle,firstChallengesPoints,firstChallengesLevel,firstChallengesLanguage,homeUserName;
    ImageView NewsImageView,ArticlesImageView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        requestQueue = Volley.newRequestQueue(this);
        firstNewsTextView = findViewById(R.id.title_news);
        NewsImageView = findViewById(R.id.img_news);

        homeUserName = findViewById(R.id.homeUserName);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        homeUserName.setText(shared_getData.getString("enterUser",""));

        firstChallengesTitle = findViewById(R.id.challengeTitle);
        firstChallengesPoints= findViewById(R.id.challengePoints);
        firstChallengesLevel= findViewById(R.id.challengeLvl);
        firstChallengesLanguage= findViewById(R.id.challengeLang);

        firstArticleTextView = findViewById(R.id.tvArticle);
        ArticlesImageView = findViewById(R.id.imgArticle);

        JsonObjectRequest jsonObjectRequestNews = new JsonObjectRequest(Request.Method.GET, newsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("allnews");
                            JSONObject resp = jsonArray.getJSONObject(0);
                            String title = resp.getString("news_title");
                            String img = resp.getString("news_image");

                            link = resp.getString("news_link");
                            firstNewsTextView.setText(title);
                            Picasso.get().load(img).into(NewsImageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات الاخبار

        JsonObjectRequest jsonObjectRequestChallenges = new JsonObjectRequest(Request.Method.GET, challengesUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("allchallenges");
                            JSONObject resp = jsonArray.getJSONObject(0);
                            String title = resp.getString("challenge_title");
                            String points = resp.getString("challenge_points");
                            String level = resp.getString("challenge_level");
                            String language = resp.getString("challenge_language");
                            firstChallengesTitle.setText(title);
                            firstChallengesPoints.setText(points);
                            firstChallengesLevel.setText(level);
                            firstChallengesLanguage.setText(language);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات التحديات

        JsonObjectRequest jsonObjectRequestArticles= new JsonObjectRequest(Request.Method.GET, articlesUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("allarticles");
                            JSONObject resp = jsonArray.getJSONObject(0);
                            String id = resp.getString("article_id");
                            String title = resp.getString("article_title");
                            String img = resp.getString("article_img");
                            firstArticleTextView.setText(title);
                            Picasso.get().load(MainLink + "articlesImages/" + img + ".png").into(ArticlesImageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات المقالات
        requestQueue.add(jsonObjectRequestNews);
        requestQueue.add(jsonObjectRequestChallenges);
        requestQueue.add(jsonObjectRequestArticles);


        findViewById(R.id.NewsCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            }
        });

        findViewById(R.id.allNewsCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserNewsActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.allChallengesCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserChallengesActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.allArticlesCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserArticlesActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        //بدية البار السفلي
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //هنا كود التنقل بين الصفحات الخاصه بالبار السفلي
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
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
                        startActivity(new Intent(getApplicationContext(), UserMoreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        //نهاية البار السفلي
    }
}