package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewContextChallenges extends AppCompatActivity {

    private TextView viewQuestion,viewQuestionTitle;
    private EditText userAnswer;
    private Button checkAnswer;
    public static String answer, point, programming_language;

    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_context_challenges);
        findViewById(R.id.butBackSolveChallenges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewContextChallenges.this, UserChallengesActivity.class));
            }
        });


        userAnswer = findViewById(R.id.enterAnswer);
        viewQuestion = findViewById(R.id.contextViewCha);
        viewQuestionTitle = findViewById(R.id.titleInChallengeContext);
        checkAnswer = findViewById(R.id.checkBut);

        showQuestion(RecyclerViewAdapterChallenges.index);
        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    } //  end showQuestion
    @Override
    public void onBackPressed() {
        return;
    }
    public void showQuestion(int id) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "ViewContextChallenge.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_usersS = jsonResponse.getJSONArray("All_challenge");
                            String str = "challenge_title_en";
                            for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                if (MainActivity.langStr.equals("ar")) str = "challenge_title_ar";
                                String challenge_content = responsS.getString("challenge_content");
                                answer = responsS.getString("challenge_answer");//  بأخذ المعلومات من صفحة php من نص sql
                                point = responsS.getString("challenge_points");
                                programming_language = responsS.getString("challenge_programming_language");
                                viewQuestionTitle.setText(responsS.getString(str));
                                viewQuestion.append(challenge_content);
                            }

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
    }// end showQuestion

    private void checkAnswer() {
        String user = userAnswer.getText().toString();
        checkAnswer.setEnabled(false);

        if (answer.equals(user)) {
            sendAnswer();
        } else {
            Toast.makeText(this, "try again", Toast.LENGTH_LONG).show();
            checkAnswer.setEnabled(true);
        }
    }//end checkAnswer

    private void sendAnswer() {
        int idChallenge = RecyclerViewAdapterChallenges.index;
        Response.Listener<String> responseLisener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String success = jsonObject.getString("success");

                    if (success.contains("Reg_ok")) {
                        Toast.makeText(ViewContextChallenges.this, "good answer", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ViewContextChallenges.this, UserChallengesActivity.class);
                        startActivity(intent);
                    } else if (success.contains("Error")) {
                        Toast.makeText(ViewContextChallenges.this, "عذرا حدث خطأ لم يتم إرسال البيانات", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE); // اسم الملف الذي يحتوي المعلومات
        String userName = shared_getData.getString("username", ""); // استدعاء القيم عن طريقة المفتاح
        String img = shared_getData.getString("imgCode", "");
        Data_send_challenge send_challenge = new Data_send_challenge(userName, idChallenge, programming_language, point, img, responseLisener); // ارسال القيم الى صفحة التواصل بين السيرفر و التطبيق
        RequestQueue queue = Volley.newRequestQueue(ViewContextChallenges.this);
        queue.add(send_challenge);
    }//end sendAnswer
}