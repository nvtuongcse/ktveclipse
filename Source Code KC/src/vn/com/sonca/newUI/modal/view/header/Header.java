package vn.com.sonca.newUI.modal.view.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnDeviceClickLitener;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnHeaderClickListener;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnHomeClickListener;

/**
 * Created by tuong on 15/01/2018.
 */

public class Header extends ViewGroup implements OnDeviceClickLitener,OnHomeClickListener{

    private OnHeaderClickListener listener;
    public Header(Context context) {
        super(context);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Header(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bot) {
        int w =right-left;
        int h = bot-top;
        Home home = (Home) getChildAt(0);
        Device device = (Device) getChildAt(1);
        home.layout(0,0,278*w/1120,h);
        device.layout(w-278*w/1120,0,w,h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        int h = 96*w/1120;
        setMeasuredDimension(w,h);
    }

    @Override
    public void OnDeviceClickLitener() {
        if(listener!=null)
        listener.OnDeviceClickedListener();
    }

    @Override
    public void OnHomeClickListener() {
        if(listener!=null)
        listener.OnHomeClickedListener();
    }
}
