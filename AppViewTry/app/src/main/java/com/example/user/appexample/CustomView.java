package com.example.user.appexample;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path;
import android.graphics.Paint.Style;

import android.os.Build;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.AttributeSet;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Toast; 
 
public class CustomView extends View{
	//Paint.ANTI_ALIAS_FLAG
	Paint paint = new Paint();
	Path mouthPath = new Path();
	private int faceColor = Color.YELLOW;
	private int mouthColor = Color.RED;
	private int eyeRigthColor = Color.BLUE; 
	private int eyeLeftColor = Color.RED;
	private int eyesColor = Color.BLACK;
	private int borderColor = Color.BLACK;
	private float borderWidth = 4;
	private int size = 320;
	
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		drawFaceBackground(canvas);
		drawEyes(canvas);
		drawMouth(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){	
		super.onMeasure(widthMeasureSpec , heightMeasureSpec);
		size = Math.min(View.MeasureSpec.getSize(widthMeasureSpec) , View.MeasureSpec.getSize(heightMeasureSpec));
		setMeasuredDimension(size , size);

	}
	
	private void drawFaceBackground(Canvas canvas){	
		paint.setColor(faceColor);
		paint.setStyle(Paint.Style.FILL);
		float radius = size / 2f;
		canvas.drawCircle(size / 2f, size / 2f, radius, paint);

		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(borderWidth);

		canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);
	}
	
	private void drawEyes(Canvas canvas){	
		paint.setColor(eyesColor);
		paint.setStyle(Paint.Style.FILL);
		RectF leftEyeRect = new RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f);
		canvas.drawOval(leftEyeRect, paint);
		RectF rightEyeRect = new RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f);
		canvas.drawOval(rightEyeRect, paint);
	}
	
	private void drawMouth(Canvas canvas){	
		mouthPath.moveTo(size * 0.22f, size * 0.7f);
		mouthPath.quadTo(size * 0.50f, size * 0.70f, size * 0.78f, size * 0.70f);
		mouthPath.quadTo(size * 0.50f, size * 1.05f, size * 0.22f, size * 0.70f);
		paint.setColor(mouthColor);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(mouthPath, paint);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(borderWidth + 10);
		canvas.drawPath(mouthPath, paint);
	}
	

	
	
}