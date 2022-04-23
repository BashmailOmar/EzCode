package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

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

    private TextView viewQuestion;
    private EditText userAnswer;
    public static String answer,point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_context_challenges);

        userAnswer = findViewById(R.id.enterAnswer);
        viewQuestion = findViewById(R.id.contextViewCha);
        Button checkAnswer = findViewById(R.id.checkBut);

        showQuestion(RecyclerViewAdapterChallenges.index);

        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    }

    public void showQuestion(int id){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "ViewContextChallenge.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_usersS = jsonResponse.getJSONArray("All_challenge");

                            for (int i = 0; i < jsonArray_usersS.length(); i++) {

                                JSONObject responsS = jsonArray_usersS.getJSONObject(i);

                                String challenge_content = responsS.getString("challenge_content");
                                answer = responsS.getString("challenge_answer");
                                point = responsS.getString("challenge_points");
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
    }

    private void checkAnswer(){
        String user = userAnswer.getText().toString();

        if (answer.equals(user)){
            Toast.makeText(this, "good answer", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "try again", Toast.LENGTH_LONG).show();
        }
    }
}