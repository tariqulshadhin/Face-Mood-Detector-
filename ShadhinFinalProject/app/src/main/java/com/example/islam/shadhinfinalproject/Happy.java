package com.example.islam.shadhinfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class Happy extends AppCompatActivity {
    Bitmap bmp;
    CircleImageView imageView;

    DatabaseReference reference;
    String name="";
    TextView txt_sad_timer1;
    TextView txt_sad_timer2;
    TextView txt_sad_timer3;
    TextView txt_sad_timer4;

    ImageButton btn_sad_play1;
    ImageButton btn_sad_play2;
    ImageButton btn_sad_play3;
    ImageButton btn_sad_play4;

    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    MediaPlayer mediaPlayer4;

    private boolean playPause1;
    private boolean playPause2;
    private boolean playPause3;
    private boolean playPause4;

    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;
    ProgressDialog progressDialog3;
    ProgressDialog progressDialog4;

    private boolean initialStage1 = true;
    private boolean initialStage2 = true;
    private boolean initialStage3 = true;
    private boolean initialStage4 = true;

    double finalTime1;
    double finalTime2;
    double finalTime3;
    double finalTime4;

    DatabaseReference reference_happy;
    String url1,url2,url3,url4;
    TextView name1,name2,name3,name4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy);
        imageView=findViewById(R.id.imageView_tutor_account);

        name1=findViewById(R.id.name1);
        name2=findViewById(R.id.name2);
        name3=findViewById(R.id.name3);
        name4=findViewById(R.id.name4);



        txt_sad_timer1=findViewById(R.id.txt_sad_timer1);
        txt_sad_timer2=findViewById(R.id.txt_sad_timer2);
        txt_sad_timer3=findViewById(R.id.txt_sad_timer3);
        txt_sad_timer4=findViewById(R.id.txt_sad_timer4);


        mediaPlayer1 = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer3 = new MediaPlayer();
        mediaPlayer4 = new MediaPlayer();

        mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer3.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer4.setAudioStreamType(AudioManager.STREAM_MUSIC);

        progressDialog1 = new ProgressDialog(this);
        progressDialog2 = new ProgressDialog(this);
        progressDialog3 = new ProgressDialog(this);
        progressDialog4 = new ProgressDialog(this);

        btn_sad_play1=findViewById(R.id.btn_sad_play1);
        btn_sad_play2=findViewById(R.id.btn_sad_play2);
        btn_sad_play3=findViewById(R.id.btn_sad_play3);
        btn_sad_play4=findViewById(R.id.btn_sad_play4);

        imageView.setImageBitmap(BitmapHelper.getInstance().getBitmap());
        reference=FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference_happy=FirebaseDatabase.getInstance().getReference().child("Happy");

        reference_happy.child("01").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String name=dataSnapshot.child("name").getValue().toString();
                    url1 =dataSnapshot.child("url").getValue().toString();
                    name1.setText(name);



                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference_happy.child("02").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String name=dataSnapshot.child("name").getValue().toString();
                    url2 =dataSnapshot.child("url").getValue().toString();
                    name2.setText(name);


                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference_happy.child("03").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String name=dataSnapshot.child("name").getValue().toString();
                    url3 =dataSnapshot.child("url").getValue().toString();
                    name3.setText(name);


                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference_happy.child("04").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String name=dataSnapshot.child("name").getValue().toString();
                    url4 =dataSnapshot.child("url").getValue().toString();
                    name4.setText(name);


                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_sad_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause1){
                    btn_sad_play1.setImageResource(R.drawable.pause);
                    if (initialStage1){
                        new Player1().execute(url1);



                    }else {
                        if (!mediaPlayer1.isPlaying()){
                            mediaPlayer1.start();
                        }
                    }
                    playPause1=true;


                }else {
                    btn_sad_play1.setImageResource(R.drawable.play);
                    if(mediaPlayer1 != null && mediaPlayer1.isPlaying()){
                        mediaPlayer1.pause();
                    }
                    playPause1=false;
                }
            }
        });

        btn_sad_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause2){
                    btn_sad_play2.setImageResource(R.drawable.pause);
                    if (initialStage2){
                        new Player2().execute(url2);



                    }else {
                        if (!mediaPlayer2.isPlaying()){
                            mediaPlayer2.start();
                        }
                    }
                    playPause2=true;


                }else {
                    btn_sad_play2.setImageResource(R.drawable.play);
                    if(mediaPlayer2 != null && mediaPlayer2.isPlaying()){
                        mediaPlayer2.pause();
                    }
                    playPause2=false;
                }
            }
        });

        btn_sad_play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause3){
                    btn_sad_play3.setImageResource(R.drawable.pause);
                    if (initialStage3){
                        new Player3().execute(url3);



                    }else {
                        if (!mediaPlayer3.isPlaying()){
                            mediaPlayer3.start();
                        }
                    }
                    playPause3=true;


                }else {
                    btn_sad_play3.setImageResource(R.drawable.play);
                    if(mediaPlayer3 != null && mediaPlayer3.isPlaying()){
                        mediaPlayer3.pause();
                    }
                    playPause3=false;
                }
            }
        });
        btn_sad_play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause4){
                    btn_sad_play4.setImageResource(R.drawable.pause);
                    if (initialStage4){
                        new Player4().execute(url4);



                    }else {
                        if (!mediaPlayer4.isPlaying()){
                            mediaPlayer4.start();
                        }
                    }
                    playPause4=true;


                }else {
                    btn_sad_play4.setImageResource(R.drawable.play);
                    if(mediaPlayer4 != null && mediaPlayer4.isPlaying()){
                        mediaPlayer4.pause();
                    }
                    playPause4=false;
                }
            }
        });












    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       if  (mediaPlayer1 != null) {
            mediaPlayer1.reset();
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
        else if  (mediaPlayer2 != null) {
            mediaPlayer2.reset();
            mediaPlayer2.release();
            mediaPlayer2 = null;
        }
        else if  (mediaPlayer3 != null) {
            mediaPlayer3.reset();
            mediaPlayer3.release();
            mediaPlayer3 = null;
        }
        else if  (mediaPlayer4 != null) {
            mediaPlayer4.reset();
            mediaPlayer4.release();
            mediaPlayer4 = null;
        }
    }


    public void goSongtolisten(View view) {
        startActivity(new Intent(Happy.this,Songs.class));
    }

    public void gomain2activity(View view) {
        startActivity(new Intent(Happy.this,Main2Activity.class));
        finish();
    }

    public class Player1 extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared=false;
            try {
                mediaPlayer1.setDataSource(strings[0]);
                mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage1 = true;
                        playPause1 = false;
                        btn_sad_play1.setImageResource(R.drawable.play);
                        mediaPlayer1.stop();
                        mediaPlayer1.reset();
                    }
                });
                mediaPlayer1.prepare();

                prepared = true;

            } catch (IOException e) {
                e.printStackTrace();
                prepared=false;
            }
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog1.isShowing()) {
                progressDialog1.cancel();
                finalTime1 = mediaPlayer1.getDuration();
                txt_sad_timer1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime1),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime1) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime1))));


            }

            mediaPlayer1.start();
            initialStage1 = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1.setMessage("Buffering...");
            progressDialog1.show();


        }

    }
    public class Player2 extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared=false;
            try {
                mediaPlayer2.setDataSource(strings[0]);
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage2 = true;
                        playPause2 = false;
                        btn_sad_play2.setImageResource(R.drawable.play);
                        mediaPlayer2.stop();
                        mediaPlayer2.reset();
                    }
                });
                mediaPlayer2.prepare();

                prepared = true;

            } catch (IOException e) {
                e.printStackTrace();
                prepared=false;
            }
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog2.isShowing()) {
                progressDialog2.cancel();
                finalTime2 = mediaPlayer2.getDuration();
                txt_sad_timer2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime2),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime2) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime2))));


            }

            mediaPlayer2.start();
            initialStage2 = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog2.setMessage("Buffering...");
            progressDialog2.show();


        }

    }
    public class Player3 extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared=false;
            try {
                mediaPlayer3.setDataSource(strings[0]);
                mediaPlayer3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage3 = true;
                        playPause3 = false;
                        btn_sad_play3.setImageResource(R.drawable.play);
                        mediaPlayer3.stop();
                        mediaPlayer3.reset();
                    }
                });
                mediaPlayer3.prepare();

                prepared = true;

            } catch (IOException e) {
                e.printStackTrace();
                prepared=false;
            }
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog3.isShowing()) {
                progressDialog3.cancel();
                finalTime3 = mediaPlayer3.getDuration();
                txt_sad_timer3.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime3),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime3) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime3))));


            }

            mediaPlayer3.start();
            initialStage3 = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog3.setMessage("Buffering...");
            progressDialog3.show();


        }

    }
    public class Player4 extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared=false;
            try {
                mediaPlayer4.setDataSource(strings[0]);
                mediaPlayer4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage4 = true;
                        playPause4 = false;
                        btn_sad_play4.setImageResource(R.drawable.play);
                        mediaPlayer4.stop();
                        mediaPlayer4.reset();
                    }
                });
                mediaPlayer4.prepare();

                prepared = true;

            } catch (IOException e) {
                e.printStackTrace();
                prepared=false;
            }
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog4.isShowing()) {
                progressDialog4.cancel();
                finalTime4 = mediaPlayer4.getDuration();
                txt_sad_timer4.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime4),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime4) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime4))));


            }

            mediaPlayer4.start();
            initialStage4 = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog4.setMessage("Buffering...");
            progressDialog4.show();


        }

    }

}
