package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserNewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;
    private List<List_Item> listItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_news);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.news:
                        return true;
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(),UserChallengesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.articles:
                        startActivity(new Intent(getApplicationContext(),UserArticlesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(),UserMoreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView_dAdapter = new RecyclerViewAdapter(listItems, this);
        recyclerView.setAdapter(recyclerView_dAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == listItems.size() - 1) {
                    Get_All_News(listItems.get(listItems.size()-1).getId());
                }

            }
        });

        Get_All_News(0);

    }//end on Create

    private void Get_All_News(int limit) {

//contextViewCha  enterAnswer   checkBut
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "news.php?limit=" + limit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_usersS = jsonResponse.getJSONArray("All_News");

                            for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                JSONObject responsS = jsonArray_usersS.getJSONObject(i);

                                int id = responsS.getInt("news_id");
                                String title = responsS.getString("news_title");
                                String link = responsS.getString("news_link");
                                String img_link = responsS.getString("news_image");

                                listItems.add(new List_Item(id, title,link ,img_link));
                            }
                            recyclerView_dAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        stringRequest.setShouldCache(false);
    }

}



























/*
 <LinearLayout
        android:id="@+id/LayoutContextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/topBarChallenges"
        android:layout_marginTop="20dp"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:id="@+id/contextViewCha"
            android:textColor="@color/white"
            android:textSize="22sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/LayoutAnswer"
        android:layout_width="373dp"
        android:layout_height="76dp"
        android:layout_below="@+id/LayoutContextView"
        android:layout_marginLeft="19dp"
        android:background="@drawable/backgrund_solve_challenges"
        android:orientation="vertical">

        <EditText
            android:id="@+id/enterAnswer"
            android:layout_width="380dp"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:background="#99C4C4C4"
            android:gravity="center"
            android:hint="Enter The Answer Here"
            android:textColor="@color/white"
            android:textColorHighlight="@color/white"
            android:textColorHint="@color/white"
            android:textSize="18sp" />
    </LinearLayout>

    <Button
        android:id="@+id/checkBut"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/LayoutAnswer"
        android:layout_margin="40dp"
        android:text="Send The Answer" />


        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/LayoutText"
        android:layout_marginTop="15dp"
        android:orientation="horizontal">
            <EditText
                android:id="@+id/enterAnswer"
                android:layout_width="310dp"
                android:layout_height="55dp"
                android:background="@drawable/custom_edittext_underline"
                android:ems="20"
                android:layout_marginLeft="5dp"
                android:gravity="center"
                android:hint="Enter The Answer Here"
                android:textColor="@color/black"
                android:textColorHint="@color/black"
                android:textSize="18sp" />

            <Button
                android:id="@+id/checkBut"
                android:layout_width="wrap_content"
                android:layout_marginLeft="5dp"
                android:layout_height="wrap_content"
                android:background="@drawable/custom_edittext_underline"
                android:text="Send" />
        </LinearLayout>
 */