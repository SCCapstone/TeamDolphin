package com.example.teamdolphin;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.io.File;
import java.io.OutputStream;

import kotlinx.coroutines.internal.ExceptionsConstuctorKt;
import yuku.ambilwarna.AmbilWarnaDialog;



public class TesterCanvas extends AppCompatActivity{

    //inited the DrawView object
    private DrawView paint;

    //current functionality of canvas with SEMESTER 1 Build
    private ImageButton undo,save,brush,home, dropdown;

    private ImageButton eraser, colorPicker, pen, eyeDropper;
    private ImageButton selection, paintBucket, colorPreview, shapeTool;
    private ImageButton drag, zoom, rotate;

    //inited rangeslider object for brush stroke
    private RangeSlider rangeSlider, rangeSliderZoom, rangeSliderRotate;

    //stores the color to a local integer
    private int localColor, brushColor, eraserColor;
    private boolean eraserClicked = false;
    private boolean dragClickedBefore = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester_canvas);

        //getting the references needed to construct canvas page
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);
        rangeSliderZoom = (RangeSlider) findViewById(R.id.rangebarzoom);
        rangeSliderRotate = (RangeSlider) findViewById(R.id.rangebarrotate);
        undo = (ImageButton) findViewById(R.id.button_undo);
        save = (ImageButton) findViewById(R.id.button_save);
        brush = (ImageButton) findViewById(R.id.button_brush);
        home = (ImageButton) findViewById(R.id.button_home);

        //second row tools for canvas UI
        eraser = (ImageButton) findViewById(R.id.button_eraser);
        colorPicker = (ImageButton) findViewById(R.id.button_color);
        pen = (ImageButton) findViewById(R.id.button_pen);
        eyeDropper = (ImageButton) findViewById(R.id.button_eyedropper);

        //third row tools for canvas UI
        selection = (ImageButton) findViewById(R.id.button_selection);
        paintBucket = (ImageButton) findViewById(R.id.button_paintbucket);
        colorPreview = (ImageButton) findViewById(R.id.button_colorpreview);
        shapeTool = (ImageButton) findViewById(R.id.button_shape);

        //fourth row tools for canvas UI
        drag = (ImageButton) findViewById(R.id.button_drag);
        zoom = (ImageButton) findViewById(R.id.button_zoom);
        rotate = (ImageButton) findViewById(R.id.button_rotate);

        dropdown = (ImageButton) findViewById(R.id.button_menu);

        //set default color to black
        localColor = 0;
        //holds current color of brush
        brushColor = 0;
        //holds eraser value for canvas
        eraserColor = 0;


        //The onclick listeners for each button

        //If picture already exists, import it
        String projectName = FileCreation.Companion.getProjectNameString() + ".png";
        String duplicateFile =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DolphinArt Projects/" + projectName;
        File myFile = new File(duplicateFile);
        if(myFile.exists()) {
            System.out.println("The File Exists");
            //paint.importImage(duplicateFile);
        }


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
                if(eraserClicked == true)
                    paint.setColor(brushColor);
                paint.setEnabled(false);
                if(rangeSlider.getVisibility() == view.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);

            }
        });

        eraser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                paint.setColor(eraserColor);
                paint.setEnabled(false);
                eraserClicked = true;
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
                brushSize = value;
            }
        });


        rangeSliderZoom.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser)
            {
                System.out.println("Value changed to " + value); //okay, the value is changing
                paint.setScaleX(value*3);
                paint.setScaleY(value*3);
            }
        });

        rangeSliderRotate.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser)
            {

                paint.setRotation(value*360);
            }

        });

        zoom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(rangeSliderZoom.getVisibility() == view.VISIBLE)
                    rangeSliderZoom.setVisibility(View.GONE);
                else
                    rangeSliderZoom.setVisibility(View.VISIBLE);

            }
        });

        rotate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(rangeSliderRotate.getVisibility() == view.VISIBLE)
                    rangeSliderRotate.setVisibility(View.GONE);
                else
                    rangeSliderRotate.setVisibility(View.VISIBLE);

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
                Intent intent = getIntent();

                int width, height, background;
                String path;

                try{
                    width = intent.getIntExtra("width", paint.getMeasuredWidth());
                    height = intent.getIntExtra("height", paint.getMeasuredHeight());
                    background = intent.getIntExtra("background", Color.WHITE);
                }catch (Resources.NotFoundException e){
                    width = 0;
                    height = 0;
                    background = -1;
                }

                try{
                    path = intent.getStringExtra("imagePath");
                }catch (Resources.NotFoundException e){
                    path = null;
                }
                paint.init(height, width, background, path);
                eraserColor = background;
            }
        });

        //Make drag button drag the canvas
        drag.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(dragClickedBefore == false) {
                    paint.setEnabled(true);
                    paint.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            float xDown = 0;
                            float yDown = 0;
                            switch (event.getActionMasked()) {
                                case MotionEvent.ACTION_DOWN:
                                    xDown = event.getX();
                                    yDown = event.getY();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    float movedX, movedY;
                                    movedX = event.getX();
                                    movedY = event.getY();

                                    float distanceX = movedX - xDown;
                                    float distanceY = movedY - yDown;

                                    paint.setX(paint.getX() + distanceX);
                                    paint.setY(paint.getY() + distanceY);

                                    xDown = movedX;
                                    yDown = movedY;
                                    dragClickedBefore = true;
                                    break;
                            }

                            return true;
                        }
                    });
                } else {
                    paint.setEnabled(false);
                    dragClickedBefore = false;
                }
                //paint.drag();
                //paint.setCameraDistance();
            }
        });

        //Make Full Menu Visible and Hidden
        dropdown.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(eraser.getVisibility() == view.VISIBLE) {

                    eraser.setVisibility(View.GONE);
                    colorPicker.setVisibility(View.GONE);
                    pen.setVisibility(View.GONE);
                    eyeDropper.setVisibility(View.GONE);

                    selection.setVisibility(View.GONE);
                    paintBucket.setVisibility(View.GONE);
                    colorPreview.setVisibility(View.GONE);
                    shapeTool.setVisibility(View.GONE);
                    home.setVisibility(View.GONE);
                    drag.setVisibility(View.GONE);
                    zoom.setVisibility(View.GONE);
                    rotate.setVisibility(View.GONE);
                }
                else {

                    eraser.setVisibility(View.VISIBLE);
                    colorPicker.setVisibility(View.VISIBLE);
                    pen.setVisibility(View.VISIBLE);
                    eyeDropper.setVisibility(View.VISIBLE);

                    selection.setVisibility(View.VISIBLE);
                    paintBucket.setVisibility(View.VISIBLE);
                    colorPreview.setVisibility(View.VISIBLE);
                    shapeTool.setVisibility(View.VISIBLE);
                    home.setVisibility(View.VISIBLE);

                    drag.setVisibility(View.VISIBLE);
                    zoom.setVisibility(View.VISIBLE);
                    rotate.setVisibility(View.VISIBLE);
                }
            }
        });

        //when colorPicker is pressed open dialog for color
        colorPicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                //uses openColorDialogue to open Library Dialogue
                openColorDialogue();
            }
        });
    }

    //method used to open the dialog provided by open source library
    public void openColorDialogue(){
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, localColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener(){
            @Override
                    public void onCancel(AmbilWarnaDialog dialog){

            }
            @Override
                    public void onOk(AmbilWarnaDialog dialog, int color){
                localColor = color;
                brushColor = color;
                paint.setColor(color);
                colorPicker.setColorFilter(color);
            }
                });
        colorPickerDialogue.show();
    }
}