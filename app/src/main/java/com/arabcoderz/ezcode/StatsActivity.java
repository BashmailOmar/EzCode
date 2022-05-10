package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    PieChart eduPieCharts, genderPieCharts, countryPieCharts;
    String age, ageDescription, eduDescription, genderDescription, countryDescription,
            eduLv1, eduLv2, eduLv3, eduLv4, male, female;
    final int[] myColors = {
            Color.rgb(0, 139, 139),
            Color.rgb(100, 149, 237),
            Color.rgb(147, 112, 219),
            Color.rgb(205, 133, 63),
            Color.rgb(128, 128, 128),
            Color.rgb(255, 127, 80),
            Color.rgb(189, 183, 107),
            Color.rgb(147, 112, 219),
            Color.rgb(255, 182, 193),
            Color.rgb(160, 82, 45),
            Color.rgb(46, 139, 87),
            Color.rgb(255, 228, 181),
            Color.rgb(211, 211, 211),
    };
    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        findViewById(R.id.backBtnStats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatsActivity.this, UserMoreActivity.class));
            }
        });
        //--------------------------------------------------------------
        if (shared_getData.getString("language", "").equals("ar")) {//غيرو وخليه بالشاريد بريفرينسيز
            ageDescription = "متوسط الاعمار";
            eduDescription = "المستوى التعليمي";
            genderDescription = "نوع المستخدمين";
            countryDescription = "دول المستخدمين";
            //*----------------------------*
            age = "العمر";
            eduLv1 = "أقل من الثانوي";
            eduLv2 = "ثانوي";
            eduLv3 = "دبلوم متوسط";
            eduLv4 = "بكالوريوس فأعلى";
            male = "ذكر";
            female = "انثى";
            //*----------------------------*

        } else {
            ageDescription = "Average age";
            eduDescription = "Educational level";
            genderDescription = "Users' gender";
            countryDescription = "Users' country";
            //*----------------------------*
            age = "Age";
            eduLv1 = "Less than secondary";
            eduLv2 = "Secondary";
            eduLv3 = "Intermediate diploma";
            eduLv4 = "Bachelor and Above";
            male = "Male";
            female = "Female";
            //*----------------------------*
        }
        //--------------------------------------------------------------
        eduPieCharts = findViewById(R.id.eduPieChart);
        genderPieCharts = findViewById(R.id.genderPieChart);
        countryPieCharts = findViewById(R.id.countryPieChart);
        //--------------------------------------------------------------
        //--------------------------------------------------------------
        getEduInfo();
        getGenderInfo();
        getCountryInfo();
    }
//    void getAgeInfo() {
//        ArrayList<PieEntry> PieChart = new ArrayList<>();
//        PieChart.add(new PieEntry(1028, age + " < 18"));
//        PieChart.add(new PieEntry(1741, "17 < " + age + " < 28"));
//        PieChart.add(new PieEntry(344, age + " > 27"));
//        PieDataSet PieDataSet = new PieDataSet(PieChart, "");
//        PieDataSet.setColors(ColorTemplate.createColors(myColors));
//        PieDataSet.setValueTextColor(Color.WHITE);
//        PieDataSet.setValueTextSize(18f);
//        PieData PieData = new PieData(PieDataSet);
//        agePieCharts.setData(PieData);
//        agePieCharts.getDescription().setEnabled(false);
//        agePieCharts.setCenterText(ageDescription);
//        agePieCharts.setCenterTextSize(20);
//        agePieCharts.animateY(1500);
//    }
    public void getEduInfo() {
        int edu1 = Integer.parseInt(shared_getData.getString("less_than_secondary", ""));
        int edu2 = Integer.parseInt(shared_getData.getString("secondary", ""));
        int edu3 = Integer.parseInt(shared_getData.getString("intermediate_diploma", ""));
        int edu4 = Integer.parseInt(shared_getData.getString("bachelor_and_above", ""));

        ArrayList<PieEntry> PieChart = new ArrayList<>();
        PieChart.add(new PieEntry(edu1, eduLv1));
        PieChart.add(new PieEntry(edu2, eduLv2));
        PieChart.add(new PieEntry(edu3, eduLv3));
        PieChart.add(new PieEntry(edu4, eduLv4));
        PieDataSet PieDataSet = new PieDataSet(PieChart, "");
        PieDataSet.setColors(ColorTemplate.createColors(myColors));
        PieDataSet.setValueTextColor(Color.WHITE);
        PieDataSet.setValueTextSize(18f);
        PieData PieData = new PieData(PieDataSet);
        eduPieCharts.setData(PieData);
        eduPieCharts.getDescription().setEnabled(false);
        eduPieCharts.setCenterText(eduDescription);
        eduPieCharts.setCenterTextSize(20);
        eduPieCharts.animateY(2000);
    }
    void getGenderInfo() {
        int gen1 = Integer.parseInt(shared_getData.getString("male", ""));
        int gen2 = Integer.parseInt(shared_getData.getString("female", ""));

        ArrayList<PieEntry> PieChart = new ArrayList<>();
        PieChart.add(new PieEntry(gen1, male));
        PieChart.add(new PieEntry(gen2, female));
        PieDataSet PieDataSet = new PieDataSet(PieChart, "");
        PieDataSet.setColors(ColorTemplate.createColors(myColors));
        PieDataSet.setValueTextColor(Color.WHITE);
        PieDataSet.setValueTextSize(18f);
        PieData PieData = new PieData(PieDataSet);
        genderPieCharts.setData(PieData);
        genderPieCharts.getDescription().setEnabled(false);
        genderPieCharts.setCenterText(genderDescription);
        genderPieCharts.setCenterTextSize(20);
        genderPieCharts.animateY(2500);
    }
    void getCountryInfo() {
        String conName1 = getCountryName(shared_getData.getString("firstcountry_name", ""));
        String conName2 = getCountryName(shared_getData.getString("secondcountry_name", ""));
        String conName3 = getCountryName(shared_getData.getString("thirdcountry_name", ""));
        String conName4 = getCountryName(shared_getData.getString("forthcountry_name", ""));
        String conName5 = getCountryName("Other");

        //-----------------------------------------------------------------------------------------*
        int conNum1 = Integer.parseInt(shared_getData.getString("firstcountry_number", ""));
        int conNum2 = Integer.parseInt(shared_getData.getString("secondcountry_number", ""));
        int conNum3 = Integer.parseInt(shared_getData.getString("thirdcountry_number", ""));
        int conNum4 = Integer.parseInt(shared_getData.getString("forthcountry_number", ""));
        int conNum5 = Integer.parseInt(shared_getData.getString("othercountry_number", ""));

        ArrayList<PieEntry> PieChart = new ArrayList<>();
        PieChart.add(new PieEntry(conNum1, conName1));
        PieChart.add(new PieEntry(conNum2, conName2));
        PieChart.add(new PieEntry(conNum3, conName3));
        PieChart.add(new PieEntry(conNum4, conName4));
        PieChart.add(new PieEntry(conNum5, conName5));
        PieDataSet PieDataSet = new PieDataSet(PieChart, "");
        PieDataSet.setColors(ColorTemplate.createColors(myColors));
        PieDataSet.setValueTextColor(Color.WHITE);
        PieDataSet.setValueTextSize(18f);
        PieData PieData = new PieData(PieDataSet);
        countryPieCharts.setData(PieData);
        countryPieCharts.getDescription().setEnabled(false);
        countryPieCharts.setCenterText(countryDescription);
        countryPieCharts.setCenterTextSize(20);
        countryPieCharts.animateY(3000);
    }
    String getCountryName(String countryName) {
        String finalCountryName = countryName;
        if (shared_getData.getString("language", "").equals("ar")) {
            switch (countryName) {
                case "Saudi Arabia":
                    finalCountryName = "المملكة العربية السعودية";
                    break;
                case "Yemen":
                    finalCountryName = "اليمن";
                    break;
                case "Tunisia":
                    finalCountryName = "تونس";
                    break;
                case "Egypt":
                    finalCountryName = "مصر";
                    break;
                case "Sudan":
                    finalCountryName = "السودان";
                    break;
                case "Somalia":
                    finalCountryName = "الصومال";
                    break;
                case "Algeria":
                    finalCountryName = "الجزائر";
                    break;
                case "Morocco":
                    finalCountryName = "المغرب";
                    break;
                case "Iraq":
                    finalCountryName = "العراق";
                    break;
                case "Syria":
                    finalCountryName = "سوريا";
                    break;
                case "Libya":
                    finalCountryName = "ليبيا";
                    break;
                case "Jordan":
                    finalCountryName = "الاردن";
                    break;
                case "Emirates":
                    finalCountryName = "الامارات";
                    break;
                case "Lebanon":
                    finalCountryName = "لبنان";
                    break;
                case "Mauritania":
                    finalCountryName = "موريتانيا";
                    break;
                case "Kuwait":
                    finalCountryName = "الكويت";
                    break;
                case "Oman":
                    finalCountryName = "عمان";
                    break;
                case "Qatar":
                    finalCountryName = "قطر";
                    break;
                case "Jubbuti":
                    finalCountryName = "جيبوتي";
                    break;
                case "Bahrain":
                    finalCountryName = "البحرين";
                    break;
                case "Union of Comoros":
                    finalCountryName = "جزر القمر";
                    break;
                case "Palestine":
                    finalCountryName = "فلسطين";
                    break;
                case "Other":
                    finalCountryName = "اخرى";
                    break;
                default:
                    break;
            }
        }
        return finalCountryName;
    }
}