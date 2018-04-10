package vn.com.sonca.newUI.modal.view.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnHomeClickListener;


/**
 * Created by tuong on 15/01/2018.
 */

public class Home extends View implements View.OnClickListener{

    private OnHomeClickListener listenter;
    private Drawable bg;
    private Rect rectbg = new Rect();

    public Home(Context context) {
        super(context);
        init();
    }

    private void init() {
        bg = getResources().getDrawable(R.drawable.logo);
    }

    public Home(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Home(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        int h = w/278*96;
        setMeasuredDimension(w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        rectbg.set(0,0,getRight(),getBottom());
        bg.setBounds(rectbg);
        bg.draw(canvas);
    }

    @Override
    public void onClick(View view) {
        listenter.OnHomeClickListener();
    }
}
