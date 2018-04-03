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
            "01-05", "01-06", "01-07", "01-08", "01-09", "01-10",
            "01-05", "01-06", "01-07", "01-08", "01-09", "01-10",
            "01-05", "01-06", "01-07", "01-08", "01-09", "01-10",
            "01-05", "01-06", "01-07", "01-08", "01-09", "01-10"};
    private Float[] lineValue = {1000f, 1800f, 2400f, 5500f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f,
            7600f, 9700f, 18000f, 0f, 20000f, 0f};
    private String[] yVal = {"0-", "5000-", "10000-", "15000-", "20000-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);
        Map map = new HashMap(16);
        List data = Arrays.asList(lineValue);
        map.put(0, data);
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
