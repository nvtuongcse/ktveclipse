package vn.com.sonca.newUI.modal.view.charseach;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import vn.com.sonca.MyLog.MyLog;

/**
 * Created by tuong on 16/01/2018.
 */

public class Character extends View {

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private float textS,textX, textY;
    private String character = "A";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int w;
    private boolean select = false;
    private OnCharacterSearchListener listener;

    public void setOnCharClickedListener(OnCharacterSearchListener listener) {
        this.listener = listener;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
        invalidate();
    }

    public Character(Context context) {
        super(context);
    }



    public Character(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public Character(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        int h = 58*w/90;
        setMeasuredDimension(w,h);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = right-left;
        textS = w/3;
        textPaint.setTextSize(textS);
        paint.setStyle(Paint.Style.FILL);
        textPaint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,200,20);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setARGB(255,255,255,255);
        paint.setStrokeWidth(2);
        textX =  (w/2 - textPaint.measureText("A")/2);
        textY = 57*w/180 +0.35f*textS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!select) {
            textPaint.setARGB(255,255,255,255);
            canvas.drawText(character, textX, textY, textPaint);
            canvas.drawLine(w / 4, 56 * w / 90, w * 3 / 4, 56 * w / 90, paint);
        }
        else {
            textPaint.setARGB(255,255,255,0);
            canvas.drawText(character, textX, textY, textPaint);
            canvas.drawLine(w / 4, 56 * w / 90, w * 3 / 4, 56 * w / 90, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                MyLog.e("GroupChar", character + "--" );
                    if(listener!=null)
                    listener.OnCharacterCLicked(character);
                invalidate();
                break;
        }
        return true;
    }

    public void setSelect(boolean select) {
        this.select = select;
        invalidate();
    }
}
