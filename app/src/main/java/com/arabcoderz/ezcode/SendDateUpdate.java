package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendDateUpdate extends StringRequest {
    private static final String sendCommentURL = MainActivity.MainLink + "AccountUpdate.php";
    private Map<String, String> MapData; // متغير لإرسال البيانات

    public SendDateUpdate(String newImg, String oldName, Response.Listener<String> listener) {
        super(Request.Method.POST, sendCommentURL, listener, null);
        MapData = new HashMap<>();
        MapData.put("newImg", newImg);
        MapData.put("oldName", oldName);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }

}
