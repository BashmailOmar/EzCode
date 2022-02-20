package com.arabcoderz.ezcode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Register extends StringRequest {

    private  static final String url = "http://192.168.1.13/EzAppPHP/data_send.php";
    private Map<String, String> MapData; // متغير لإرسال البيانات

    public Send_Data_Register(String full_user_name, String user_name,String email,
                              String password,String ImgCode,String date,String edu,String country,String gender,
                              Response.Listener<String> listener){
        super(Request.Method.POST,url,listener,null);
        MapData = new HashMap<>();
        MapData.put("full_user_name",full_user_name);
        MapData.put("user_name",user_name);
        MapData.put("email",email);
        MapData.put("password",password);
        MapData.put("ImgCode",ImgCode);
        MapData.put("date",date);
        MapData.put("edu",edu);
        MapData.put("country",country);
        MapData.put("gender",gender);
    }
    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
