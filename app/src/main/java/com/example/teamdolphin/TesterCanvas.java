package com.example.teamdolphin;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.chrono.Era;

import yuku.ambilwarna.AmbilWarnaDialog;


public class TesterCanvas extends AppCompatActivity {

    //inited the DrawView object
    private DrawView paint;

    //Orignal Functionality of Canvas
    private ImageButton undo, save, brush, home, dropdown;

    //Functionality Added onto Canvas
    private ImageButton eraser, colorPicker, eyeDropper;
    private ImageButton paintBucket, shapeTool;
    private ImageButton drag, zoom, rotate, fixView;
    private ImageButton circle;

    private LinearLayout shapes;

    //inited rangeslider object for brush stroke
    private RangeSlider rangeSlider, rangeSliderZoom, rangeSliderRotate, rangeSliderCircle, rangeSliderRect;

    //stores the color to a local integer
    private int localColor, brushColor, eraserColor;
    private boolean eraserClicked = false;
    private boolean dragClickedBefore = false;

    //Layout that has all the tools in it
    private GridLayout gridLayout;

    //Buttons list
    private ImageButton[] buttonsList;
    private ImageButton primaryButton;
    float previousBrushSize = 16;
    int correctedSize = -9999;
    int currentRotation = 0;
    float currentZoom = 0.33f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester_canvas);

        //getting the references needed to construct canvas page
        paint = findViewById(R.id.draw_view);
        rangeSlider = findViewById(R.id.rangebar);
        rangeSliderZoom = findViewById(R.id.rangebarzoom);
        rangeSliderRotate = findViewById(R.id.rangebarrotate);
        rangeSliderCircle = findViewById(R.id.rangebarcircle);
        //rangeSliderRect = findViewById(R.id.rangebarrectangle);
        undo = findViewById(R.id.button_undo);
        save = findViewById(R.id.button_save);
        primaryButton = findViewById(R.id.button_primary);
        brush = findViewById(R.id.button_brush);
        home = findViewById(R.id.button_home);
        fixView = findViewById(R.id.button_fix_view);

        //second row tools for canvas UI
        eraser = findViewById(R.id.button_eraser);
        colorPicker = findViewById(R.id.button_color);
        //pen = findViewById(R.id.button_pen);
        eyeDropper = findViewById(R.id.button_eyedropper);

        //third row tools for canvas UI
        //selection = findViewById(R.id.button_selection);
        paintBucket = findViewById(R.id.button_paintbucket);
        //colorPreview = findViewById(R.id.button_colorpreview);
        shapeTool = findViewById(R.id.button_shape);

        //fourth row tools for canvas UI
        drag = findViewById(R.id.button_drag);
        zoom = findViewById(R.id.button_zoom);
        rotate = findViewById(R.id.button_rotate);

        dropdown = findViewById(R.id.button_menu);

        shapes = findViewById(R.id.CanvasUIRowShapes);
        //rect = findViewById(R.id.button_rect);
        circle = findViewById(R.id.button_circle);

        //set default color to black
        localColor = 0;
        //holds current color of brush
        brushColor = Color.BLACK;
        //holds eraser value for canvas
        eraserColor = 0;

        //Initializing the gridLayout
        gridLayout = findViewById(R.id.tools_grid_layout);
        primaryButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.OVERLAY);
        primaryButton.setOnClickListener(BrushOnClickListener);

        //Initializing Buttons list
        buttonsList = new ImageButton[gridLayout.getChildCount()];
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            buttonsList[i] = (ImageButton) gridLayout.getChildAt(i);
        }
        System.out.println("Buttons list size" + buttonsList.length);

        //The onclick listeners for each button

        //If picture already exists, import it
        String projectName = FileCreation.Companion.getProjectNameString() + ".png";
        String duplicateFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DolphinArt Projects/" + projectName;
        File myFile = new File(duplicateFile);
        if (myFile.exists()) {
            System.out.println("The File Exists");
            //paint.importImage(duplicateFile);
        }


        //using the draw view function, remove most recent stroke
        undo.setOnClickListener(UndoOnClickListener);

        //is able to save the current bitmap as a png in the filesystem storage
        save.setOnClickListener(SaveOnClickListener);

        //toggle button for brushSizeSlider
        brush.setOnClickListener(BrushOnClickListener);

        eraser.setOnClickListener(EraserOnClickListener);

        //OnClick Listener for Eye Dropper Tool
        eyeDropper.setOnClickListener(EyeDropperOnClickListener);

        //when colorPicker is pressed open dialog for color
        colorPicker.setOnClickListener(ColorPickerOnClickListener);

        zoom.setOnClickListener(ZoomOnClickListener);

        rotate.setOnClickListener(RotateOnClickListener);

        //returns the user to the homepage
        home.setOnClickListener(HomeClickListener);

        //Make drag button drag the canvas
        drag.setOnClickListener(DragOnClickListener);

        //Make Full Menu Visible and Hidden
        dropdown.setOnClickListener(DropDownOnClickListener);

        //The Sliders for our tool functionalities

        //ranges of the brush size slider/rangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        //listener for when the user sets a new brush size
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
                previousBrushSize = (int) value;
                //brushSize = value;
            }
        });


        rangeSliderZoom.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                System.out.println("Value changed to " + value); //okay, the value is changing
                currentZoom = value;
                paint.setScaleX(value * 3);
                paint.setScaleY(value * 3);
            }
        });
        rangeSliderRotate.setValueFrom(0.0f);
        rangeSliderRotate.setValueTo(360.0f);
        rangeSliderRotate.setStepSize(1f);
        rangeSliderRotate.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                currentRotation = (int) value;
                paint.setRotation(value);

            }

        });

        //Initialization of our Canvas
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Intent intent = getIntent();

                int width, height, background;
                String path;

                try {
                    width = intent.getIntExtra("width", paint.getMeasuredWidth());
                    height = intent.getIntExtra("height", paint.getMeasuredHeight());
                    background = intent.getIntExtra("background", Color.WHITE);
                } catch (Resources.NotFoundException e) {
                    width = 0;
                    height = 0;
                    background = -1;
                }

                try {
                    path = intent.getStringExtra("imagePath");
                } catch (Resources.NotFoundException e) {
                    path = null;
                }
                paint.init(height, width, background, path);
                eraserColor = background;
            }
        });

        //OnClick Listener for Shape Tool
        shapeTool.setOnClickListener(ShapeToolOnClickListener);
        fixView.setOnClickListener(fixViewOnClickListener);

        //OnClick Listener for Paint Dumper Tool
        paintBucket.setOnClickListener(PaintBucketOnClickListener);


        final boolean[] circleClickedBefore = {false};
        //OnClick Listener for circle Tool
        circle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(circleClickedBefore[0] == false) {
                    Context context = getApplicationContext();
                    CharSequence text = "Circle Tool";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    rangeSliderCircle.setVisibility(View.VISIBLE);
                    circleClickedBefore[0] = true;
                } else {
                    rangeSliderCircle.setVisibility(View.GONE);
                    circleClickedBefore[0] = false;
                }

            }
        });
        rangeSliderCircle.setValueFrom(0.0f);
        rangeSliderCircle.setValueTo(1500.0f);
        rangeSliderCircle.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                //previousBrushSize = paint.getStrokeWidth();
                paint.setStrokeWidth((int) value);
            }

        });
    }

    //method used to open the dialog provided by open source library
    public void openColorDialogue() {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, localColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        localColor = color;
                        brushColor = color;
                        System.out.println(brushColor+" THIS IS BRUSH COLOR");
                        paint.setColor(color);
                        colorPicker.setColorFilter(color);
                    }
                });
        colorPickerDialogue.show();
    }

    //Helper Clases for our DropDown Menu
    //Closing Dropdown Menu when opened
    public void closeDropDown() {
        for (int i = 4; i < buttonsList.length; i++) {
            buttonsList[i].setVisibility(View.GONE);
        }
    }

    //Opening Dropdown Menu when closed
    public void openDropDown() {
        for (int i = 4; i < buttonsList.length; i++) {
            buttonsList[i].setVisibility(View.VISIBLE);
        }
    }

    //Copy Current tool to main menu row
    public void copyPrimaryButtonFrom(ImageButton selectedButton, View.OnClickListener listener) {
        if(primaryButton.getId()==selectedButton.getId())
            return;
        primaryButton.setImageDrawable(selectedButton.getDrawable());
        primaryButton.setOnClickListener(listener);
        primaryButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.OVERLAY);
        closeDropDown();

        if(selectedButton.getId()==shapeTool.getId()){
            shapes.setVisibility(View.VISIBLE);
        }else{
            shapes.setVisibility(View.GONE);
        }
    }

    //onclick listener for eyedropper functionality
    private final View.OnClickListener EyeDropperOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getApplicationContext();
            CharSequence text = "Eye Dropper";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            paint.setEnabled(true);

            paint.setOnTouchListener(new View.OnTouchListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float xDown = 0;
                    float yDown = 0;
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            xDown = event.getX();
                            yDown = event.getY();
                            Bitmap bitmap = paint.save();
                            Color tempColor = bitmap.getColor((int)xDown, (int)yDown);
                            //int colorConvertor = tempColor.toArgb(tempColor);
                            brushColor = tempColor.toArgb();
                            localColor = brushColor;
                            paint.setColor(brushColor);
                            colorPicker.setColorFilter(brushColor);

                            break;
                    }

                    return true;
                }
            });
            copyPrimaryButtonFrom(eyeDropper, EyeDropperOnClickListener);

        }
    };

    //onclick listener for our brush functionality
    private final View.OnClickListener BrushOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Brush";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            paint.setStrokeWidth((int) previousBrushSize);
            primaryButton.setImageDrawable(brush.getDrawable());
            //Ensure all other range sliders are disabled
            rangeSliderRotate.setVisibility(View.GONE);
            rangeSliderZoom.setVisibility(View.GONE);
            rangeSliderCircle.setVisibility(View.GONE);
            //rangeSliderRect.setVisibility(View.GONE);

            //if( paint.getStrokeWidth() == correctedSize ) {
                //paint.setStrokeWidth((int) previousBrushSize);
                //System.out.println("correctedSize = " + correctedSize + "\npreviousBrushSize = " + previousBrushSize + "\ncurrent brush size = " + paint.getStrokeWidth());
            //}

            if (eraserClicked == true)
                paint.setColor(brushColor);
            paint.setEnabled(false);
            if (rangeSlider.getVisibility() == View.VISIBLE)
                rangeSlider.setVisibility(View.GONE);
            else
                rangeSlider.setVisibility(View.VISIBLE);
            copyPrimaryButtonFrom(brush, BrushOnClickListener);

        }
    };

    //onclick listener for our eraser funcationlity
    //Tracks Eraser Size/Color
    private final View.OnClickListener EraserOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Eraser";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Ensure all other sliders disable
            rangeSliderRotate.setVisibility(View.GONE);
            rangeSliderZoom.setVisibility(View.GONE);
            rangeSliderCircle.setVisibility(View.GONE);
            //rangeSliderRect.setVisibility(View.GONE);

            paint.setColor(eraserColor);
            paint.setStrokeWidth((int) previousBrushSize);
            paint.setEnabled(false);
            eraserClicked = true;
            if (rangeSlider.getVisibility() == View.VISIBLE)
                rangeSlider.setVisibility(View.GONE);
            else
                rangeSlider.setVisibility(View.VISIBLE);
            copyPrimaryButtonFrom(eraser, EraserOnClickListener);
        }
    };

    //onclick listener for udno functionality
    //Undo's using our Stroke from the ArrayList paths
    private final View.OnClickListener UndoOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Undo";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            paint.undo();
        }
    };

    //onclick listener for our Color Dialogue
    private final View.OnClickListener ColorPickerOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //uses openColorDialogue to open Library Dialogue
            openColorDialogue();
        }
    };

    //onclick listener for dropdown, used for closing the sliders
    private final View.OnClickListener DropDownOnClickListener = new View.OnClickListener(){
        public void onClick(View view) {

            rangeSlider.setVisibility(View.GONE);
            rangeSliderRotate.setVisibility(View.GONE);
            rangeSliderZoom.setVisibility(View.GONE);
            rangeSliderCircle.setVisibility(View.GONE);
            //rangeSliderRect.setVisibility(View.GONE);
            if (eraser.getVisibility() == View.VISIBLE) {
                closeDropDown();
            } else {
                openDropDown();
            }
        }
    };

    private final View.OnClickListener fixViewOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Return Canvas to Standard Position";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            paint.setScaleX(1);
            paint.setScaleY(1);
            paint.setRotation(0);
            paint.setX(0);
            paint.setY(0);
        }
    };

    @Override
    public void onBackPressed() {
        if(DrawView.saved==false){
            System.out.println("Not saved");
            Intent intent = new Intent(TesterCanvas.this, MainActivity.class);
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        save.performClick();
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(intent);
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to save the project before leaving?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            return;
        }
        super.onBackPressed();
    }

    private final View.OnClickListener HomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(DrawView.saved==false){
                System.out.println("Not saved");
                Intent intent = new Intent(TesterCanvas.this, MainActivity.class);
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            save.performClick();
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            startActivity(intent);
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to save the project before leaving?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return;
            }
            Intent intent = new Intent(TesterCanvas.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener ShapeToolOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getApplicationContext();
            CharSequence text = "Shape Tool";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            primaryButton.setImageDrawable(shapeTool.getDrawable());
            //shapes.setVisibility(View.VISIBLE);
            if (shapes.getVisibility() == View.VISIBLE)
                shapes.setVisibility(View.GONE);
            else
                shapes.setVisibility(View.VISIBLE);
            copyPrimaryButtonFrom(shapeTool, ShapeToolOnClickListener);
        }

    };

    private final View.OnClickListener ZoomOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Zoom";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Set all other range sliders to invisible
            rangeSliderRotate.setVisibility(View.GONE);
            rangeSlider.setVisibility(View.GONE);
            rangeSliderZoom.setValueFrom(0.1f);
            rangeSliderZoom.setValueTo(0.99f);
            rangeSliderZoom.setValues(currentZoom);

            if (rangeSliderZoom.getVisibility() == View.VISIBLE)
                rangeSliderZoom.setVisibility(View.GONE);
            else
                rangeSliderZoom.setVisibility(View.VISIBLE);
            //copyPrimaryButtonFrom(zoom, ZoomOnClickListener);

        }
    };

    private final View.OnClickListener RotateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Rotate";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Set all other range sliders to invisible
            rangeSlider.setVisibility(View.GONE);
            rangeSliderZoom.setVisibility(View.GONE);

            if (rangeSliderRotate.getVisibility() == View.VISIBLE)
                rangeSliderRotate.setVisibility(View.GONE);
            else
                rangeSliderRotate.setVisibility(View.VISIBLE);
            copyPrimaryButtonFrom(rotate, RotateOnClickListener);
        }
    };

    private final View.OnClickListener PaintBucketOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Context context = getApplicationContext();
            CharSequence text = "Paint Dumper";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            primaryButton.setImageDrawable(paintBucket.getDrawable());
            int height = paint.getMeasuredHeight();
            int width = paint.getMeasuredWidth();
            int size;
            if(height > width) {
                size = height;
            } else {
                size = width;
            }
            correctedSize = size*4;
            //previousBrushSize = paint.getStrokeWidth();

            if (eraserClicked == true)
                paint.setColor(brushColor);
            paint.setEnabled(false);

            paint.setStrokeWidth(correctedSize);
            copyPrimaryButtonFrom(paintBucket, PaintBucketOnClickListener);
        }
    };

    private final View.OnClickListener DragOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            Context context = getApplicationContext();
            CharSequence text = "Drag";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            final float[] xDis = {0};
            final float[] yDis = {0};
                paint.setEnabled(true);
                paint.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //paint.setRotation(0);
                        float xDown = 0;
                        float yDown = 0;
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                paint.setRotation(0);
                                xDown = event.getX();
                                yDown = event.getY();
                                xDis[0] = event.getX();
                                yDis[0] = event.getY();
                                //paint.setRotation(currentRotation);
                                break;
                            case MotionEvent.ACTION_UP:
                                paint.setRotation(currentRotation);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                float movedX, movedY;
                                movedX = event.getX();
                                movedY = event.getY();

                                float distanceX = movedX - xDown;
                                float distanceY = movedY - yDown;

                                paint.setX(paint.getX() + distanceX - xDis[0]);
                                paint.setY(paint.getY() + distanceY - yDis[0]);


                                xDown = movedX;
                                yDown = movedY;
                                dragClickedBefore = true;
                                break;
                        }
                        //paint.setRotation(currentRotation);
                        return true;
                    }
                });

            copyPrimaryButtonFrom(drag, DragOnClickListener);
        }
    };

    private final View.OnClickListener SaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //getting current bitmap from DrawView
            Bitmap bitmap = paint.save();

            //opening outputstream to write into a file
            OutputStream imageOutStream = null;

            ContentValues contentValues = new ContentValues();

            //name of file current a preset
            String projectName = FileCreation.Companion.getProjectNameString() + ".png";

            //if the file exists, overwrite it.
            String duplicateFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/DolphinArt Projects/" + projectName;
            File dupFile = new File(duplicateFile);
            if(dupFile.exists()){
                dupFile.delete();
            }

            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, projectName);

            //filetype that we will be using now PNG
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

            //On Phone location of where file will be saved
            File pictureFolder = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
            );
            //Makes the DolphinArt Projects folder
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DolphinArt Projects");
            if (!mediaStorageDir.exists())
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
                int duration = Toast.LENGTH_SHORT;

                //The actual toast message
                Toast toast = Toast.makeText(context, savedText, duration);
                toast.show();

                // close the output stream after use
                imageOutStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DrawView.saved = true;
        }
    };
}