package com.example.teamdolphin;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.io.File;
import java.io.OutputStream;



public class TesterCanvas extends AppCompatActivity{

    //inited the DrawView object
    private DrawView paint;

    //current functionality of canvas with SEMESTER 1 Build
    private ImageButton undo,save,brush,home;

    //inited rangeslider object for brush stroke
    private RangeSlider rangeSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester_canvas);

        //getting the references needed to construct canvas page
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);
        undo = (ImageButton) findViewById(R.id.button_undo);
        save = (ImageButton) findViewById(R.id.button_save);
        brush = (ImageButton) findViewById(R.id.button_brush);
        home = (ImageButton) findViewById(R.id.button_home);

        //The onclick listeners for each button

        //using the drawview function, remove most recent stroke
        undo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                paint.undo();
            }
        });

        /* is able to save the current bitmap
        as a png in the filesystem storage*/
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //getting current bitmap from DrawView
                Bitmap bitmap = paint.save();

                //opening outputstream to write into a file
                OutputStream imageOutStream = null;

                ContentValues contentValues = new ContentValues();

                //name of file current a preset
                String projectName = FileCreation.Companion.getProjectNameString() + ".png";
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, projectName);

                //filetype that we will be using now PNG
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

                //On Phone location of where file will be saved
                File pictureFolder = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                );
                //Makes the DolphinArt Projects folder
                File mediaStorageDir =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"DolphinArt Projects");
                if(!mediaStorageDir.exists())
                    mediaStorageDir.mkdirs();


                //Environment.DIRECTORY_PICTURES
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "DolphinArt Projects");

                //get the Uri of the file which is to be created in the storage
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    //open the output stream with the above uri
                    imageOutStream = getContentResolver().openOutputStream(uri);

                    //this method writes the files in storage
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);

                    //Creating the Toast and values
                    Context context = getApplicationContext();
                    CharSequence savedText = "\"" + projectName + "\" Saved to Pictures\\DolphinArt Projects";
                    int duration= Toast.LENGTH_SHORT;

                    //The actual toast message
                    Toast toast = Toast.makeText(context,savedText,duration);
                    toast.show();

                    // close the output stream after use
                    imageOutStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //toggle button for brushSizeSlider
        brush.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(rangeSlider.getVisibility() == view.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);

            }
        });

        //ranges of the brush size slider/rangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        //listener for when the user sets a new brush size
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener()
        {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser)
            {
                paint.setStrokeWidth((int) value);
            }
        });


        //returns the user to the homepage
        home.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(TesterCanvas.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }
}