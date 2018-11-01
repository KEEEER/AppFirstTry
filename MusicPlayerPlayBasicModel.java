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

 
public class MP extends View{
	Paint paint = new Paint();
	private int usableWidth = 0;
	private int usableHeight = 0;
	private int size = 320;
	
    public MP(Context context, AttributeSet attrs) {
        super(context, attrs);
		paint.setStyle(Paint.Style.FILL);
    }
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		draw(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec){	
		super.onMeasure(widthMeasureSpec , heightMeasureSpec);
		usableWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		usableHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		size = Math.min(usableWidth , usableHeight);
		setMeasuredDimension(size , size);
	}
	
	private void draw(Canvas canvas){	


	}
	public void method(){
		invalidate();
	}
	
	
	
	
	

	
	
}