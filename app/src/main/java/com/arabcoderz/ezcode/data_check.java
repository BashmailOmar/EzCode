package com.arabcoderz.ezcode;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class data_check extends StringRequest {


    private static final String url = "http://192.168.1.13/EzAppPHP/data_check.php";
    private Map<String, String> MapData; //


    public data_check(String username, String password, Response.Listener<String> listener) {
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

