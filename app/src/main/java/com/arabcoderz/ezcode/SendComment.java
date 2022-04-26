package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendComment extends StringRequest {
    private static final String sendCommentURL = MainActivity.MainLink + "add_comment.php";
    private Map<String, String> MapData; // متغير لإرسال البيانات

    public SendComment(String article, String comment_writer, String comment_content, Response.Listener<String> listener) {
        super(Request.Method.POST, sendCommentURL, listener, null);
        MapData = new HashMap<>();
        MapData.put("article", article);
        MapData.put("comment_writer", comment_writer);
        MapData.put("comment_content", comment_content);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
