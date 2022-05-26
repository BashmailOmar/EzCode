package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendArticles extends StringRequest {
    private  static final String url = MainActivity.MainLink + "SendArticles.php";
    private Map<String, String> MapData; // متغير لإرسال البيانات

    public SendArticles(String title, String content, String userName, Response.Listener<String> listener){
        super(Request.Method.POST,url,listener,null);
        MapData = new HashMap<>();
        MapData.put("title",title);
        MapData.put("content",content);
        MapData.put("userName",userName);
    }
    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
