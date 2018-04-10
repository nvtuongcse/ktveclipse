package vn.com.sonca.newUI.modal.view.controler;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.view.View;

/**
 * Created by tuong on 16/01/2018.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.Touch.CustomView.TouchVolumnView;
import vn.com.sonca.zzzzz.MyApplication;

public class Volume extends View {

    private int min_val = 0;
    private Paint paintMain = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String title = "";
    private TouchVolumnView.OnVolumnListener listener;
    private int max_val = 15;
    private boolean mute;


    public void setVolumn(int status) {

        if(levelVolume!=status && !flagMove){
            tempValue = status;
            levelVolume=status;
            invalidate();
        }

    }

    public void setOnVolumnListener(TouchVolumnView.OnVolumnListener onVolumnListener) {
        this.listener = onVolumnListener;
    }

    public boolean isMute() {
        return true;
    }

    public int getVolumn() {
        return levelVolume;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public interface OnControlVolumeListener {
        public void OnChangeVolume(int value);
    }


    public Volume(Context context) {
        super(context);
        initView(context);
    }

    public Volume(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Volume);
        max_val = typedArray.getInt(R.styleable.Volume_max_value,15);
        min_val = typedArray.getInt(R.styleable.Volume_min_value,0);
        title = typedArray.getString(R.styleable.Volume_title);
        initView(context);
    }

    public Volume(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Volume);
        max_val = typedArray.getInt(R.styleable.Volume_max_value,15);
        min_val = typedArray.getInt(R.styleable.Volume_min_value,0);
        title = typedArray.getString(R.styleable.Volume_title);
    }

    private Typeface tfBold;
    private String textVolume;
    private Drawable drawBG_Volume;
    private Drawable drawTick, drawBG_Slide, drawBG_Fill, drawBG_Volume_Icon;
    private int levelVolume = 0;
    private void initView(Context context) {
//        tfBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
//        textVolume = getResources().getString(R.string.por_volume_1);
        drawBG_Volume = getResources().getDrawable(R.drawable.boder_1);
        drawTick = getResources().getDrawable(R.drawable.volume_15);
        drawBG_Slide = getResources().getDrawable(R.drawable.volume_slide);
        drawBG_Fill = getResources().getDrawable(R.drawable.volume_slide_act);
        drawBG_Volume_Icon = getResources().getDrawable(R.drawable.portrait_volume_icon);

    }

    private int widthLayout, heightLayout;
    private float textS, textX, textY, textY2;
    private Rect rectBG_Volume = new Rect();
    private Rect rectBG_Slide = new Rect();
    private Rect rectTick = new Rect();
    private Rect rectVolume = new Rect();
    private Rect rectVolume_Icon = new Rect();
    private Rect rectVolume_Fill = new Rect();
    private int spaceTick = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        widthLayout = w;
        heightLayout = h;

        int tamX = (int) (0.5*w);
        int tamY = (int) (90 * h / 1030);
        int height = 40 * h / 1030;
        int width = 88 * height / 38;
        rectBG_Volume.set(tamX - width, tamY - height, tamX + width, tamY + height);

        textS = 3 * rectBG_Volume.height() / 4;
        textY = rectBG_Volume.top + 0.45f * rectBG_Volume.height() + textS / 2;

        int mw = 15 * w / 430;
        int mTop = 185 * h / 1030;
        int mBottom = 900 * h / 1030;
        int mLeft = (int)(0.5*w) - mw;
        int mRight = (int)(0.5*w) + mw;
        rectBG_Slide.set(mLeft, mTop, mRight, mBottom);

        mTop = 195 * h / 1030;
        mBottom = 895 * h / 1030;
        mRight = (int)(0.5*w) - mw;
        mw = 100 * w / 430;
        mLeft = mRight - mw;
        rectTick.set(mLeft, mTop, mRight, mBottom);

        mTop = 210 * h / 1030;
        mBottom = 870 * h / 1030;
        rectVolume.set(mLeft, mTop, mRight, mBottom);
        spaceTick = rectVolume.height() / (max_val-min_val);

        textY2 = rectBG_Slide.bottom + 3 * textS / 2;

        tamX = (int) (0.5*w);
        tamY = (int) (90 * h / 1030);
        height = 40 * h / 1030;
        width = height;
        rectVolume_Icon.set(tamX - width, tamY - height, tamX + width, tamY + height);
        rectVolume_Fill.set(rectBG_Slide.left, rectVolume_Icon.centerY(), rectBG_Slide.right, rectBG_Slide.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        textPaint.setStyle(Style.FILL);
        textPaint.setTextSize(textS);
        textPaint.setTypeface(tfBold);

        drawBG_Volume.setBounds(rectBG_Volume);
        drawBG_Volume.draw(canvas);

        textPaint.setARGB(255, 155, 50, 5);
        canvas.drawText(levelVolume + "", rectBG_Volume.centerX() - textPaint.measureText(levelVolume + "") / 2, textY, textPaint);

//		textPaint.setARGB(255, 255, 255, 0);
//		canvas.drawRect(rectVolume, textPaint);

//        drawTick.setBounds(rectTick);
//        drawTick.draw(canvas);

        drawBG_Slide.setBounds(rectBG_Slide);
        drawBG_Slide.draw(canvas);

        textPaint.setARGB(255, 155, 50, 5);
        paintMain.setARGB(255,155,50,5);
        paintMain.setStrokeWidth(2.5f);
        canvas.drawText(title, rectBG_Volume.centerX() - textPaint.measureText(title) / 2, textY2, textPaint);

        // drawTick
        float pad = 5 * heightLayout / 400;
        float padX = 20 * widthLayout / 480;
        for(int i = 0;i<=max_val-min_val;i++) {
            canvas.drawLine(rectTick.left + rectTick.width()/3, rectBG_Slide.bottom - (i*spaceTick)-(float)rectVolume_Icon.height()/2, rectTick.left+ rectTick.width()/3*2, rectBG_Slide.bottom - i*spaceTick - (float)rectVolume_Icon.height()/2, paintMain);
        }
        if(max_val<15) {
            String str = min_val + "";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.bottom + pad, textPaint);
            str = (max_val + min_val) / 2 + "";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.centerY() + pad * 2, textPaint);
            str = max_val + "";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.top + pad * 3, textPaint);
        }
        else{
            String str = "0";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.bottom, textPaint);

            str = "5";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.bottom - (float)spaceTick*6 + pad*2, textPaint);

            str = "10";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.bottom - (float)spaceTick*11 + pad * 2, textPaint);

            str = "15";
            canvas.drawText(str, rectTick.left - textPaint.measureText(str) + padX, rectTick.bottom - (float)spaceTick*16 + pad * 3, textPaint);
        }
        if(flagMove){
            drawBG_Fill.setBounds(rectVolume_Fill);
            drawBG_Fill.draw(canvas);

            drawBG_Volume_Icon.setBounds(rectVolume_Icon);
            drawBG_Volume_Icon.draw(canvas);
        } else {
            if(tempValue > -1){
                valueToRect(tempValue);
                rectVolume_Fill.set(rectBG_Slide.left, rectVolume_Icon.centerY(), rectBG_Slide.right, rectBG_Slide.bottom);

                drawBG_Fill.setBounds(rectVolume_Fill);
                drawBG_Fill.draw(canvas);

                drawBG_Volume_Icon.setBounds(rectVolume_Icon);
                drawBG_Volume_Icon.draw(canvas);
            }
        }

    }

    private int tempValue = 0;
    private boolean flagMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:{
                flagMove = true;
                float mX = event.getX();
                float mY = event.getY();
                //MyLog.d("controlVolume", "==ACTION_MOVE=mX="+mX+"=mY="+mY+"=left="+rectTick.left+"=right="+rectBG_Slide.right+"=top="+rectVolume.top+"=bottom="+rectVolume.bottom);
                if(mX >= (rectTick.left - rectBG_Volume.width()) && mX <= (rectBG_Slide.right + rectBG_Volume.width()) && mY >= rectVolume.top && mY <= rectVolume.bottom){
                    rectVolume_Icon.set(rectVolume_Icon.left, (int)(mY - rectVolume_Icon.height() / 2), rectVolume_Icon.right, (int)(mY + rectVolume_Icon.height() / 2));
                    rectVolume_Fill.set(rectBG_Slide.left, rectVolume_Icon.centerY(), rectBG_Slide.right, rectBG_Slide.bottom);
                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_UP:{
                flagMove = false;
                positionToValue(rectVolume_Icon.centerY() - spaceTick / 2);
                //MyLog.d("controlVolume", "==ACTION_UP=tempValue="+tempValue);
                if(tempValue > -1){
                    if(min_val<0)
                    levelVolume = tempValue+min_val;
                    else levelVolume = tempValue;
                    invalidate();
                    if(levelVolume==0) {
                        mute = true;
                        if (listener != null)
                            listener.onMute(mute);
                    }
                    else {
                        mute = false;
                        if (listener != null)
                            listener.onMute(mute);
                    }
                    if (listener != null) {
                        listener.onVolumn(levelVolume);
                    }
                }
            }
            break;
            case MotionEvent.ACTION_DOWN:{
                float mX = event.getX();
                float mY = event.getY();
                if(mX >= (rectTick.left - rectBG_Volume.width()) && mX <= (rectBG_Slide.right + rectBG_Volume.width()) && mY >= rectVolume.top && mY <= rectVolume.bottom){
                    //MyLog.d("controlVolume", "==ACTION_DOWN==");
                    rectVolume_Icon.set(rectVolume_Icon.left, (int)(mY - rectVolume_Icon.height() / 2), rectVolume_Icon.right, (int)(mY + rectVolume_Icon.height() / 2));
                    rectVolume_Fill.set(rectBG_Slide.left, rectVolume_Icon.centerY(), rectBG_Slide.right, rectBG_Slide.bottom);
                    invalidate();
                }
            }
        }
        return true;
    }

    public int getLevelVolume(){
        return levelVolume;
    }

    private void valueToRect(int value){
        int startCenterY = rectVolume.bottom;
        int realCenterY = (int) (startCenterY - value * spaceTick);
        rectVolume_Icon.set(rectVolume_Icon.left, realCenterY - rectVolume_Icon.height() / 2, rectVolume_Icon.right, realCenterY + rectVolume_Icon.height() / 2);
    }

    private void positionToValue(float mY){
        int startFloatY_Bottom = rectVolume.bottom;
        int startFloatY_Top = (int) (rectVolume.bottom - spaceTick);

//		MyLog.e("ControlVolume", "positionToValue-----------------");
//		MyLog.d("ControlVolume", "mY = " + mY);
//		MyLog.d("ControlVolume", "startFloatY_Bottom = " + startFloatY_Bottom);
//		MyLog.d("ControlVolume", "startFloatY_Top = " + startFloatY_Top);
//		MyLog.d("ControlVolume", "spaceTick = " + spaceTick);

        if(mY <= startFloatY_Bottom && mY >= startFloatY_Top){
            tempValue = 0;
            return;
        }

        int countValue = 0;
        do {
            countValue++;
            startFloatY_Bottom -= spaceTick;
            startFloatY_Top -= spaceTick;

            if(mY <= startFloatY_Bottom && mY >= startFloatY_Top){
                //MyLog.d("ControlVolume", "tempValue = " + tempValue);
                tempValue = countValue;
                return;
            }

        } while (mY > startFloatY_Bottom || mY < startFloatY_Top || countValue < (max_val-min_val));

        tempValue = -1;

    }
    public static final int INTCOMMAND = 4;
    public static void setCommandEnable(boolean bool){
        if (bool) {
            MyApplication.intCommandEnable |= INTCOMMAND;
        } else {
            MyApplication.intCommandEnable &= (~INTCOMMAND);
        }
    }

    public static boolean getCommandEnable(){
        return (MyApplication.intCommandEnable & INTCOMMAND) == INTCOMMAND;
    }

    public static final int INTMEDIUM = 2;
    public static void setCommandMedium(int value){
        int clear = 0x00000003;
        MyApplication.intCommandMedium &= (~(clear << INTMEDIUM));
        MyApplication.intCommandMedium |= (value << INTMEDIUM);
    }

    public static int getCommandMedium(){
        int clear = 0x00000003;
        int retur = (MyApplication.intCommandMedium & (clear << INTMEDIUM)) >> INTMEDIUM;
        return retur;
    }

}
