package com.minardwu.yiyue.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minardwu.yiyue.R;
import com.minardwu.yiyue.application.AppCache;
import com.minardwu.yiyue.application.YiYueApplication;
import com.minardwu.yiyue.model.MusicBean;
import com.minardwu.yiyue.service.PlayOnlineMusicService;
import com.minardwu.yiyue.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MinardWu on 2018/2/1.
 */

public class OnlineMusicRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private long playingMusicId;
    private int playingMusicPosition = -1;
    private boolean justIn = true;
    private List<MusicBean> musicList = new ArrayList<MusicBean>();

    public OnlineMusicRecycleViewAdapter(List<MusicBean> list) {
        this.musicList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_play_all,null);
            PlayAllViewHolder playAllViewHolder = new PlayAllViewHolder(view);
            return playAllViewHolder;
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_localmusic,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position==0){
            PlayAllViewHolder playAllViewHolder = (PlayAllViewHolder) holder;
            playAllViewHolder.tv_play_all.setText(headerText);
            playAllViewHolder.tv_play_all_song_count.setText("(共"+ musicList.size()+"首)");
            playAllViewHolder.rl_play_all.setVisibility(View.VISIBLE);
            playAllViewHolder.rl_play_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClick(view,position);
                    }
                }
            });
        }else {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            //因为进入到这块说明position不为0，所以position必须要减一才能获得musicList中下标为0的元素
            //所以这个recycleView涉及到musicList的position都要-1，其他的不用，如adapter.updatePlayingMusicPosition时直接传入position即可
            if ((playingMusicId== musicList.get(position-1).getId()&&justIn)||position==playingMusicPosition) {
                viewHolder.v_Playing.setVisibility(View.VISIBLE);
                viewHolder.tv_count.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.colorGreenDeep));
                viewHolder.tv_Title.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.colorGreenDeep));
                viewHolder.tv_Artist.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.colorGreenDeep));
            } else {
                viewHolder.v_Playing.setVisibility(View.INVISIBLE);
                viewHolder.tv_count.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.grey));
                viewHolder.tv_Title.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.black_l));
                viewHolder.tv_Artist.setTextColor(YiYueApplication.getAppContext().getResources().getColor(R.color.grey));
            }
            MusicBean music = musicList.get(position-1);
            viewHolder.tv_Title.setText(music.getTitle());
            viewHolder.tv_count.setText(position+"");
            String artist = FileUtils.getArtistAndAlbum(music.getArtist(), music.getAlbum());
            viewHolder.tv_Artist.setText(artist);
            viewHolder.v_Divider.setVisibility(position != musicList.size() ? View.VISIBLE : View.GONE);
            viewHolder.iv_More.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onMoreClick(view,position);
                    }
                }
            });
            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClick(view,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return musicList.size()+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_playing)
        View v_Playing;
        @BindView(R.id.iv_cover)
        ImageView iv_Cover;
        @BindView(R.id.tv_count)
        TextView tv_count;
        @BindView(R.id.tv_title)
        TextView tv_Title;
        @BindView(R.id.tv_artist)
        TextView tv_Artist;
        @BindView(R.id.iv_more)
        ImageView iv_More;
        @BindView(R.id.v_divider)
        View v_Divider;
        @BindView(R.id.ll_item)
        View ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PlayAllViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_play_all_song_count)
        TextView tv_play_all_song_count;
        @BindView(R.id.tv_play_all)
        TextView tv_play_all;
        @BindView(R.id.rl_play_all)
        RelativeLayout rl_play_all;

        public PlayAllViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void updatePlayingMusicId(PlayOnlineMusicService playOnlineMusicService) {
        if (playOnlineMusicService.getPlayingMusic() != null) {
            playingMusicId = playOnlineMusicService.getPlayingMusic().getId();
        } else {
            playingMusicId = -1;
        }
    }

    public void updatePlayingMusicPosition(int position) {
        justIn = false;
        playingMusicPosition = position;
    }

    public int getPlayingMusicPosition() {
        return playingMusicPosition;
    }

    public List<MusicBean> getMusicList() {
        return musicList;
    }

    private String headerText;

    public void setHeaderText(String artistId){
        if(artistId.equals(AppCache.getPlayOnlineMusicService().getListId())){
            headerText = "正在循环";
        }else {
            headerText = "开始循环";
        }
        notifyDataSetChanged();
    }

    public interface OnRecycleViewClickListener{
        void onItemClick(View view,int position);
        void onMoreClick(View view,int position);
    }

    private OnRecycleViewClickListener listener;

    public void setOnRecycleViewClickListener(OnRecycleViewClickListener listener){
        this.listener = listener;
    }
}
