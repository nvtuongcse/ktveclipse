package vn.com.sonca.newUI.modal.view.controler;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.zzzzz.MyApplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

public class MuteView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath;
    private Drawable drawable = null;
    private String title;

    private float widthLayout;
    private float heightLayout;

    private float centerX, centerY;
    private float radius;

    private float STROKE_WIDTH_1;
    private float PADDING;

    private static final String STROKE_COLOR_1 = "#91d2d6";

    private boolean isMute;
    private float textScale;

    public void setMute(boolean isMute) {
        this.isMute = isMute;
        invalidate();
        requestLayout();
    }

    public boolean getMute() {
        return isMute;
    }

    public MuteView(Context context) {
        super(context);
        initView(context);
    }

    public MuteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public MuteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        isMute = false;
        title = getResources().getString(R.string.ktv_caocap_7).toUpperCase();
    }

    private vn.com.sonca.Touch.CustomView.SliderMuteView.OnMuteListener listener;

    public interface OnMuteListener {
        public void OnMuteSlider(boolean isMute);
    }

    public void setOnMuteListener(vn.com.sonca.Touch.CustomView.SliderMuteView.OnMuteListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setK(getWidth(), getHeight());

        mPaint.setTextSize(textScale);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT,
                Typeface.BOLD));
        mPaint.setARGB(178, 255, 255, 255);
        Rect boundRect = new Rect();
        mPaint.getTextBounds(title, 0, title.length(), boundRect);
        canvas.drawText(title, widthLayout / 2 - boundRect.width() / 2,
                heightLayout*0.9f,
                mPaint);

        if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
            if (isMute) {
                drawable = getResources().getDrawable(R.drawable.icon_mute);
            } else {
                drawable = getResources().getDrawable(R.drawable.icon_sound);
            }
            drawable.setBounds(KD1L, KD1T, KD1R, KD1B);
            drawable.draw(canvas);
        } else if(MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT){
            if (isMute) {
                drawable = getResources().getDrawable(R.drawable.zlight_loa_on);
            } else {
                drawable = getResources().getDrawable(R.drawable.zlight_loa_off);
            }
            drawable.setBounds(KD1L, KD1T, KD1R, KD1B);
            drawable.draw(canvas);
        }
    }

    private int KD1L, KD1R, KD1T, KD1B;

    private void setK(int w, int h) {
        widthLayout = w;
        heightLayout = h;

        textScale = 0.1f*w;

        KD1L = (int) (w/2 - h/2f);
        KD1R = (int) (w/2 + h/2f);
        KD1T = h/6;
        KD1B = 5*h/6;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (listener != null) {
                    listener.OnMuteSlider(isMute);
                }
            }
            invalidate();
            break;
        }
        return true;
    }
}
