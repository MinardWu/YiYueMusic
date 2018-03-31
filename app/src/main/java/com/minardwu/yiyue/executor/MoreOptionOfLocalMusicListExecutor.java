package com.minardwu.yiyue.executor;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.minardwu.yiyue.application.AppCache;
import com.minardwu.yiyue.model.MusicBean;
import com.minardwu.yiyue.utils.FileUtils;
import com.minardwu.yiyue.utils.MusicUtils;
import com.minardwu.yiyue.utils.SystemUtils;
import com.minardwu.yiyue.utils.ToastUtils;

import java.io.File;

/**
 * Created by MinardWu on 2018/3/15.
 */

public class MoreOptionOfLocalMusicListExecutor {
    public static void execute(Activity activity,int position, MusicBean musicBean,IView iView){
        switch (position){
            case 0:
                //position==0逻辑放在外面执行，不会跳到这里
                break;
            case 1:
//                Intent intent = new Intent(activity, ArtistActivity.class);
//                intent.putExtra("artistName",musicBean.getArtist());
//                intent.putExtra("artistId",musicBean.getArtistId());
//                activity.startActivity(intent);
                break;
            case 2:

                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 判断是否有WRITE_SETTINGS权限
                    if(!Settings.System.canWrite(activity)) {
                        ToastUtils.show("请开通相关权限，否则无法正常使用该功能！");
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivityForResult(intent, SystemUtils.REQUEST_WRITE_SETTING);
                    } else {
                        File music = new File(musicBean.getPath());
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.MediaColumns.DATA, music.getAbsolutePath());
                        values.put(MediaStore.MediaColumns.TITLE, music.getName());
                        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
                        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
                        values.put(MediaStore.Audio.Media.IS_ALARM, false);
                        values.put(MediaStore.Audio.Media.IS_MUSIC, true);
                        Uri uri = MediaStore.Audio.Media.getContentUriForPath(music.getAbsolutePath());
                        Uri newUri = activity.getContentResolver().insert(uri, values);
                        RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_RINGTONE, newUri);
                        Toast.makeText(activity,"设置来电铃声成功！", Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    //dosomething();
                }
                break;
            case 4:
                if(FileUtils.deleteFile(musicBean.getPath())){
                    ToastUtils.show("删除成功");
                    for (int i=0;i<AppCache.getLocalMusicList().size();i++){
                        if(AppCache.getLocalMusicList().get(i).getId()==musicBean.getId()){
                            AppCache.getLocalMusicList().remove(i);
                        }
                    }
                    iView.updateViewForExecutor();
                }else {
                    ToastUtils.show("删除失败");
                }
                break;
        }
    }
}
