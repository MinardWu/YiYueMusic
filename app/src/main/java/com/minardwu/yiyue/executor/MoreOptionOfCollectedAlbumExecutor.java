package com.minardwu.yiyue.executor;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.minardwu.yiyue.R;
import com.minardwu.yiyue.activity.ArtistActivity;
import com.minardwu.yiyue.db.MyDatabaseHelper;
import com.minardwu.yiyue.model.AlbumBean;
import com.minardwu.yiyue.utils.UIUtils;
import com.minardwu.yiyue.widget.dialog.YesOrNoDialog;

/**
 * Created by MinardWu on 2018/3/15.
 */

public class MoreOptionOfCollectedAlbumExecutor {
    public static void execute(final Activity activity, int position, final AlbumBean albumBean, final IView iView){
        switch (position){
            case 0:
                Intent artistIntent = new Intent(activity, ArtistActivity.class);
                artistIntent.putExtra(ArtistActivity.ARTIST_ID,albumBean.getArtist().getId());
                artistIntent.putExtra(ArtistActivity.ARTIST_NAME,albumBean.getArtist().getName());
                activity.startActivity(artistIntent);
                break;
            case 1:
                YesOrNoDialog dialog = new YesOrNoDialog.Builder()
                        .context(activity)
                        .subtitle(R.string.is_delete_collected_album)
                        .yes(R.string.sure, new YesOrNoDialog.PositiveClickListener() {
                            @Override
                            public void OnClick(YesOrNoDialog dialog1,View view) {
                                MyDatabaseHelper.init(activity).deleteCollectedAlbum(albumBean);
                                iView.updateViewForExecutor();
                                dialog1.dismiss();
                            }
                        })
                        .no(R.string.cancel, new YesOrNoDialog.NegativeClickListener() {
                            @Override
                            public void OnClick(YesOrNoDialog dialog1,View view) {
                                dialog1.dismiss();
                            }
                        })
                        .noTextColor(UIUtils.getColor(R.color.green_main))
                        .build();
                dialog.show();
                break;
            default:
                break;
        }
    }
}
