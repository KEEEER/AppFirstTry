package com.example.user.appexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path;
import android.graphics.Paint.Style;
import java.lang.Math;
import android.os.Build;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.AttributeSet;

import android.view.View;
import android.view.View.OnClickListener;

 
public class MusicPlayerPlayPauseView extends View{
	Paint paint = new Paint();
	Path PlayPath = new Path();

 	private int BackGroundColor = Color.RED;
	private int TriangleColor = Color.BLACK;

	private float radius = 100.0f;	
	private float cx = 600.0f;
	private float cy = 600.0f;
	private float TriangleBound = 0.85f;

	private int usableWidth = 0;
	private int usableHeight = 0;
	private int size = 320;
	
    public MusicPlayerPlayPauseView(Context context, AttributeSet attrs) {
        super(context, attrs);
		paint.setStyle(Paint.Style.FILL);
    }
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		drawBackGroundCircle(canvas);
		drawInsidePic(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){	
		super.onMeasure(widthMeasureSpec , heightMeasureSpec);
		usableWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		usableHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		size = Math.min(usableWidth , usableHeight);
		setMeasuredDimension(size , size);
	}

	private void drawBackGroundCircle(Canvas canvas){
		paint.setColor(BackGroundColor);
		canvas.drawCircle(cx , cy , radius , paint);

	}
	private void drawInsidePic(Canvas canvas){
		float reduceRadius = radius * TriangleBound;
		float XYdegree45 = (float)Math.ceil(Math.sqrt((double)(reduceRadius * reduceRadius  / 2)));
		PlayPath.moveTo(cx - XYdegree45 , cy - XYdegree45);
		PlayPath.lineTo(cx - XYdegree45 , cy + XYdegree45);
		PlayPath.lineTo(cx + (int)(radius * TriangleBound) , cy);
	//	PlayPath.moveTo(cx + (int)(radius * TriangleBound) , cy);
	//	PlayPath.lineTo(cx - (int)(radius * TriangleBound) , cy + (int)(radius * TriangleBound));
		paint.setColor(TriangleColor);
		canvas.drawPath(PlayPath , paint);
	}
	//invalidate();
	public void setXY(int x , int y){
		cx = x;
		cy = y;
		//invalidate();
	}
	public void setRadius(int radius){
		radius = radius;
		invalidate();
	}
	public void setTriangleColor(int color){	
		TriangleColor = color;
		invalidate();
	}
	public void setBackGroundCircleColor(int color){
		BackGroundColor = color;
		invalidate();
	}
	public void refresh(){
		invalidate();
	}
}