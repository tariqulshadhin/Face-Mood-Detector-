package com.example.islam.shadhinfinalproject.MusicHelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.islam.shadhinfinalproject.R;

public class SongAdapter extends RecyclerView.ViewHolder {
    public TextView tv_song_name;
    public Button imageButton_play;
    public Button imageButton_pause;



    public SongAdapter(View itemView) {
        super(itemView);
        tv_song_name=itemView.findViewById(R.id.tv_song_name);
        imageButton_play=itemView.findViewById(R.id.button_play);
        imageButton_pause=itemView.findViewById(R.id.button_pause);



    }


}
