package com.minardwu.yiyue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minardwu.yiyue.R;

public class InfoActivity extends SampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_info;
    }

    @Override
    protected void setToolbarTitle(TextView left, TextView mid, TextView right) {
        super.setToolbarTitle(left, mid, right);
        left.setVisibility(View.GONE);
        mid.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
    }

}
