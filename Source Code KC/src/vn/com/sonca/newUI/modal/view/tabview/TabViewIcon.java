package vn.com.sonca.newUI.modal.view.tabview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.newUI.main.KTVMainActivity;


/**
 * Created by tuong on 16/01/2018.
 */

public class TabViewIcon extends View {


    public static final String SONG = "Chọn bài";
    public static final String SINGER = "Ca sĩ";
    public static final String MUSICIAN = "Tác giả";
    public static final String SONGTYPE = "Thể loại";
    public static final String LANGUAGE = "Ngôn ngữ";
    public static final String FAVOURITE = "Yêu thích";
    public static final String PLAYLIST = "Playlist";
    public static final String SONGFOLLOW = "SONGFOLLOW";
    public static final String NOTHING = "NOTHING";
    public static final String REMIX = "Remix";
    public static final String HOTSONG = "Nhạc hot";
    public static final String NEWVOL = "NEWVOL";
    public static final String YOUTUBE = "Youtube";

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Drawable boder_ac, boder_in,icon_ac,icon_in;
    private Rect rectbg = new Rect();
    private String v_title = "";
    private String title = "";
    private boolean active = false;

    private OnBaseTypeViewListener listener;

    public interface OnBaseTypeViewListener {
        public void onBaseTypeView(boolean isActive, String type , TabViewIcon view);
    }
    public TabViewIcon(Context context) {
        super(context);
        init();
    }

    public void setOnTabIconClickListener(OnBaseTypeViewListener listener) {
        this.listener = listener;
    }


    public String getTitle() {
        return v_title;
    }
    public void setIcon_ac(Drawable icon_ac, Drawable icon_in, String title) {
        this.icon_ac = icon_ac;
        this.icon_in = icon_in;
        this.title = title;
        switch (title){
            case KTVMainActivity.SONG: v_title = SONG;
            break;
            case KTVMainActivity.SINGER: v_title = SINGER;
                break;
            case KTVMainActivity.MUSICIAN: v_title = MUSICIAN;
                break;
            case KTVMainActivity.HOTSONG: v_title = HOTSONG;
                break;
            case KTVMainActivity.REMIX: v_title = REMIX;
                break;
            case KTVMainActivity.YOUTUBE: v_title = YOUTUBE;
                break;
            case KTVMainActivity.PLAYLIST: v_title = PLAYLIST;
                break;
            case KTVMainActivity.SONGTYPE: v_title = SONGTYPE;
                break;
            case KTVMainActivity.FAVOURITE: v_title = FAVOURITE;
                break;


                      }
        invalidate();
    }

    private void init() {
        boder_ac = getResources().getDrawable(R.drawable.boder_icon_song_act);
        boder_in = getResources().getDrawable(R.drawable.boder_icon_song_inact);
        icon_ac = getResources().getDrawable(R.drawable.icon_tab_song_act);
    }

    public TabViewIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabViewIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = heightMeasureSpec;
        int w = 184*h/213;
        setMeasuredDimension(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = right-left;
        rectbg.set(8*w/184,0,176*w/184,148*w/184);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(active) {
            boder_ac.setBounds(rectbg);
            boder_ac.draw(canvas);
            if(icon_ac!=null){
                icon_ac.setBounds(rectbg);
                icon_ac.draw(canvas);
            }
        }
        else {
            boder_in.setBounds(rectbg);
            boder_in.draw(canvas);
            if(icon_in!=null){
                icon_in.setBounds(rectbg);
                icon_in.draw(canvas);
            }
        }

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        if(!active)
        textPaint.setARGB(255,255  ,255,255);
        else textPaint.setARGB(255,255,255,0);
        textPaint.setTextSize(getWidth()/6);
        canvas.drawText(v_title,rectbg.centerX()-textPaint.measureText(v_title)/2,getWidth()*198/184,textPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                active =!active;
                listener.onBaseTypeView(active,title,this);
                invalidate();
                break;
        }
        return true;
    }


    public int isActive() {
        if(active) return 0;
        else return 1;
    }

    public void setActive() {
        active = true;
        invalidate();
    }

    public void setInActive() {
        active = false;
        invalidate();
    }
}
