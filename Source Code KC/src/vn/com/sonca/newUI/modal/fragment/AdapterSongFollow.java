package vn.com.sonca.newUI.modal.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.Touch.CustomView.TouchGlowView;
import vn.com.sonca.Touch.CustomView.TouchMyGroupSong;
import vn.com.sonca.Touch.CustomView.TouchMyGroupSong.OnMyGroupSongListener;
import vn.com.sonca.Touch.Favourite.FavouriteStore;
import vn.com.sonca.Touch.Listener.TouchIAdapter;
import vn.com.sonca.Touch.touchcontrol.TouchMainActivity;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.main.KTVMainActivity;
import vn.com.sonca.newUI.modal.view.listview.ItemSong;
import vn.com.sonca.params.Song;
import vn.com.sonca.zzzzz.MyApplication;

public class AdapterSongFollow extends ArrayAdapter<Song> {

	private final Drawable expand_bg;
	private final Drawable unfav_ac;
	private final Drawable unfav_in;
	private String TAB = "AdapterSongFollow";
	private KTVMainActivity mainActivity;
	private ArrayList<Song> arrayList;
	private Typeface typeface;
	private TouchIAdapter listener;
	private Context context;
	private String search;
		//--------//
	private Drawable drawUser;
	private Drawable drawSinger;
	private Drawable drawActive,drawInActive,drawHover;
	private Drawable drawRemix, drawMidi, drawMV, drawMVVid;
	private Drawable drawFavourite, drawFavouriteNO;
	private Drawable ktv=null,vocal=null,remix=null,favoruite=null;
	private Drawable bg_ac,bg_in,expand_ac,expand_in,first_ac,first_in,lyric_ac,choose_ac,choose_in,lyric_in,fav_ac,fav_in;

	public void setOnAdapterListener(TouchIAdapter listener){
		this.listener = listener;
	}

	public AdapterSongFollow(Context context, int resource, ArrayList<Song> arrayList,
                                  String search, KTVMainActivity mainActivity) {
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
		this.mainActivity = mainActivity;
		this.context = context;
		this.search = search;
		this.arrayList = arrayList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ItemSong itemSong;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ktv_item, null);
			itemSong = (ItemSong) convertView.findViewById(R.id.item_song);
			itemSong.setDrawable(bg_ac,bg_in,unfav_ac,unfav_in,expand_ac,expand_bg,expand_in,first_ac,first_in,lyric_ac,choose_ac,choose_in,lyric_in,fav_ac,fav_in);
			convertView.setTag(R.id.item_song , itemSong);
		}else{
			itemSong = (ItemSong) convertView.getTag(R.id.item_song);
			if(itemSong.isActive()) itemSong.setActive(false);
		}
		if(itemSong != null){
			if(position >= arrayList.size()){
				return convertView;
			}
			final Song song = arrayList.get(position);
			int ordinarily = ((MyApplication)context.getApplicationContext()).CheckSongInPlayList(song);
			boolean bool = false;
			if (ordinarily != -1) {
				bool = true;
			}
			itemSong.setActive(bool);
			//----------------//
			itemSong.setTypeface(typeface);
			itemSong.setOrdinarilyPlaylist(ordinarily + 1);
			itemSong.setIdSinger(song.getSingerId());
			itemSong.setNameSinger(song.getSinger().getName());
			//----------------//
			itemSong.setIdMusician(song.getMusicianId());
			itemSong.setNameMusician(song.getMusician().getName());
			//----------------//
			itemSong.setIdSong(String.valueOf(song.getId()));
			if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
				itemSong.setSongName(song.getName(), bool, song.getSpannable());
				mappingdrawable(song);
				itemSong.setAtt(ktv,vocal,remix,drawFavourite,drawFavouriteNO);
			}else if(MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT){
				itemSong.setSongName(song.getName(), bool, song.getSpannable());
			}
			itemSong.setContentView(position, song);
			itemSong.setOnMyGroupSongListener(new OnMyGroupSongListener() {
				@Override public void OnFavourity(boolean bool, Song song) {
//					MyLog.e(TAB, "OnFavourity - " + song.getId());
					arrayList.get(position).setFavourite(bool);

					FavouriteStore favStore = FavouriteStore.getInstance(mainActivity.getApplicationContext());
					favStore.setFavSongIntoStore(bool, String.valueOf(song.getId()), song.getTypeABC());

					DBInterface.DBSetFavouriteSong(String.valueOf(song.getId()), String.valueOf(song.getTypeABC()), bool, context);
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
