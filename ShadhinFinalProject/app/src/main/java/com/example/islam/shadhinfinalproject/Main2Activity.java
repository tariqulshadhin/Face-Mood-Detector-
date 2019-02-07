package com.example.islam.shadhinfinalproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.aprilapps.easyphotopicker.EasyImage;

public class Main2Activity extends AppCompatActivity {
    ImageButton imageButton;
    private static final int RESULT_LOAD_IMG=1;
    ImageView imageView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    ProgressBar progressBar;
    String name="";
    volatile int righteye;
    volatile int lefteye;
    volatile int smile;
    FirebaseVisionFaceDetectorOptions options;
    FirebaseVisionFaceDetector detector;
    Task<List<FirebaseVisionFace>> result;

    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private final int MY_PERMISSION_REQUEST_CODE=1212;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        if (Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);

        }
        imageButton=findViewById(R.id.back);
        imageView=findViewById(R.id.imageview_taken);
        progressBar=findViewById(R.id.prgressbar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
                finish();
            }
        });
    }

    public void chooseimage(View view) {

       EasyImage.openGallery(Main2Activity.this, 100);




    }




    public void chooseimagefromcamera(View view) {
        EasyImage.openCamera(Main2Activity.this, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(Main2Activity.this, "Image picker error", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                final Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
                BitmapHelper.getInstance().setBitmap(myBitmap);
                progressBar.setVisibility(View.VISIBLE);
                final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(new BitmapFactory().decodeFile(imageFile.getAbsolutePath()));

                FirebaseVisionFaceDetectorOptions options =
                        new FirebaseVisionFaceDetectorOptions.Builder()
                                .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                                .setMinFaceSize(0.15f)
                                .setTrackingEnabled(true)
                                .build();


                FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                        .getVisionFaceDetector(options);

                Task<List<FirebaseVisionFace>> result =
                        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> faces) {

                                for (FirebaseVisionFace face : faces) {

                                    if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                        float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                        float lefftEyeOpenProb=face.getLeftEyeOpenProbability();
                                        float finalleftprob = lefftEyeOpenProb * 100;
                                        float finalrightprob = rightEyeOpenProb * 100;
                                        righteye=(int)finalrightprob;
                                        lefteye=(int)finalleftprob;
                                        Log.d("Right",String.valueOf(righteye));
                                        Log.d("Left",String.valueOf(lefteye));




                                        }
                                    if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                        float smileProb = face.getSmilingProbability();
                                        float finalProb = smileProb * 100;
                                        smile=(int)finalProb;
                                        Log.d("Smile",String.valueOf(smile));
                                        progressBar.setVisibility(View.INVISIBLE);

                                        if (righteye>=75&&lefteye>=75&&smile>=0&&smile<=30){
                                            if (BitmapHelper.getInstance().getBitmap()==null){
                                                Toast.makeText(Main2Activity.this,"no image",Toast.LENGTH_LONG).show();
                                            }else {
                                                Intent anotherIntent = new Intent(Main2Activity.this, Angree.class);
                                                startActivity(anotherIntent);
                                                righteye=0;
                                                lefteye=0;
                                                smile=0;
                                            }
                                        }
                                        else if (righteye<=60&&lefteye<=60&&smile<=20){
                                            if (BitmapHelper.getInstance().getBitmap()==null){
                                                Toast.makeText(Main2Activity.this,"no image",Toast.LENGTH_LONG).show();
                                            }else {
                                                Intent anotherIntent = new Intent(Main2Activity.this, Sad.class);
                                                startActivity(anotherIntent);
                                                righteye=0;
                                                lefteye=0;
                                                smile=0;
                                            }
                                        }else if (smile>=31&&smile<=60){
                                            if (BitmapHelper.getInstance().getBitmap()==null){
                                                Toast.makeText(Main2Activity.this,"no image",Toast.LENGTH_LONG).show();
                                            }else {
                                                Intent anotherIntent = new Intent(Main2Activity.this, Calm.class);
                                                startActivity(anotherIntent);
                                                righteye=0;
                                                lefteye=0;
                                                smile=0;
                                            }
                                        }else if (smile>=61&&smile<=100){
                                            if (BitmapHelper.getInstance().getBitmap()==null){
                                                Toast.makeText(Main2Activity.this,"no image",Toast.LENGTH_LONG).show();
                                            }else {
                                                Intent anotherIntent = new Intent(Main2Activity.this, Happy.class);
                                                startActivity(anotherIntent);
                                                righteye=0;
                                                lefteye=0;
                                                smile=0;
                                            }
                                        }
                                    }






                                }
                            }
                        });

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                Toast.makeText(Main2Activity.this,"Image not detected",Toast.LENGTH_LONG).show();

            }
        });


    }



    public void backtomain(View view) {
        startActivity(new Intent(Main2Activity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE

            }, MY_PERMISSION_REQUEST_CODE);
        }
    }
}
