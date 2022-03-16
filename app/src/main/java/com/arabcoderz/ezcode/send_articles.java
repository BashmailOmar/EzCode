package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class send_articles  extends StringRequest {
    private  static final String url = MainActivity.MainLink + "add_article.php";
    private Map<String, String> MapData; // متغير لإرسال البيانات

    public send_articles(String title,String content,String userName,Response.Listener<String> listener){
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
