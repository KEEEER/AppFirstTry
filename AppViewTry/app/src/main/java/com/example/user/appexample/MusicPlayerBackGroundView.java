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

 
public class MusicPlayerBackGroundView extends View{
	Paint paint = new Paint();

	private int BackGroundColor = Color.GRAY;
	private int BackGroundStyle = 1; // 0 for rect , else for RoundRect
	private int usableWidth = 0;
	private int usableHeight = 0;
	
	private float LeftBound = 0.05f;
	private float RightBound = 0.95f;
	private float TopBound = 0.05f;
	private float ButtomBound = 0.35f;
	
	private RectF backGroundRect;
	
	private int size = 320;
	
    public MusicPlayerBackGroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
		paint.setStyle(Paint.Style.STROKE);
    }
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		drawBackground(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){	
		super.onMeasure(widthMeasureSpec , heightMeasureSpec);
		usableWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		usableHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		size = Math.min(usableWidth , usableHeight);
		setMeasuredDimension(usableWidth , usableHeight);
		backGroundRect = new RectF((int)(usableWidth*LeftBound) , (int)(usableHeight * TopBound) , (int)(usableWidth* RightBound) , (int)(usableHeight * ButtomBound));
	}
	
	private void drawBackground(Canvas canvas){	
		paint.setColor(BackGroundColor);
		
		paint.setStrokeWidth(25);
		canvas.drawRoundRect(backGroundRect, 6, 6, paint);

	}
	public void setBackGroundStyle(int style){
		BackGroundStyle = style;
		if(BackGroundStyle >= 1){
			paint.setColor(BackGroundColor);
			paint.setStyle(Paint.Style.STROKE);
			
		}
		else{
			paint.setColor(BackGroundColor);
			paint.setStyle(Paint.Style.FILL);
		}
		invalidate();
	}
	public void setBackGroundColor(int color){
		BackGroundColor = color;
		paint.setColor(BackGroundColor);
		invalidate();
	}

	public int getBackGroundWidth(){
		return (int)((usableWidth) * (RightBound - LeftBound));
	}
	public int getBackGroundHeight(){
		return (int)((usableHeight) * (ButtomBound - TopBound));
	}
	public int getStartX(){
		return (int)(usableWidth * LeftBound);
	}
	public int getStartY(){
		return (int)(usableHeight * TopBound);
	}
	public int getEndX(){
		return (int)(usableWidth * (LeftBound));
	}
	public int getEndY(){
		return (int)(usableHeight * (TopBound));
	}
	
	
	

	
	
}