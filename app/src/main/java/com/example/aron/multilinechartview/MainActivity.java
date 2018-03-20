package com.example.aron.multilinechartview;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.aron.multilinechartview.view.MultiLineChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MultiLineChartView mHeightChart;
    private List<String> mUnitText = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout rootView = findViewById(R.id.rootView);
        mHeightChart = new MultiLineChartView(this);
        mHeightChart.setTitle("身高变化趋势")
                .setMinValue(40)
                .setBottomText("年龄")
                .setLeftText("身高(cm)")
                .setTitleColor(0xFF666666)
                .setBackground(Color.WHITE);
        rootView.addView(mHeightChart);
    }
}
