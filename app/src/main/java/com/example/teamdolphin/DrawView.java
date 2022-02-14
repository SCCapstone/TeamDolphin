package com.example.teamdolphin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class DrawView extends View {

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;

    //utilizes paint class as encapsulation of paint information
    private Paint mPaint;

    //Using an ArrayList to store all strokes created by the user
    private ArrayList<Stroke> paths = new ArrayList<>();

    //initializations
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    //Constructor for initialization of attributes
    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        //this is meant to smoothen the strokes of users
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setAlpha(0xff);

    }

    //instantiation of bitmap and brush settings
    public void init(int height, int width, int background, String path) {

        //This condition applies if the user is clicking on already made project
        if(path!=null) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(path);
            height = imageBitmap.getHeight();
            width = imageBitmap.getWidth();
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawBitmap(imageBitmap, 0f, 0f, null);
            //set the initial color of brush
            if (background == Color.BLACK)
                currentColor = Color.WHITE;
            else
                currentColor = Color.BLACK;

            //set the initial size of brush
            strokeWidth = 16;
        }
        //This condition applies to newly made projects using FileCreation Page
        else{
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            //set the initial color of brush
            if(background==Color.BLACK)
                currentColor = Color.WHITE;
            else
                currentColor = Color.BLACK;

            //set the initial size of brush
            strokeWidth = 16;

            //sets default color of canvas
            mCanvas.drawColor(background);
        }
    }

    //set the initial color of the brush
    public void setColor(int color) {
        currentColor = color;
    }

    //set the initial width of the brush
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    //uses the array list to check for a previous stroke to undo
    public void undo() {
        if (paths.size() != 0) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }

    //returns the current bitmap which is canvas
    public Bitmap save() {
        return mBitmap;
    }

    //main method for drawing on canvas
    @Override
    protected void onDraw(Canvas canvas) {
        //saves current canvas state
        canvas.save();

        //iterates through the list of paths
        for (Stroke fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    /* when a user first touches the canvas
     * this method adds the touch as a stroke
     * to the paths list */
    private void touchStart(float x, float y) {
        mPath = new Path();
        Stroke fp = new Stroke(currentColor, strokeWidth, mPath);
        paths.add(fp);

        //resets the path
        mPath.reset();

        //sets the starting point of the stroke to user touch
        mPath.moveTo(x, y);

        //set current coordinates of user press
        mX = x;
        mY = y;
    }

    /* checks movement of user press
     * and compares to TOUCH_TOLERANCE to see if
     * quadTo is needed to smoothen the path drawn */
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    /* when user lets go of press
     * call lineTo which ends the path
     * entry in the ArrayList */
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    /* onTouchEvent tells the canvas
     * which event is currently taking place
     * using the switch method and acts
     * accordingly*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}