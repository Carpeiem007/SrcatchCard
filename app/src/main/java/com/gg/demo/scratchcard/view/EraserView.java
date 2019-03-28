package com.gg.demo.scratchcard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gg.demo.scratchcard.R;

public class EraserView extends View {

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

    private Paint mPaint;

    private Bitmap mSrc, mDst;

    private float mEventX, mEventY;

    private Path mPath;

    private Canvas canvas;

    public EraserView(Context context) {
        this(context, null);
    }

    public EraserView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(80);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE,null);

        mSrc = BitmapFactory.decodeResource(getResources(), R.drawable.bg_1);
        mDst = Bitmap.createBitmap(mSrc.getWidth(), mSrc.getHeight(), Bitmap.Config.ARGB_8888);

        mPath = new Path();

        canvas = new Canvas(mDst);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas.drawPath(mPath, mPaint);
//        Canvas dstCanvas = new Canvas(mDst);
//        dstCanvas.drawPath(mPath, mPaint);

        canvas.drawBitmap(mDst, 0, 0, mPaint);

        mPaint.setXfermode(xfermode);

        canvas.drawBitmap(mSrc, 0, 0, mPaint);

        mPaint.setXfermode(null);

    }

    public void reset() {
        this.setVisibility(VISIBLE);
        mPath.reset();
        if (mDst != null && !mDst.isRecycled()) {
            mDst.recycle();
        }
        mDst = Bitmap.createBitmap(mDst.getWidth(), mDst.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mDst);
        invalidate();


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEventX = event.getX();
                mEventY = event.getY();
                mPath.moveTo(mEventX, mEventY);
                break;
            case MotionEvent.ACTION_MOVE:
                final float endX = (event.getX() - mEventX) / 2 + mEventX;
                final float endY = (event.getY() - mEventY) / 2 + mEventY;

                mPath.quadTo(mEventX, mEventY, endX, endY);

                mEventX = event.getX();
                mEventY = event.getY();
                break;
                //松开手指的时候判断已经划开的区域是否已经包含了参照区域，如果已经包含了，直接全部展示出来
            case MotionEvent.ACTION_UP:
               RectF rect = new RectF();
               mPath.computeBounds(rect,true);
               RectF consult  =new RectF(getWidth()*0.35f,getHeight()*0.35f,getWidth()*0.6f,getHeight()*0.6f);
               if(rect.contains(consult)){
                   this.setVisibility(GONE);
               }
                break;
            default:
        }
        invalidate();
        return true;
//        return super.onTouchEvent(event);
    }
}
