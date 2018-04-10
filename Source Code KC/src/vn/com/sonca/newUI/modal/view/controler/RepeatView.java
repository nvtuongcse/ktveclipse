package vn.com.sonca.newUI.modal.view.controler;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.Touch.touchcontrol.TouchMainActivity;
import vn.com.sonca.zktv.main.KTVMainActivity;
import vn.com.sonca.zzzzz.MyApplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RepeatView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    // private Context context;
    private String text;

    private float widthLayout;
    private float heightLayout;
    private float widthView;
    private float heightView;

    private boolean isTouch = false;

    private float backgroundWidth;
    private float backgroundHeight;
    private float drawableWidth;
    private float drawableHeight;

    private float drawableDY;
    private float textDY;
    private float textScale;

    private float STROKE_WIDTH;
    private float PADDING;
    private static final String FILL_COLOR = "#03223f";
    private static final String STROKE_COLOR_1 = "#004e90";
    private static final String STROKE_COLOR_2 = "#264e67";
    private static final String TEXT_COLOR = "#b4feff";

    private vn.com.sonca.Touch.CustomView.TouchRepeatView.OnRepeatListener listener;

    public interface OnRepeatListener {
        public void onRepeat();
        public void OnInActive();
    }

    public void setOnRepeatListener(vn.com.sonca.Touch.CustomView.TouchRepeatView.OnRepeatListener listener) {
        this.listener = listener;
    }

    public RepeatView(Context context) {
        super(context);
        initView(context);
    }

    public RepeatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public RepeatView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private boolean flagBigSize = false;
    public void setBigLayout(boolean flagBigSize){
        this.flagBigSize = flagBigSize;
        invalidate();
    }

    private Drawable drawBG;
    private Drawable activeBackgroundHover;
    private Drawable activeBackground;
    private Drawable drawable;

    private Drawable zlight_activeBackground, zlight_activeBackgroundHover,
            zlight_drawable, zlight_drawBG;

    private void initView(Context context) {
        // this.context = context;
        activeBackground = getResources().getDrawable(R.drawable.icon_repeat);
        activeBackgroundHover = getResources().getDrawable(R.drawable.icon_repeat_focus);
//		drawable = getResources().getDrawable(R.drawable.icon_repeat_focus);
        drawBG = getResources().getDrawable(R.drawable.icon_repeat);

        text = getResources().getString(R.string.main_left_8);

        zlight_activeBackground = getResources().getDrawable(
                R.drawable.zlight_control_boder_active);
        zlight_activeBackgroundHover = getResources().getDrawable(
                R.drawable.zlight_control_boder_hover);
        zlight_drawable = getResources().getDrawable(
                R.drawable.zlight_touch_mc_repeat);
        zlight_drawBG = getResources().getDrawable(R.drawable.zlight_repeat_inactive);
    }

    private boolean isConnected;
    public void setConnect(boolean isConnected){
        this.isConnected = isConnected;
        invalidate();
    }

    private boolean isPlayingSong = false;
    private boolean boolBlockComand = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        heightLayout = getHeight() * 1f;
        widthLayout = getHeight() * 1f * 4 / 3;

        STROKE_WIDTH = widthLayout / 100;
        PADDING = STROKE_WIDTH;

        drawableDY = heightLayout / 11;
        textDY = widthLayout / 7f;
        textScale = widthLayout / 10;

        backgroundHeight = 0.5f*heightLayout;
        backgroundWidth = backgroundHeight/0.6f;

        drawableWidth = drawableHeight = 0.7f * heightLayout;

        widthView = widthLayout - PADDING * 2;
        heightView = heightLayout - PADDING * 2;

        canvas.save();
        canvas.translate((getWidth() - widthLayout) / 2,
                (getHeight() - heightLayout) / 2);

        isConnected = TouchMainActivity.serverStatus != null || KTVMainActivity.serverStatus != null;

        if (TouchMainActivity.serverStatus != null || KTVMainActivity.serverStatus != null) {
            boolean flagControlFullAPI = false;
            if(MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
                if (KTVMainActivity.serverStatus.isOnOffControlFullAPI()) {
                    flagControlFullAPI = true;
                }
            } else {
                if (TouchMainActivity.serverStatus.isOnOffControlFullAPI()) {
                    flagControlFullAPI = true;
                }
            }

            if (flagControlFullAPI) {
                int clear = 0x00000003;
                int retur = (MyApplication.intCommandMedium & (clear << INTMEDIUM)) >> INTMEDIUM;
                if((MyApplication.flagDeviceUser == true && retur != 0) ||
                        (MyApplication.flagDeviceUser == false && retur == 2)){
                    boolBlockComand = true;
                }else{
                    boolBlockComand = false;
                }
            }else{
                if((MyApplication.intCommandEnable & INTCOMMAND) != INTCOMMAND){
                    boolBlockComand = true;
                }else{
                    boolBlockComand = false;
                }
            }
        }

        if (MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI || flagBigSize) { // THEME-BLUE
            if(boolBlockComand == true){
                drawBG.setBounds(
                        (int) ((widthLayout - backgroundWidth) / 2),
                        (int) ((heightLayout - backgroundHeight) / 2),
                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                drawBG.draw(canvas);
                return;
            }

            if(MyApplication.intWifiRemote != MyApplication.SONCA){
                isPlayingSong = true;
            }else{
                if(TouchMainActivity.serverStatus != null || KTVMainActivity.serverStatus != null){
                    if(MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
                        isConnected = KTVMainActivity.serverStatus.getPlayingSongID() != 0;
                    } else {
                        isConnected = TouchMainActivity.serverStatus.getPlayingSongID() != 0;
                    }

                    isPlayingSong = isConnected;
                }
            }

            if(MyApplication.intSvrModel == MyApplication.SONCA_SMARTK && MyApplication.flagPlayingYouTube){
                isConnected = true;
                isPlayingSong = isConnected;
            }

            mPaint.setTextSize(textScale);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            mPaint.setARGB(178, 255, 255, 255);
            Rect boundRect = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), boundRect);
            canvas.drawText(text, widthLayout / 2 - boundRect.width() / 2,
                    heightLayout*0.9f,
                    mPaint);

            if (isConnected) {
                if (isPlayingSong) {
                    if (isTouch) {
                        activeBackgroundHover
                                .setBounds(
                                        (int) ((widthLayout - backgroundWidth) / 2),
                                        (int) ((heightLayout - backgroundHeight) / 2),
                                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                        activeBackgroundHover.draw(canvas);
                    } else {
                        activeBackground
                                .setBounds(
                                        (int) ((widthLayout - backgroundWidth) / 2),
                                        (int) ((heightLayout - backgroundHeight) / 2),
                                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                        activeBackground.draw(canvas);
                    }

                    drawable.setBounds(
                            (int) ((widthLayout - drawableWidth) / 2),
                            (int) ((heightLayout - drawableHeight) / 2 - drawableDY),
                            (int) ((widthLayout - drawableWidth) / 2 + drawableWidth),
                            (int) ((heightLayout - drawableHeight) / 2
                                    + drawableHeight - drawableDY));
                    drawable.draw(canvas);



                    resetPaint();

                } else {
                    drawBG.setBounds(
                            (int) ((widthLayout - backgroundWidth) / 2),
                            (int) ((heightLayout - backgroundHeight) / 2),
                            (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                            (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                    drawBG.draw(canvas);
                }
            } else {
                drawBG.setBounds(
                        (int) ((widthLayout - backgroundWidth) / 2),
                        (int) ((heightLayout - backgroundHeight) / 2),
                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                drawBG.draw(canvas);
            }
        } else if (MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT) { // THEME-LIGHT
            if(boolBlockComand == true){
                zlight_drawBG.setBounds(
                        (int) ((widthLayout - backgroundWidth) / 2),
                        (int) ((heightLayout - backgroundHeight) / 2),
                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                zlight_drawBG.draw(canvas);

                mPaint.setTextSize(textScale);
                mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                mPaint.setARGB(178, 255, 255, 255);
                Rect boundRect = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), boundRect);
                canvas.drawText(text, widthLayout / 2 - boundRect.width() / 2,
                        heightLayout / 2 + boundRect.height() / 2 + textDY,
                        mPaint);
                return;
            }

            if(MyApplication.intWifiRemote != MyApplication.SONCA){
                isPlayingSong = true;
            }else{
                if(TouchMainActivity.serverStatus != null || KTVMainActivity.serverStatus != null){
                    if(MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
                        isConnected = KTVMainActivity.serverStatus.getPlayingSongID() != 0;
                    } else {
                        isConnected = TouchMainActivity.serverStatus.getPlayingSongID() != 0;
                    }

                    isPlayingSong = isConnected;
                }
            }

            if(MyApplication.intSvrModel == MyApplication.SONCA_SMARTK && MyApplication.flagPlayingYouTube){
                isConnected = true;
                isPlayingSong = isConnected;
            }

            if (isConnected) {
                if (isPlayingSong) {
                    if (isTouch) {
                        zlight_activeBackgroundHover
                                .setBounds(
                                        (int) ((widthLayout - backgroundWidth) / 2),
                                        (int) ((heightLayout - backgroundHeight) / 2),
                                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                        zlight_activeBackgroundHover.draw(canvas);
                    } else {
                        zlight_activeBackground
                                .setBounds(
                                        (int) ((widthLayout - backgroundWidth) / 2),
                                        (int) ((heightLayout - backgroundHeight) / 2),
                                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                        zlight_activeBackground.draw(canvas);
                    }

                    zlight_drawable.setBounds(
                            (int) ((widthLayout - drawableWidth) / 2),
                            (int) ((heightLayout - drawableHeight) / 2 - drawableDY),
                            (int) ((widthLayout - drawableWidth) / 2 + drawableWidth),
                            (int) ((heightLayout - drawableHeight) / 2
                                    + drawableHeight - drawableDY));
                    zlight_drawable.draw(canvas);

                    mPaint.setTextSize(textScale);
                    mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    mPaint.setColor(Color.WHITE);
                    Rect boundRect = new Rect();
                    mPaint.getTextBounds(text, 0, text.length(), boundRect);
                    canvas.drawText(text, widthLayout / 2 - boundRect.width() / 2,
                            heightLayout / 2 + boundRect.height() / 2 + textDY,
                            mPaint);

                    resetPaint();

                } else {
                    zlight_drawBG.setBounds(
                            (int) ((widthLayout - backgroundWidth) / 2),
                            (int) ((heightLayout - backgroundHeight) / 2),
                            (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                            (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                    zlight_drawBG.draw(canvas);

                    mPaint.setTextSize(textScale);
                    mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    mPaint.setARGB(178, 255, 255, 255);
                    Rect boundRect = new Rect();
                    mPaint.getTextBounds(text, 0, text.length(), boundRect);
                    canvas.drawText(text, widthLayout / 2 - boundRect.width() / 2,
                            heightLayout / 2 + boundRect.height() / 2 + textDY,
                            mPaint);

                    resetPaint();
                }
            } else {
                zlight_drawBG.setBounds(
                        (int) ((widthLayout - backgroundWidth) / 2),
                        (int) ((heightLayout - backgroundHeight) / 2),
                        (int) ((widthLayout - backgroundWidth) / 2 + backgroundWidth),
                        (int) ((heightLayout - backgroundHeight) / 2 + backgroundHeight));
                zlight_drawBG.draw(canvas);

                mPaint.setTextSize(textScale);
                mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                mPaint.setARGB(178, 255, 255, 255);
                Rect boundRect = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), boundRect);
                canvas.drawText(text, widthLayout / 2 - boundRect.width() / 2,
                        heightLayout / 2 + boundRect.height() / 2 + textDY,
                        mPaint);

                resetPaint();
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(boolBlockComand == true){
            return true;
        }
        if (isConnected) {
            if (event.getX() < (getWidth() - widthLayout) / 2
                    || (getWidth() - event.getX()) < (getWidth() - widthLayout) / 2
                    || event.getY() < (getHeight() - heightLayout) / 2
                    || (getHeight() - event.getY()) < (getHeight() - heightLayout) / 2) {
                if (isTouch) {
                    isTouch = false;
                    invalidate();
                    return true;
                }
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    isTouch = false;
                    invalidate();
                    if (listener != null) {
                        listener.onRepeat();
                    }
                    return true;
                case MotionEvent.ACTION_DOWN:
                    isTouch = true;
                    invalidate();
                    return true;
            }
        } else {
            if(event.getAction() == MotionEvent.ACTION_UP){
                if(listener != null){
                    listener.OnInActive();
                }
            }
        }
        return true;
    }

    private void resetPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    public static final int INTCOMMAND = 1024;
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

    public static final int INTMEDIUM = 18;
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
