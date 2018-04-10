package vn.com.sonca.newUI.modal.fragment;
import java.util.ArrayList;

import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.CustomView.TouchGlowView;
import vn.com.sonca.Touch.CustomView.TouchMyGroupFavourite;
import vn.com.sonca.Touch.CustomView.TouchMyGroupSong;
import vn.com.sonca.Touch.CustomView.TouchMyGroupFavourite.OnGroupFavouriteListener;
import vn.com.sonca.Touch.CustomView.TouchMyGroupSong.OnMyGroupSongListener;
import vn.com.sonca.Touch.Listener.TouchIAdapter;
import vn.com.sonca.Touch.touchcontrol.TouchMainActivity;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.modal.view.listview.ItemSong;
import vn.com.sonca.params.Song;
import vn.com.sonca.zktv.main.KTVMainActivity;
import vn.com.sonca.zzzzz.MyApplication;
import vn.com.hanhphuc.karremote.R;

public class AdapterFavourite extends ArrayAdapter<Song>{

    private final Drawable bg_in;
    private final Drawable bg_ac;
    private final Drawable expand_bg;
    private final Drawable expand_ac,expand_in,lyric_ac,lyric_in,choose_ac,choose_in;
    private Context context;
    private TouchIAdapter listener;
    private Typeface typeface;
    private ArrayList<Song> arrayList;
    //-----------//
    private Drawable drawSinger,drawUser;
    private Drawable drawActive,drawInActive,drawHover;
    private Drawable drawRemix, drawMidi, drawMV, drawMVVid;
    private Drawable drawFavourite, drawFavouriteNO,fav_ac,fav_in,unfav_ac,unfav_in,first_ac,first_in;
    private Drawable ktv;
    private Drawable favoruite;
    private Drawable remix;
    private Drawable vocal;

    public void setOnAdapterListener(TouchIAdapter listener){
        this.listener = listener;
    }

    public AdapterFavourite(Context context, int resource, ArrayList<Song> arrayList , vn.com.sonca.newUI.main.KTVMainActivity mainActivity) {
        super(context, resource, arrayList);
        drawFavourite = context.getResources().getDrawable(R.drawable.favourite_active);
        drawFavouriteNO = context.getResources().getDrawable(R.drawable.favourite_inactive);
        drawSinger = context.getResources().getDrawable(R.drawable.vocal);
        drawRemix = context.getResources().getDrawable(R.drawable.remix);
        drawMidi = context.getResources().getDrawable(R.drawable.midi);
        drawMV = context.getResources().getDrawable(R.drawable.ktv_midi);
        drawMVVid = context.getResources().getDrawable(R.drawable.ktv_video);
        bg_in = context.getResources().getDrawable(R.drawable.bg_song);
        bg_ac = context.getResources().getDrawable(R.drawable.bg_song_active);
        expand_bg = context.getResources().getDrawable(R.drawable.bg_btn_circle);
        expand_ac = context.getResources().getDrawable(R.drawable.icon_up);
        expand_in= context.getResources().getDrawable(R.drawable.expand);
        lyric_ac = context.getResources().getDrawable(R.drawable.lyric_active);
        lyric_in = context.getResources().getDrawable(R.drawable.lyric_hover);
        choose_ac = context.getResources().getDrawable(R.drawable.check_active);
        choose_in = context.getResources().getDrawable(R.drawable.check_hover);
        fav_ac = context.getResources().getDrawable(R.drawable.icon_favourite_active);
        fav_in = context.getResources().getDrawable(R.drawable.icon_favourite_hover);
        unfav_ac= context.getResources().getDrawable(R.drawable.icon_unfavourite_active);
        unfav_in = context.getResources().getDrawable(R.drawable.icon_unfavourite_hover);
        first_ac= context.getResources().getDrawable(R.drawable.first_active);
        first_in = context.getResources().getDrawable(R.drawable.first_hover);

        typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ItemSong myGroupSong = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ktv_item, null);
            myGroupSong = (ItemSong)convertView.findViewById(R.id.item_song);
            myGroupSong.setDrawable(bg_ac,bg_in,unfav_ac,unfav_in,expand_ac,expand_bg,expand_in,first_ac,first_in,lyric_ac,choose_ac,choose_in,lyric_in,fav_ac,fav_in);
            convertView.setTag(R.id.item_song , myGroupSong);
        }else{
            myGroupSong = (ItemSong) convertView.getTag(R.id.myGroupSong);
        }
        if(myGroupSong != null){
            final Song song = arrayList.get(position);
            int ordinarily = ((MyApplication)context.getApplicationContext()).CheckSongInPlayList(song);
            boolean bool = false;
            if (ordinarily != -1) {
                bool = true;
            }
            //----------------//
            myGroupSong.setTypeface(typeface);
            myGroupSong.setOrdinarilyPlaylist(ordinarily + 1);
            myGroupSong.setIdSinger(song.getSingerId());
            myGroupSong.setNameSinger(song.getSinger().getName());
            //----------------//
            myGroupSong.setIdMusician(song.getMusicianId());
            myGroupSong.setNameMusician(song.getMusician().getName());
            //----------------//
            myGroupSong.setIdSong(String.valueOf(song.getId()));
            myGroupSong.setContentView(position, song);
            if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
                myGroupSong.setSongName(song.getName(), bool, song.getSpannable());
                mappingdrawable(song);
                myGroupSong.setAtt(ktv,vocal,remix,drawFavourite,drawFavouriteNO);
                MyLog.e("AdapterFavourite",song.getName());
            }else if(MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT){
                myGroupSong.setSongName(song.getName(), bool, song.getSpannable());

            }
            myGroupSong.setOnMyGroupSongListener(new OnMyGroupSongListener() {
                @Override public void OnFavourity(boolean bool, Song song) {
                    if(bool == false){
                        if(listener != null){
                            listener.OnItemFavourite(position, song);
                        }
                    }
                }
                @Override
                public void OnActive(boolean bool , Song song , String ipSong , float x , float y) {
                    // song.setActive(bool);
                    // ((MyApplication)mainActivity.getApplication()).addSongIntoPlayList(song);
                    if(listener != null){
                        listener.OnItemActive(song, ipSong , x , y);
                    }
                }
                @Override
                public void OnFristRes(boolean bool, Song song, int position, float x , float y) {
                    if(listener != null){
                        listener.OnFirstClick(song , position, x , y);
                    }
                }
                @Override
                public void OnSingerLink(boolean bool, String name, int[] idSinger) {
                    if(listener != null){
                        listener.OnSingerLink(bool, name, idSinger);
                    }
                }
                @Override
                public void OnLockNotify() {
                    // TODO Auto-generated method stub

                }
                @Override
                public void OnUnLockNotify() {
                    // TODO Auto-generated method stub

                }
                @Override
                public void OnShowLyric(int position, String idSong) {
                    if(listener != null){
                        listener.OnShowLyric(position, song);
                    }
                }
                @Override
                public void onPlayYouTube(Song song) {
                    // TODO Auto-generated method stub

                }
                @Override
                public void onDownYouTube(Song song) {
                    // TODO Auto-generated method stub

                }
            });
        }
        return convertView;
    }
    private void mappingdrawable(Song song) {
        switch (song.getMediaType()) {
            case MIDI:
                ktv = drawMidi;
                break;
            case VIDEO:
                ktv = drawMVVid;
                break;
            case MP3:
                ktv = drawMidi;
                break;
            case SINGER:
                ktv = drawMV;
                break;
            case ALL:
                ktv = drawMV;
                break;
        }
        if(song.isFavourite()) favoruite = drawFavourite; else favoruite = drawFavouriteNO;
        if(song.isRemix()) remix = drawRemix;
        if(song.isVocalSinger()) vocal = drawSinger;
    }

}
