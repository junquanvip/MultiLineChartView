package com.example.aron.multilinechartview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.aron.multilinechartview.utils.ClickUtils;
import com.example.aron.multilinechartview.view.BaseChartView;
import java.lang.ref.SoftReference;

/**
 * @author aron
 */
public class ChartViewShowActivity extends Activity {

    private static SoftReference<? extends View> sTransferView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (sTransferView != null && sTransferView.get() != null) {
            if (sTransferView.get().getParent() != null) {
                ((ViewGroup) sTransferView.get().getParent()).removeAllViews();
            }
            sTransferView.get().setOnClickListener(null);
            setContentView(R.layout.chart_view_show_activity);
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.chart_view_show_activity);
            findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            frameLayout.addView(sTransferView.get(), 0);
        }
    }

    public static void showView(BaseChartView view) {
        if (!ClickUtils.isClick()) {
            return;
        }
        sTransferView = new SoftReference<>(view.clone());
        jumpChartActivity(view);
    }


    private static void jumpChartActivity(View view) {
        Intent intent = new Intent(view.getContext(), ChartViewShowActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        if (!ClickUtils.isClick()) {
            return;
        }
        super.finish();
    }

}
