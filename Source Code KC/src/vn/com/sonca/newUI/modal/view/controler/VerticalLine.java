package vn.com.sonca.newUI.modal.view.controler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import vn.com.hanhphuc.karremote.R;

/**
 * Created by tuong on 12/03/2018.
 */

public class VerticalLine extends View {


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = heightMeasureSpec;
        int w = 4*h/98;
        setMeasuredDimension(w,h);
    }

    public VerticalLine(Context context) {
        super(context);

    }


    public VerticalLine(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public VerticalLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
