package com.minardwu.yiyue.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.minardwu.yiyue.R;
import com.minardwu.yiyue.application.AppCache;
import com.minardwu.yiyue.http.mock.MockData;
import com.minardwu.yiyue.utils.Preferences;
import com.minardwu.yiyue.utils.ToastUtils;
import com.minardwu.yiyue.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MinardWu
 * @date : 2018/5/15
 */

public class MockControllerActivity extends SampleActivity {

    private Switch sw_mock_data;
    private Switch sw_mock_search_data;
    private Switch sw_mock_artist_data;
    private Switch sw_mock_album_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_controler);
        sw_mock_data = findViewById(R.id.sw_mock_data);
        sw_mock_search_data = findViewById(R.id.sw_mock_search_data);
        sw_mock_artist_data = findViewById(R.id.sw_mock_artist_data);
        sw_mock_album_data = findViewById(R.id.sw_mock_album_data);

        sw_mock_data.setChecked(Preferences.enableUseMockData());
        sw_mock_search_data.setChecked(Preferences.enableUseMockSearchData());
        sw_mock_artist_data.setChecked(Preferences.enableUseMockArtistData());
        sw_mock_album_data.setChecked(Preferences.enableUseMockAlbumData());

        sw_mock_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_mock_data.isChecked()){
                    AppCache.getPlayOnlineMusicService().replaceMusicList(MockData.musicList);
                    ToastUtils.showShortToast("现在开始使用模拟歌曲数据");
                    Preferences.saveUseMockData(true);
                }else {
                    ToastUtils.showShortToast("取消使用模拟歌曲数据");
                    Preferences.saveUseMockData(false);
                }
            }
        });

        sw_mock_search_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_mock_search_data.isChecked()){
                    ToastUtils.showShortToast("现在开始使用模拟搜索数据");
                    Preferences.saveUseMockSearchData(true);
                }else {
                    ToastUtils.showShortToast("取消使用模拟搜索数据");
                    Preferences.saveUseMockSearchData(false);
                }
            }
        });

        sw_mock_artist_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_mock_artist_data.isChecked()){
                    ToastUtils.showShortToast("现在开始使用模拟歌手数据");
                    Preferences.saveUseMockArtistData(true);
                }else {
                    ToastUtils.showShortToast("取消使用模拟歌手数据");
                    Preferences.saveUseMockArtistData(false);
                }
            }
        });

        sw_mock_album_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_mock_album_data.isChecked()){
                    ToastUtils.showShortToast("现在开始使用模拟专辑数据");
                    Preferences.saveUseMockAlbumData(true);
                }else {
                    ToastUtils.showShortToast("取消使用模拟专辑数据");
                    Preferences.saveUseMockAlbumData(false);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mock_controler;
    }

    @Override
    protected void setToolbar(Toolbar toolbar) {
        super.setToolbar(toolbar);
        toolbar.setBackgroundColor(UIUtils.getColor(R.color.black));
    }

    @Override
    protected void setToolbarImage(ImageView imageView) {
        super.setToolbarImage(imageView);
        imageView.setImageResource(R.drawable.ic_back);
    }

    @Override
    protected void setToolbarTitle(TextView left, TextView mid, TextView right) {
        super.setToolbarTitle(left, mid, right);
        left.setText("Mock数据");
        left.setTextColor(UIUtils.getColor(R.color.black));
        mid.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
    }
}
