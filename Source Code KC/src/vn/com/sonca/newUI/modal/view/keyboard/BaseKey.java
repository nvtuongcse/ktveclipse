package vn.com.sonca.newUI.modal.view.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TimingLogger;
import android.view.View.OnClickListener;
import android.view.View;

import java.sql.Time;

import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.Keyboard.GroupKeyBoard;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnClickBaseKeyListener;
import vn.com.sonca.zzzzz.MyApplication;

public class BaseKey extends View implements OnClickListener {

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintMain = new Paint(Paint.ANTI_ALIAS_FLAG);
    private OnClickBaseKeyListener listener;
    private boolean enable = true;

    public BaseKey(Context context) {
        super(context);
        initView(context);
    }

    public BaseKey(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public BaseKey(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context){
        setOnClickListener(this);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }



    public void setOnClickBaseKeyListener(OnClickBaseKeyListener listener){
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int magin;
    private int widthLayout, heightLayout;
    private RectF restKey = new RectF();
    private Rect rectImage = new Rect();
    private Rect zlightRectImage = new Rect();
    private int textS, textY;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthLayout = w;
        heightLayout = h;
        magin = 10;
        restKey.set(magin, magin, widthLayout - magin, heightLayout - magin);
        rectImage.set(0, 0, w, h);

        int d = (int) (0.05*w);
        zlightRectImage.set(d, d, w - d, h - d);

        textS = (int) (0.45*h);
        textY = (int) (0.5*h + 0.35*textS);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
            if (pressed) {
                if(drawAC != null){
                    drawAC.setBounds(rectImage);
                    drawAC.draw(canvas);
                }
            } else {
                if(drawIN != null){
                    drawIN.setBounds(rectImage);
                    drawIN.draw(canvas);
                }
            }
            if (enable) {
                if(drawAC != null){
                    drawAC.setBounds(rectImage);
                    drawAC.draw(canvas);
                }
            } else {
                if(drawIN != null){
                    drawIN.setBounds(rectImage);
                    drawIN.draw(canvas);
                }
            }
            if(namekey.equals(GroupKeyBoard.CLEAR) || namekey.equals(GroupKeyBoard.SPACE)){
                return;
            }
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(textS);
            if (pressed&enable) {
                textPaint.setColor(Color.YELLOW);
                textPaint.setShadowLayer(10, 0, 0, Color.WHITE);
            } else {
                if(enable) textPaint.setColor(Color.WHITE); else textPaint.setColor(Color.GRAY);
                textPaint.setShadowLayer(0, 0, 0, Color.WHITE);
            }

            float textX = (float) (0.5 * widthLayout - 0.5 * textPaint.measureText(namekey));
            canvas.drawText(namekey, textX, textY, textPaint);


        }else if(MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT){
            if (pressed) {
                if(drawAC != null){
                    drawAC.setBounds(zlightRectImage);
                    drawAC.draw(canvas);
                }
            } else {
                if(drawIN != null){
                    drawIN.setBounds(zlightRectImage);
                    drawIN.draw(canvas);
                }
            }
            if(namekey.equals(GroupKeyBoard.CLEAR) || namekey.equals(GroupKeyBoard.SPACE)){
                return;
            }
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(textS);
            if (pressed&enable) {
                textPaint.setColor(Color.YELLOW);
            } else {
                if(enable) textPaint.setColor(Color.WHITE); else textPaint.setColor(Color.GRAY);
            }
            if(namekey.equals("123") && layout == View.GONE){
                textPaint.setColor(Color.GREEN);
            }
            float textX = (float) (0.5 * widthLayout - 0.5 * textPaint.measureText(namekey));
            canvas.drawText(namekey, textX, textY, textPaint);
        }


    }

    private boolean pressed = false;
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        this.pressed = pressed;
        invalidate();
    }

    private String namekey = "";
    private Drawable drawAC = null;
    private Drawable drawIN = null;
    public void setImage(Drawable drawAC, Drawable drawIN, String namekey){
        this.drawAC = drawAC;
        this.drawIN = drawIN;
        this.namekey = namekey;
        invalidate();
    }

    @Override
    public void onClick(View v) {
        MyLog.i("[Time Start]",System.currentTimeMillis() + " ");
        if(listener != null && enable){
            listener.OnNameKey(namekey, v);
        }
    }
    public void setEnable(boolean enable, Drawable drawAC, Drawable drawIN,String character){
        this.enable = enable;
        setImage(drawAC,drawIN,character);
        if(enable){
            textPaint.setColor(Color.WHITE);
        }
        else{
            textPaint.setColor(Color.GRAY);
        }
        invalidate();
    }
    private int layout;
    public void setLayoutView(int layout){
        this.layout = layout;
        invalidate();
    }

}
