package com.ranglerz.tlc.tlc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ranglerz.tlc.tlc.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by User-10 on 30-Aug-16.
 */
public class CaptureSignature extends View {

    LinearLayout mContent;
    static final float STROKE_WIDTH = 10f;
    static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    Paint paint = new Paint();
    Path path = new Path();

    float lastTouchX;
    float lastTouchY;
    final RectF dirtyRect = new RectF();


    // default constructor for Class
    public CaptureSignature(LinearLayout mContent,Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContent= mContent;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    // for clear canvas
    public void clear() {
        path.reset();
        invalidate();
//        save.setEnabled(false);
    }


    // for save the bitmap to file and then return the bitmap

    public Bitmap save() throws IOException {
        // get the bitmap from the LinearLayout
        // create the bitmap
        Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
                mContent.getHeight(), Bitmap.Config.ARGB_8888);
        // create a canvas to draw the bitmap get from the Layout
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = mContent.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        // draw the canvas on Linear layout
        mContent.draw(canvas);

        // Convert Bitmap into Bytes Array
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
        Intent intent = new Intent();
        intent.putExtra("byteArray", bs.toByteArray());
//        setResult(1, intent);


        // create a unique fileName
        UUID id = UUID.randomUUID();
        String fileName = "TLCSignature" + id;

        // creat a folder
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"TLCSignature");

        // if directory not exist create a new dirctory
        if(!storageDir.exists()) {
            Log.d("tag","Directory Not exist Creating New");
            storageDir.mkdir();
        }


        // create a file in a directory
        File signatureFile = File.createTempFile(
                fileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        try {

            // create a stream to write a file from bitmap
            FileOutputStream out = new FileOutputStream(signatureFile);
//               returnedBitmap = Bitmap.createScaledBitmap(returnedBitmap, 50, 30, false);
            returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnedBitmap;
//        finish();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // draw what user draw with his finger on canvas
        canvas.drawPath(path, paint);
    }


    // when use tocuhes the canvas
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
//        save.setEnabled(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }



}