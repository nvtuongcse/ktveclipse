package vn.com.sonca.newUI.modal.view.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.CustomView.TouchMyGroupSong;
import vn.com.sonca.Touch.Favourite.FavouriteStore;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.params.Song;
import vn.com.sonca.params.SongType;
import vn.com.sonca.utils.AppConfig;
import vn.com.sonca.zzzzz.MyApplication;

/**
 * Created by tuong on 15/01/2018.
 */

public class ItemSongType extends View {

    private Drawable bg_ac,bg_in,expand_ac,expand_in,first_ac,first_in,lyric_ac,choose_ac,choose_in,lyric_in,fav_ac,fav_in;
    private Rect rectbg=new Rect(),rectexpand_ac=new Rect(),rectexpand_in=new Rect(),rectfirst=new Rect(),rectlyric=new Rect(),rectchoose=new Rect(),rectfav=new Rect();
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String songId = "000000 | 000000",songName = "TenBaiHat",songSinger = "",songMusician="";
    private boolean active = false;
    private Drawable expand_bg;
    private int textS;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Drawable ktv=null,vocal=null,remix=null,favourite=null;
    private Rect rectktv = new Rect(),rectvocal = new Rect(),rectfavourite=new Rect(),rectremix = new Rect();
    private TouchMyGroupSong.OnMyGroupSongListener listener;
    private String textLyric = "";
    private int position;

    private boolean isRemix;
    private boolean isSinger;
    private AppConfig.MEDIA_TYPE ismedia;
    private boolean isUser;
    private String idSong;
    private String idSong5;
    private int intABC;
    private boolean isRunFavourity;
    private String textABC;
    private Song song;
    private boolean isClick;
    private String textSinger;
    private int ordinarily = -1;
    private Spannable wordtoSpan;
    private int[] idSinger;
    private int[] idMusician;
    private boolean isFirst = false;
    private Timer timerFavourity = null;
    private Context context;
    private boolean isFavourity;
    private Drawable unfav_ac;
    private Drawable unfav_in;
    private SongType data;

    public void setAtt(Drawable ktv,Drawable vocal,Drawable remix,Drawable favourite){
        if(ktv!=null)
            setKtv(ktv);
        if(favourite!=null)
            setFavourite(favourite);
        if(remix!=null)
            setRemix(remix);
        if(vocal!=null)
            setVocal(vocal);
        invalidate();
    }

    public void setKtv(Drawable ktv) {
        this.ktv = ktv;
        invalidate();
    }

    public void setVocal(Drawable vocal) {
        this.vocal = vocal;
        invalidate();
    }

    public void setRemix(Drawable remix) {
        this.remix = remix;
        invalidate();
    }

    public void setFavourite(Drawable favourite) {
        this.favourite = favourite;
        invalidate();
    }

    public ItemSongType(Context context) {
        super(context);
        init();
    }
    public boolean isActive(){
        return active;
    }

    private void init() {
        context = getContext();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        bg_in = context.getResources().getDrawable(R.drawable.bg_song);
//        bg_ac = context.getResources().getDrawable(R.drawable.bg_song_active);
//        expand_bg = context.getResources().getDrawable(R.drawable.bg_btn_circle);
//        expand_ac = context.getResources().getDrawable(R.drawable.icon_up);
//        expand_in= context.getResources().getDrawable(R.drawable.expand);
//        lyric_ac = context.getResources().getDrawable(R.drawable.lyric_active);
//        lyric_in = context.getResources().getDrawable(R.drawable.lyric_hover);
//        choose_ac = context.getResources().getDrawable(R.drawable.check_active);
//        choose_in = context.getResources().getDrawable(R.drawable.check_hover);
//        fav_ac = context.getResources().getDrawable(R.drawable.icon_favourite_active);
//        fav_in = context.getResources().getDrawable(R.drawable.icon_favourite_hover);
//        unfav_ac= context.getResources().getDrawable(R.drawable.icon_unfavourite_active);
//        unfav_in = context.getResources().getDrawable(R.drawable.icon_unfavourite_hover);
//        first_ac= context.getResources().getDrawable(R.drawable.first_active);
//        first_in = context.getResources().getDrawable(R.drawable.first_hover);
    }
    public void setDrawable(Drawable bg_ac,Drawable bg_in,Drawable unfav_ac, Drawable unfav_in,Drawable expand_ac,Drawable expand_bg,Drawable expand_in,Drawable first_ac,Drawable first_in,Drawable lyric_ac,Drawable choose_ac,Drawable choose_in,Drawable lyric_in,Drawable fav_ac,Drawable fav_in){
        this.bg_ac = bg_ac;
        this.bg_in = bg_in;
        this.expand_ac = expand_ac;
        this.expand_bg = expand_bg;
        this.expand_in = expand_in;
        this.first_ac = first_ac;
        this.first_in = first_in;
        this.fav_ac = fav_ac;
        this.fav_in = fav_in;
        this.unfav_ac = unfav_ac;
        this.unfav_in = unfav_in;
        this.lyric_ac = lyric_ac;
        this.lyric_in = lyric_in;
        this.choose_ac = choose_ac;
        this.choose_in = choose_in;
        invalidate();
    }
    public ItemSongType(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemSongType(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        if(!active) {
            int h = 96*w/1120;
            setMeasuredDimension(w,h);
        }
        else {
            int h = 204*w/1120;
            setMeasuredDimension(w,h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if(!active){
            if(bg_in!=null) {
                bg_in.setBounds(rectbg);
                bg_in.draw(canvas);
            }if(expand_in!=null){
                expand_in.setBounds(rectexpand_in);
                expand_in.draw(canvas);
            }
        }
        else {
            bg_ac.setBounds(rectbg);
            bg_ac.draw(canvas);
            if(expand_bg!=null&expand_in!=null&expand_ac!=null) {
                expand_bg.setBounds(rectexpand_ac);
                expand_bg.draw(canvas);
                expand_ac.setBounds(rectexpand_ac);
                expand_ac.draw(canvas);
            }
            if(first_in!=null&first_ac!=null) {
                first_ac.setBounds(rectfirst);
                first_ac.draw(canvas);
            }
            textPaint.setARGB(239, 108, 0, 1);
            textPaint.setTextSize(textS/2);
            canvas.drawText("Ưu tiên",rectfirst.centerX()-textPaint.measureText("Ưu tiên")/2, (float) (rectfirst.bottom+0.35*textS/2),textPaint);
            canvas.drawText("Chọn bài",rectchoose.centerX()-textPaint.measureText("Chọn bài")/2, (float) (rectchoose.bottom+0.35*textS/2),textPaint);
            canvas.drawText("Xem lời",rectlyric.centerX()-textPaint.measureText("Xem lời")/2, (float) (rectlyric.bottom+0.35*textS/2),textPaint);
            canvas.drawText("Yêu thích",rectfav.centerX()-textPaint.measureText("Yêu thích")/2, (float) (rectfav.bottom+0.35*textS/2),textPaint);
            if(choose_in!=null&choose_ac!=null) {
                choose_ac.setBounds(rectchoose);
                choose_ac.draw(canvas);
            }
            if(lyric_in!=null&lyric_ac!=null) {
                lyric_ac.setBounds(rectlyric);
                lyric_ac.draw(canvas);
            }
            if(isFavourity) {
                if(fav_in!=null&fav_ac!=null){
                    fav_ac.setBounds(rectfav);
                    fav_ac.draw(canvas);
                }
            }else {
                if(unfav_in!=null&unfav_ac!=null){
                    unfav_ac.setBounds(rectfav);
                    unfav_ac.draw(canvas);
                }
            }

        }
        if(ktv!=null) {
            ktv.setBounds(rectktv);
            ktv.draw(canvas);
        }
        if(vocal!=null) {
            vocal.setBounds(rectvocal);
            vocal.draw(canvas);
        }
        if(remix!=null) {
            remix.setBounds(rectremix);
            remix.draw(canvas);
        }
        if(favourite!=null) {
            favourite.setBounds(rectfavourite);
            favourite.draw(canvas);
        }
        paint.setARGB(255,255,255,255);
        paint.setStrokeWidth(3);
        canvas.drawLine(818*getWidth()/1120,22*getWidth()/1120,818*getWidth()/1120,74*getWidth()/1120,paint);

        textPaint.setARGB(255,255,255,255);
        textPaint.setTextSize(textS/1.5f);
        canvas.drawText(songId,rectexpand_in.centerX()-textPaint.measureText(songId)/2, (float) (30*getWidth()/1120+0.35*textS/1.5f),textPaint);
        textPaint.setTextSize(textS);
//        textPaint.setARGB(255,255,255,255);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(songName,222*getWidth()/1120, (float) (45*getWidth()/1120+0.35*textS),textPaint);
//        textPaint.setARGB(255,255,255,255);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextSize(textS/1.2f);
        canvas.drawText(songSinger + songMusician,836*getWidth()/1120, (float) (27*getWidth()/1120+0.35*textS/1.2f),textPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = (right-left);
        int h = bottom-top;
        rectbg.set(0,0,w,h);
        rectexpand_in.set(74*w/1120,125*h/192,102*w/1120,179*h/192);
        rectexpand_ac.set(45*w/1120,90*h/192,133*w/1120,178*h/192);
        rectfirst.set(288*w/1120,94*w/1120,376*w/1120,182*w/1120);
        rectchoose.set(536*w/1120,94*w/1120,624*w/1120,182*w/1120);//288,94
        rectlyric.set(784*w/1120,94*w/1120,872*w/1120,182*w/1120);
        rectfav.set(1032*w/1120,94*w/1120,w,182*w/1120);

        rectktv.set(830*w/1120,54*w/1120,906*w/1120,86*w/1120);
        rectvocal.set(918*w/1120,50*w/1120,950*w/1120,82*w/1120);
        rectremix.set(962*w/1120,54*w/1120,1038*w/1120,86*w/1120);
        rectfavourite.set(1050*w/1120,50*w/1120,1084*w/1120,82*w/1120);
        textS = 32*w/1120;


    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_UP:
//                if(x>rectexpand_ac.left&&x<rectexpand_ac.right&&y<rectexpand_ac.bottom&&y>rectexpand_ac.top){
//                    active=!active;
//                    requestLayout();
//                }
//                if(x>rectfirst.left&&x<rectfirst.right&&y<rectfirst.bottom&&y>rectfirst.top){
//                    int[] location = new int[2];
//                    this.getLocationOnScreen(location);
//                    int ScreenX = location[0] + getHeight() / 2;
//                    int ScreenY = location[1] + getHeight() / 2;
//                    listener.OnFristRes(isFirst, song, position, ScreenX, ScreenY);
//                }
//                if(x>rectlyric.left&&x<rectlyric.right&&y<rectlyric.bottom&&y>rectlyric.top){
//                    listener.OnShowLyric(position,idSong);
//                }
//                if(x>rectchoose.left&&x<rectchoose.right&&y<rectchoose.bottom&&y>rectchoose.top){
//                    int[] location = new int[2];
//                    this.getLocationOnScreen(location);
//                    int ScreenX = location[0] + getHeight() / 2;
//                    int ScreenY = location[1] + getHeight() / 2;
//                    listener.OnActive(isFirst,song,idSong,ScreenX,ScreenY);
//                }
//                if(x>rectfav.left&&x<rectfav.right&&y<rectfav.bottom&&y>rectfav.top){
//                    MyLog.e("ItemSong","____OnFavouriteClicked____");
//                    if(MyApplication.intSvrModel == MyApplication.SONCA_SMARTK && song.isYoutubeSong()){
//                        if (listener != null) {
//                            listener.onPlayYouTube(song);
//                        }
//                    } else {
//                        saveFavourity();
//                    }
//                    invalidate();
//                }
//                break;
//        }
//        return true;
//    }

    private void saveFavourity(){
        if(isRunFavourity){
            return;
        }
        if(timerFavourity != null){
            timerFavourity.cancel();
            timerFavourity = null;
        }
        isRunFavourity = true;
        timerFavourity = new Timer();
        timerFavourity.schedule(new TimerTask() {
            @Override public void run() {
                isFavourity = !isFavourity;
                song.setFavourite(isFavourity);
                FavouriteStore favStore = FavouriteStore.getInstance(context);
                favStore.setFavSongIntoStore(isFavourity,
                        String.valueOf(song.getId()), song.getTypeABC());
                DBInterface.DBSetFavouriteSong(String.valueOf(song.getId()),
                        String.valueOf(song.getTypeABC()), isFavourity, context);
                handlerFavourity.sendEmptyMessage(0);
            }
        }, 50);
    }

    @SuppressLint("HandlerLeak")
    private Handler handlerFavourity = new Handler(){
        public void handleMessage(android.os.Message msg) {
            invalidate();
            if (listener != null) {
                listener.OnFavourity(isFavourity, song);
            }
            isRunFavourity = false;
        }
    };


    public void setActive(boolean bool) {
        active = bool;
        requestLayout();
    }

    public void setTypeface(Typeface typeface) {

    }

    public void setSongName(String name, boolean bool, Spannable spannable) {
        this.songName = name;
        this.active  = bool;
        if (wordtoSpan == null) {
            this.wordtoSpan = new SpannableString(songName);
        } else {
            this.wordtoSpan = wordtoSpan;
        }

    }

    public void setContentView(int position, Song song) {
//        this.spanNemberID = song.getSpannableNumber();
        this.textLyric = song.getLyric();
        this.position = position;
        this.isFavourity = song.isFavourite();
        this.isRemix = song.isRemix();
        this.isSinger = song.isMediaSinger();
        this.ismedia = song.getMediaType();
        this.isUser = !song.isSoncaSong();
        int id = song.getId();
        if (id != 0) {
            this.idSong = String.valueOf(id);
        } else {
            this.idSong = "";
        }

        int id5 = song.getIndex5();
        if (id5 != 0) {
            this.idSong5 = String.valueOf(id5);
        } else {
            this.idSong5 = "";
        }

        intABC = song.getTypeABC();
        textABC = "";
		/*
		int intABC = song.getTypeABC();
		switch (intABC) {
		case 1:		textABC = "(A)";		break;
		case 2:		textABC = "(B)";		break;
		case 3:		textABC = "(C)";		break;
		default:	break;
		}
		*/
        isRunFavourity = false;
        this.song = song;
        isClick = false;

        //--------------------//

        if(MyApplication.intSvrModel == MyApplication.SONCA_SMARTK && song.isYoutubeSong()){
            this.textSinger = cutText(10,song.getSingerName());
        } else {
            if(ismedia == AppConfig.MEDIA_TYPE.MIDI){
                this.textSinger = song.getMusician().getNamecut();
            }else{
                this.textSinger = song.getSinger().getNamecut();
            }
        }
        invalidate();




    }

    private String cutText(int maxLength, String content) {
        if(content.length() <= maxLength){
            return content;
        }
        return content.substring(0, maxLength) + "...";
    }

    public void setOnMyGroupSongListener(TouchMyGroupSong.OnMyGroupSongListener onMyGroupSongListener) {
        this.listener = onMyGroupSongListener;
    }

    public void setOrdinarilyPlaylist(int i) {
        if (ordinarily <= 0) {
            this.ordinarily = -1;
        } else {
            this.ordinarily = ordinarily;
        }
    }

    public void setIdSinger(int[] singerId) {
        this.idSinger = singerId;
    }

    public void setNameSinger(String name) {
        this.songSinger = name;
    }

    public void setIdMusician(int[] musicianId) {
        this.idMusician = musicianId;
    }

    public void setNameMusician(String name) {
        this.songMusician = name;
    }

    public void setIdSong(String s) {
        this.songId = s;
    }

    public void setData(SongType data) {
        this.data = data;
        this.songName = data.getName();
        this.songSinger = String.valueOf(data.getCountTotal() + " Bài");
        invalidate();
    }
}
