package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopTenActivity extends AppCompatActivity {
    static String url;
    //تم وضعها ستاتك لكي نستطيع تثبيت تغير القيمه بعد تحديث الصفحه
    public String place;
    ListView listPlaces;
    ArrayList<ListPlaces> listUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        if (url == null) {
            url = MainActivity.MainLink + "all_places.php";
        }//نتحقق من الرابط ان لم تكن لديه قيمه ف نعطيه القيمه الظاهره امامك لجلب مراكز المستخدمين
        //نتحقق من نوع اللغه ان لم تكن لديها قيمه ف نعطيها القيمه الظاهره امامك لجلب مراكز المستخدمين
        listPlaces = (ListView) findViewById(R.id.placesListTopTen);
        findViewById(R.id.backBtnTopTen).setOnClickListener(view -> {
            finish();
            overridePendingTransition(0, 0);
            url = null;
        });
        findViewById(R.id.javaBtnInTopBar).setOnClickListener(view -> {
            url = MainActivity.MainLink + "java_places.php";
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });//عند الضغط على كلمة جافا نغير الرابط ونوع اللغه البرمجية ومن ثم نحدث الصفحه وتطلع معانا القائمة الجديده
        findViewById(R.id.pythonBtnInTopBar).setOnClickListener(view -> {
            url = MainActivity.MainLink + "python_places.php";
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });//عند الضغط على كلمة بايثون نغير الرابط ونوع اللغه البرمجية ومن ثم نحدث الصفحه وتطلع معانا القائمه الجديده
        findViewById(R.id.jsBtnInTopBar).setOnClickListener(view -> {
            url = MainActivity.MainLink + "javascript_places.php";
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });//عند الضغط على كلمة جافاسكريبت نغير الرابط ونوع اللغه البرمجية ومن ثم نحدث الصفحه وتطلع معانا القائمة الجديده
        findViewById(R.id.allBtnInTopBar).setOnClickListener(view -> {
            url = MainActivity.MainLink + "all_places.php";
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });//عند الضغط على كلمة الكل نغير الرابط ونوع اللغه البرمجية ومن ثم نحدث الصفحه وتطلع معانا القائمه الجديده
        getAllUsers();
    }

    public void getAllUsers() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("places");
                            for (int i = 0; i < jsonArray.length() && i < 10; i++) {
                                JSONObject resp = jsonArray.getJSONObject(i);
                                place = Integer.toString(i + 1);//هنا عشان م نقدر نسحب رقم ترتيب المستخدم من قاعدة البيانات ف سويت هذا المتغير عشان يبدأ من الرقم 1
                                String username = resp.getString("account_username");
                                String point = resp.getString("points");
                                String img = resp.getString("account_avatar_code");
                                listUsers.add(new ListPlaces(place, username, point, img));
                                TopTenActivity.this.listAllItem();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("VOLLEY", "ERROR"));
        requestQueue.add(jsonObjectRequest);
    }
    public void listAllItem() {
        listAdpter lA = new listAdpter(listUsers);
        listPlaces.setAdapter(lA);
    }
    //هذي الميثودات خاصه بالادابتر. احنى مسوين بلوك معين واحد ونقعد نستدعيه ف القائمه لكل مستخدم
    class listAdpter extends BaseAdapter {
        ArrayList<ListPlaces> listA = new ArrayList<ListPlaces>();

        public listAdpter(ArrayList<ListPlaces> listA) {
            this.listA = listA;
        }

        @Override
        public int getCount() {
            return listA.size();
        }

        @Override
        public Object getItem(int position) {
            return listA.get(position).username;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;
            if (position > 2) {
                view = layoutInflater.inflate(R.layout.row_users_places, null);
            } else if (position == 0) {
                view = layoutInflater.inflate(R.layout.row_first_place, null);
            } else if (position == 1) {
                view = layoutInflater.inflate(R.layout.row_second_place, null);
            } else {
                view = layoutInflater.inflate(R.layout.row_third_place, null);
            }

            TextView place = (TextView) view.findViewById(R.id.placeInRowPlaces);
            TextView username = (TextView) view.findViewById(R.id.usernameInRowPlaces);
            TextView point = (TextView) view.findViewById(R.id.pointsInRowPlaces);
            ImageView img = (ImageView) view.findViewById(R.id.user_img);
            place.setText(listA.get(position).place);
            username.setText(listA.get(position).username);
            point.setText(listA.get(position).point);
            Picasso.get().load(MainActivity.MainLink + "avatar/" + listA.get(position).img).into(img);
            return view;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TopTenActivity.this, MainActivity.class);
        startActivity(intent);    }
}