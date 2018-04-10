package vn.com.sonca.newUI.modal.view.tabview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.newUI.modal.fragment.FragmentBase;


public class TabFilter extends View {

    private Drawable bg,bg_hover,icon;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String title = "";
    private float textS, textX,textY;
    private Rect rectbg = new Rect();
    private OnBntFilterListener listener;
    public void SetOnBntListener(OnBntFilterListener listener){
        this.listener = listener;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
        invalidate();
    }
    private boolean selected = false;
    public void setNoBG(){
        bg = null;
        bg_hover=null;
        invalidate();
    }
    boolean disable = false;

    public void setDisable(boolean disable) {
        this.disable = disable;
        this.active= !disable;
        invalidate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }
    public void setTitle(int index,int sum){
        if(sum!=-1) {
            int mod5 = sum % 5 == 0 ? sum / 5 : sum / 5 + 1;
            this.title = (index/5+1)+"/"+mod5;
        }
        else {
            String[] temp = this.title.split("/");
            this.title = (index/5+1)+"/"+temp[1];
        }

        invalidate();
    }

    private Rect recticon = new Rect();
    private boolean active = false;

    public TabFilter(Context context) {
        super(context);
        init();
    }

    private void init() {
        bg = getResources().getDrawable(R.drawable.bnt);
        bg_hover =getResources().getDrawable(R.drawable.bnt_hover);

    }

    public TabFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TabFilter);
        title = typedArray.getString(R.styleable.TabFilter_tab_title);
        init();
    }

    public TabFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setActive(boolean active) {
        this.active = active;
        this.selected = active;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                active = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float x  = event.getX();
                float y = event.getY();
                if(x<getLeft()||x>getRight()||y<getBottom()||y>getTop())
                    active = false;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                active = false;
                x = event.getX();
                y = event.getY();
                if(x<getLeft()||x>getRight()||y<getBottom()||y>getTop()){
                    if(listener!=null)  listener.OnBntFilterClickListener(this);
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w =  widthMeasureSpec;
        int h =  66*w/140;
        if(heightMeasureSpec>=h) heightMeasureSpec = h;
        setMeasuredDimension(w,heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textX=  (getWidth()/2-textPaint.measureText(title)/2);



        if(!active&!selected|disable){
            if(bg!=null) {
                bg.setBounds(rectbg);
                bg.draw(canvas);
            }
        }
        else {
            if(bg_hover!=null) {
                bg_hover.setBounds(rectbg);
                bg_hover.draw(canvas);
            }
        }
        if(icon!=null) {
            icon.setBounds(recticon);
            icon.draw(canvas);
        }
        else{
            if(disable){
                textPaint.setColor(Color.GRAY);
                canvas.drawText(title, textX, textY, textPaint);
            }else {
                if (!active & !selected) {
                    textPaint.setARGB(255, 255, 255, 255);

                    canvas.drawText(title, textX, textY, textPaint);
                } else {
                    textPaint.setARGB(255, 255, 200, 20);
                    canvas.drawText(title, textX, textY, textPaint);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = right-left;
        textS = 22*w/140;
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setARGB(255,255,255,255);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(textS);

        textY = 66*w/280 + 0.35f*textS;
        rectbg.set(0,0,w,66*w/140);
        recticon.set(48*w/140,12*w/140,92*w/140,48*w/140);

    }



}
