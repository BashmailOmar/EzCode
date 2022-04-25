package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Data_send_challenge extends StringRequest {

    private static final String url = MainActivity.MainLink + "send_data_challenge.php";
    private Map<String, String> MapData;


    public Data_send_challenge(String username, int idChallenge, String programming_language, String points, String imgCode, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);

        MapData = new HashMap<>();
        MapData.put("username", username);
        MapData.put("programming_language", programming_language);
        MapData.put("imgCode", imgCode);
        MapData.put("idChallenge", String.valueOf(idChallenge));
        MapData.put("points", points);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
