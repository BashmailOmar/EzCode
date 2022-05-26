package com.arabcoderz.ezcode;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendLogin extends StringRequest {


    private static final String url = MainActivity.MainLink + "SendLogin.php";
    private Map<String, String> MapData; //


    public SendLogin(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

        MapData = new HashMap<>();
        MapData.put("username", username);
        MapData.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}

