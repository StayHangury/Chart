package com.niujunjie.www.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.niujunjie.www.chart.chart.LineChart;
import com.niujunjie.www.chart.chart.Params;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LineChart chart;
    private String[] xVal = {"01-01", "01-02", "01-03", "01-04",
            "01-05", "01-06", "01-07", "01-08", "01-09", "01-10",
            "01-11", "01-12", "01-13", "01-14", "01-15", "01-16",
            "01-17", "01-18", "01-19", "01-20", "01-21", "01-22",
            "01-23", "01-24", "01-25", "01-26", "01-27", "01-28",
            "01-29", "01-30", "01-31", "01-32", "01-33", "01-34"};

    private Float[] lineValue = {1000f, 1800f, 2400f, 5500f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f};

    private Float[] lineValue2 = {1000f, 2000f, 3000f, 4000f,
            5000f, 6000f, 7000f, 8000f, 9000f, 10000f,
            11000f, 12000f, 13000f, 14000f, 15000f, 16000f,
            17000f, 18000f, 19000f, 20000f, 19000f, 18000f,
            17000f, 16000f, 15000f, 14000f, 13000f, 12000f,
            11000f, 10000f, 9000f, 8000f, 7000f, 6000f};

    private String[] yVal = {"0-", "5000-", "10000-", "15000-", "20000-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);
        Map map = new HashMap(16);
        List data = Arrays.asList(lineValue);
        List data2 = Arrays.asList(lineValue2);
        map.put(0, data);
        map.put(1, data2);
        Params params = new Params(getBaseContext());
        params.setxLables(Arrays.asList(xVal))
                .setYMaxValue(20000)
                .setYMinValue(0)
                .setyLables(Arrays.asList(yVal))
                .setData(map);

        chart.setParams(params);
        chart.setData();
    }
}
