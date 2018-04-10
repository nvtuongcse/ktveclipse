package vn.com.sonca.newUI.modal.view.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnDeviceClickLitener;

/**
 * Created by tuong on 15/01/2018.
 */

public class Device extends View implements View.OnClickListener{

    private Drawable bg;
    private Drawable device;
    private Rect rectbg = new Rect();
    private Rect rectdevice = new Rect();
    private OnDeviceClickLitener listener;


    public Device(Context context) {
        super(context);
        init();
    }

    private void init() {
        bg = getResources().getDrawable(R.drawable.bg_device);
    }

    public Device(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Device(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDevice(Drawable device) {
        this.device = device;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        int h = 96*w/278;
        setMeasuredDimension(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int w = right-left;
        int h = bottom-top;
        rectbg.set(0,0,w,h);
        rectdevice.set(w/278*103,h/96*15,w-w/278*103,h-h/96*15);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bg.setBounds(rectbg);
        bg.draw(canvas);

        if(device!=null){
            device.setBounds(rectdevice);
            device.draw(canvas);
        }
    }

    @Override
    public void onClick(View view) {
        listener.OnDeviceClickLitener();
    }
}


