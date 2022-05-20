package com.arabcoderz.ezcode;

import static com.arabcoderz.ezcode.MainActivity.MainLink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    static String challengesUrl = MainLink + "challengesHome.php";
    static String articlesUrl = MainLink + "articlesHome.php";

//    private SharedPreferences shared_getData;
//    private static String KEY_PREF_NAME = "userKEY";

    String link;
    TextView firstNewsTextView, firstChallengesTitle, firstChallengesPoints, firstChallengesLevel, firstChallengesLanguage, articleTextViewWriter, articleTextViewDate, articleTextViewTitle, articleTextViewContent;
    ImageView NewsImageView;
    RequestQueue requestQueue;
    int idArt,idChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        //---------------------------------------------------------
        requestQueue = Volley.newRequestQueue(this);
        firstNewsTextView = findViewById(R.id.title_news);
        NewsImageView = findViewById(R.id.img_news);
        //---------------------------------------------------------
        firstChallengesTitle = findViewById(R.id.challengeTitle);
        firstChallengesPoints = findViewById(R.id.challengePoints);
        firstChallengesLevel = findViewById(R.id.challengeLvl);
        firstChallengesLanguage = findViewById(R.id.challengeLang);
        //---------------------------------------------------------
        articleTextViewWriter = findViewById(R.id.article_writer);
        articleTextViewDate = findViewById(R.id.article_date);
        articleTextViewTitle = findViewById(R.id.article_title);
        articleTextViewContent = findViewById(R.id.article_content);
        //---------------------------------------------------------

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
                            String idd = resp.getString("challenge_id");
                            idChall = Integer.valueOf(idd);
                            String str = "challenge_title_en";
                            if (MainActivity.langStr.equals("ar")) str = "challenge_title_ar";
                            String points = resp.getString("challenge_points");
                            String level = resp.getString("challenge_level");
                            String language = resp.getString("challenge_programming_language");
                            firstChallengesTitle.setText(resp.getString(str));
                            firstChallengesPoints.setText(points);
                            firstChallengesLevel.setText(level);
                            firstChallengesLanguage.setText(language);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));//جلب بيانات التحديات

        JsonObjectRequest jsonObjectRequestArticles = new JsonObjectRequest(Request.Method.GET, articlesUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("allarticles");
                            JSONObject resp = jsonArray.getJSONObject(0);
                            String idd = resp.getString("article_id");
                            idArt = Integer.valueOf(idd);
                            String writer = resp.getString("article_writer");
                            String date = resp.getString("article_date");
                            String title = resp.getString("article_title");
                            String content = resp.getString("article_content");
                            articleTextViewWriter.setText(writer);
                            articleTextViewDate.setText(date);
                            articleTextViewTitle.setText(title);
                            articleTextViewContent.setText(content);
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

        findViewById(R.id.challenge_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewAdapterChallenges.index = idChall;
                startActivity(new Intent(UserHomeActivity.this, ViewContextChallenges.class));
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

        findViewById(R.id.card_View_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticlesRecyclerViewAdapter.articleId = idArt;
                startActivity(new Intent(UserHomeActivity.this, ArticleContentActivity.class));
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