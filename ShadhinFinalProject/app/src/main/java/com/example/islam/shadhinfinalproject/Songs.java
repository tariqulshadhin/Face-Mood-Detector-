package com.example.islam.shadhinfinalproject;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.example.islam.shadhinfinalproject.MusicHelper.SongAdapter;
import com.example.islam.shadhinfinalproject.MusicHelper.SongHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class Songs extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<SongHelper,SongAdapter> adapter;
    DatabaseReference reference_song;
    MediaPlayer mediaPlayer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Toast.makeText(getApplicationContext(),"Please listen one by one. Don't move to another song until present song is totaly finished",Toast.LENGTH_LONG).show();
        recyclerView = findViewById(R.id.recyclerview_song);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reference_song = FirebaseDatabase.getInstance().getReference().child("Song");
        adapter = new FirebaseRecyclerAdapter<SongHelper, SongAdapter>(SongHelper.class, R.layout.songadapter, SongAdapter.class, reference_song) {
            @Override
            protected void populateViewHolder(final SongAdapter viewHolder, final SongHelper model, int position) {
                viewHolder.tv_song_name.setText(model.getName());
                final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
                mediaPlayer[0] =new MediaPlayer();
                viewHolder.imageButton_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {

                            mediaPlayer[0].setDataSource(model.getUrl());
                            mediaPlayer[0].prepareAsync();
                            mediaPlayer[0].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer medi) {
                                    medi.start();
                                    viewHolder.imageButton_play.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    Toast.makeText(Songs.this,"Please wait song is playing",Toast.LENGTH_LONG).show();

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                viewHolder.imageButton_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mediaPlayer[0].stop();
                        mediaPlayer[0].reset();
                        mediaPlayer[0].release();
                        mediaPlayer[0] =null;
                        viewHolder.imageButton_pause.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        Toast.makeText(Songs.this,"Please wait song is stoping",Toast.LENGTH_LONG).show();
                    }
                });



                }
        };

        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer.reset();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void gobacktoMain2activity(View view) {
        startActivity(new Intent(Songs.this,Main2Activity.class));
        finish();
    }
}
