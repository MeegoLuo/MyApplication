package com.example.meego.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by meego on 2016/12/31.
 */

public class TrackView extends View {

    private int oldColor= 0xff000000;

    private int newColor= 0xffff0000;

    private float progress;

    private int textSize;

    private String text="元旦来了";

    private Rect textBounds=new Rect();

    private int textWidth;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    private int viewWidth;

    private int start;

    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    private Context context;

    public TrackView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public TrackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public TrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init(){

        mPaint.setTextSize(dip2px(context,30));//设置字体大小
        textWidth=(int)mPaint.measureText(text);//字体的长度
        //放置字体的矩形
        mPaint.getTextBounds(text,0,text.length(),textBounds);

    }

    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureWidth(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);

        //设置试图大小
        setMeasuredDimension(width,height);
        //视图的宽度
        viewWidth=width-getPaddingLeft()-getPaddingRight();
        //字体的起点
        start=viewWidth/2-textWidth/2;
    }

    private int measureHeight(int heightMeasureSpec){
       int heightMode= MeasureSpec.getMode(heightMeasureSpec);
       int height=  MeasureSpec.getSize(heightMeasureSpec);
        int result=0;
        if(heightMode==MeasureSpec.EXACTLY){
            result=height;
        }else {
            result=Math.min(textBounds.height(),height);
        }
        return result+getPaddingBottom()+getPaddingTop();
    }
    private int measureWidth(int widthMeasureSpec){
        int heightMode= MeasureSpec.getMode(widthMeasureSpec);
        int height=  MeasureSpec.getSize(widthMeasureSpec);
        int result=0;
        if(heightMode==MeasureSpec.EXACTLY){
            result=height;
        }else {
            result=Math.min(textBounds.width(),height);
        }
        return result+getPaddingLeft()+getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawoldText(canvas);
        drawNewText(canvas);
    }
    private void drawNewText(Canvas canvas){
        mPaint.setColor(newColor);
        canvas.save();
        canvas.clipRect(start,0,getMeasuredWidth()*progress,getMeasuredHeight());
        canvas.drawText(text,start,getMeasuredHeight()/2+textBounds.height()/2,mPaint);
        canvas.restore();

    }
    private void drawoldText(Canvas canvas){
        mPaint.setColor(oldColor);
        canvas.save();
        canvas.drawText(text,start,getMeasuredHeight()/2+textBounds.height()/2,mPaint);
        canvas.restore();

    }
}
