package com.example.islam.shadhinfinalproject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {
    Button button_go;
    TextToSpeech textToSpeech;
    RelativeLayout relativeLayout;
    SharedPreferences prefs;
    DatabaseReference reference_name;
    String name;
    String uid="";

    FirebaseAuth auth;

    SpotsDialog dialog1;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=findViewById(R.id.rellllll);
        dialog1=new SpotsDialog(MainActivity.this);
        try {
            uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            reference_name=FirebaseDatabase.getInstance().getReference().child("user").child(Objects.requireNonNull(uid));
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        prefs= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (!prefs.contains("Firsttime")){
            textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onInit(int i) {
                    if (i==TextToSpeech.SUCCESS){
                        int result=textToSpeech.setLanguage(Locale.ENGLISH);
                        if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(MainActivity.this,"Speaking is not supported",Toast.LENGTH_LONG).show();

                        }else{
                            textToSpeech.setPitch(0.6f);
                            textToSpeech.setSpeechRate(0.8f);

                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                                textToSpeech.speak("Welcome to face mood detector. So let's play with it and press get started",TextToSpeech.QUEUE_FLUSH,null,null);
                            }else {
                                textToSpeech.speak("Welcome to face mood detector. So let's play with it press get started",TextToSpeech.QUEUE_FLUSH,null,null);
                            }
                        }
                    }
                }
            });
        }else {
            reference_name.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        name=dataSnapshot.child("name").getValue().toString();
                        speak();



                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        button_go=findViewById(R.id.get);
        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!prefs.contains("Firsttime")){
                    Intent intent=new Intent(MainActivity.this,PhoneNumber.class);
                    startActivity(intent);
                    finish();


                }else {
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                    startActivity(intent);
                }



            }
        });




   }



    private void speak() {
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(MainActivity.this,"Speaking is not supported",Toast.LENGTH_LONG).show();

                    }else{
                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(0.8f);

                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                            textToSpeech.speak("Welcome "+name,TextToSpeech.QUEUE_FLUSH,null,null);
                        }else {
                            textToSpeech.speak("Welcome "+name,TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                    }
                }
            }
        });

    }
}
