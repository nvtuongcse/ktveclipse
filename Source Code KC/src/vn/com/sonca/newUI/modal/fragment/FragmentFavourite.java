package vn.com.sonca.newUI.modal.fragment;

import java.util.ArrayList;
import vn.com.sonca.Lyric.LoadLyricNew;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.Listener.TouchIAdapter;
import vn.com.sonca.Touch.Listener.TouchIBaseFragment;
import vn.com.sonca.Touch.touchcontrol.OnMainListener;
import vn.com.sonca.Touch.touchcontrol.TouchFragmentBase;
import vn.com.sonca.Touch.touchcontrol.TouchItemBack;
import vn.com.sonca.Touch.touchcontrol.TouchMainActivity;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.modal.view.tabview.OnBntFilterListener;
import vn.com.sonca.newUI.modal.view.tabview.TabFilter;
import vn.com.sonca.params.ServerStatus;
import vn.com.sonca.params.Song;
import vn.com.sonca.zktv.main.KTVMainActivity;
import vn.com.sonca.zzzzz.MyApplication;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.utils.AppSettings;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.moonbelly.youtubeFrag.MyYouTubeInfo;

public class FragmentFavourite extends FragmentBase implements OnMainListener ,OnBntFilterListener{

    private String TAB = "FragmentFavourite";
    private vn.com.sonca.newUI.main.KTVMainActivity mainActivity;
    private KTVMainActivity ktvMainActivity;
    private TouchIBaseFragment listener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (TouchIBaseFragment) activity;
            if(MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
                ktvMainActivity = (KTVMainActivity) activity;
            } else {
                mainActivity = (vn.com.sonca.newUI.main.KTVMainActivity) activity;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private FragmentActivity getMyActivity(){
        if(MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
            return this.ktvMainActivity;
        } else {
            return this.mainActivity;
        }
    }

    private Context context;
    private ListView listView;
    private LinearLayout layoutShowThongBao;
    private ArrayList<Song> listSongs = null;

    private TextView tvThongBao1;
    private TextView tvThongBao2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.touch_fragment_favourite, container, false);
        layoutShowThongBao = (LinearLayout)view.findViewById(R.id.layoutShowThongBao);
        listView = (ListView)view.findViewById(R.id.listview);
        context = getActivity().getApplicationContext();

        tvThongBao1 = (TextView)view.findViewById(R.id.tvThongBao1);
        tvThongBao2 = (TextView)view.findViewById(R.id.tvThongBao2);

        if(listener != null){
            listener.OnNameSearch("", "");
        }

        listSongs = new ArrayList<Song>();
        AppSettings setting = AppSettings.getInstance(context);
//		if(setting.loadServerLastUpdate() != 0) {
//		if(setting.isUpdated()) {
        loadSong = new LoadSongFromDatabase(0, 0);
        loadSong.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//		}

        changeColorScreen();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

///////////////////////////// - DATABASE - //////////////////////////////

    private LoadSongFromDatabase loadSong;
    private AdapterFavourite adapterSong = null;

    @Override
    public void OnBntFilterClickListener(TabFilter tabFilter) {
        switch (tabFilter.getTitle()){
            case "up":
//                listView.smoothScrollByOffset(5);
                listView.smoothScrollBy(-listView.getHeight(),1000);
                break;
            case "down":
                listView.smoothScrollBy(listView.getHeight(),1000);

                break;
        }
    }

    private class LoadSongFromDatabase extends AsyncTask<Void, Void, Integer> {

        private int offset;
        private int sum;

        public LoadSongFromDatabase(int offset, int sum) {
            this.offset = offset;
            this.sum = sum;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                getPlayListKTV();
                getFavourite();
                listSongs = DBInterface.DBGetFavouriteSongList(offset, sum, context);
                UpdateFavouriteIntoView(listSongs);
                UpdatePlayListIntoView(listSongs);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                if(listSongs != null){
                    if(listSongs.size() > 0){
                        layoutShowThongBao.setVisibility(View.INVISIBLE);
                    }else{
                        layoutShowThongBao.setVisibility(View.VISIBLE);
                    }
                }else{
                    layoutShowThongBao.setVisibility(View.VISIBLE);
                }
                adapterSong = new AdapterFavourite(context, R.layout.ktv_item,listSongs,mainActivity);
                adapterSong.setOnAdapterListener(new TouchIAdapter() {
                    @Override public void OnItemActive(Song song, String id , float x , float y) {
                        if (listener != null) {
                            listener.onClickItem(song, id , TouchMainActivity.FAVOURITE , "" , -1, x , y);
                        }
                    }
                    @Override public void OnItemFavourite(int position, Song song) {
                        try {
                            listSongs.remove(position);
                            for (Song songs : listSongs) {
                                if(!songs.isFavourite()){
                                    songs.setFavourite(true);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        loadSong = new LoadSongFromDatabase(0,0);
                        loadSong.execute();
                        if (listener != null) {
                            listener.OnClickFavourite();
                        }
                        if(listSongs != null){
                            if(listSongs.size() > 0){
                                layoutShowThongBao.setVisibility(View.INVISIBLE);
                            }else{
                                layoutShowThongBao.setVisibility(View.VISIBLE);
                            }
                        }else{
                            layoutShowThongBao.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override public void OnFirstClick(Song song, int position, float x, float y) {
                        if(listener != null){
                            listener.onFirstClick(song , TouchMainActivity.FAVOURITE , position, x , y);
                        }
                    }
                    @Override
                    public void onPlaySong(Song song, int position, float x, float y) {
                        if(listener != null){
                            listener.onPlaySong(song , TouchMainActivity.FAVOURITE, 0, x, y);
                        }
                    }
                    @Override
                    public void onDeleteSong(Song song , int position) {

                    }

                    @Override
                    public void OnSingerLink(boolean bool, String name, int[] idSinger) {
                        if(listener != null && !name.equals("-")){
                            ((MyApplication)context).addListBack(
                                    new TouchItemBack(TouchMainActivity.FAVOURITE, "", -1));
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
                    public void OnShowLyric(int position, Song song) {
                        final LoadLyric loadLyric = new LoadLyric(context, (vn.com.sonca.newUI.main.KTVMainActivity) getActivity());

                        loadLyric.setData(song , MyApplication.NameFileLyric);
                        loadLyric.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

                listView.setAdapter(adapterSong);
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        mainActivity.setTotal(listView.getFirstVisiblePosition()+1,listSongs.size());
                    }
                });
                mainActivity.setTotal(listView.getFirstVisiblePosition()+1,listSongs.size());
                if(listener != null){
                    listener.OnClickFavourite();
                }
            }
        }
    }

/////////////////////////// - LISTENER - //////////////////////////////


    @Override
    protected void UpdateAdapter() {

    }

    @Override
    public void OnSearchMain(int state1, int state2, String search) {
        // TODO Auto-generated method stub
    }

    @Override
    public void OnSK90009() {
        if (isAdded() && getMyActivity() != null) {
            if (adapterSong != null && listSongs != null) {
                ArrayList<Song> playlist = ((MyApplication)mainActivity.getApplication()).getListActive();
                for (int i = 0; i < listSongs.size(); i++) {
                    boolean value = playlist.contains(listSongs.get(i).getId());
                    listSongs.get(i).setActive(value);
                }
                adapterSong.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void OnLoadSucessful() {
        // TODO Auto-generated method stub
    }

    @Override
    public void OnUpdateImage() {
        // TODO Auto-generated method stub
    }

    @Override
    public void OnUpdateCommad(ServerStatus status) {
        // TODO Auto-generated method stub
    }

    @Override
    public void OnUpdateView() {
        if(adapterSong != null){
            adapterSong.notifyDataSetChanged();
        }
        changeColorScreen();
    }

    private void changeColorScreen(){
        if (MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT) {
            tvThongBao1.setTextColor(Color.parseColor("#21BAA9"));
            tvThongBao2.setTextColor(Color.parseColor("#21BAA9"));
        }else if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
            tvThongBao1.setTextColor(getResources().getColor(R.color.thong_bao_1_a));
            tvThongBao2.setTextColor(getResources().getColor(R.color.thong_bao_1_a));
        }
        layoutShowThongBao.invalidate();
    }

    @Override
    public void OnClosePopupYouTube(int position) {
        // TODO Auto-generated method stub
    }
}
