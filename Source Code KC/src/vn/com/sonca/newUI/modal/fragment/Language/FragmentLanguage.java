package vn.com.sonca.newUI.modal.fragment.Language;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.CustomView.LanguageButtonModel;
import vn.com.sonca.Touch.CustomView.LanguageTopModelView;
import vn.com.sonca.Touch.CustomView.LanguageTopModelView.OnTopModelListener;
import vn.com.sonca.Touch.Listener.TouchIBaseFragment;
import vn.com.sonca.Touch.touchcontrol.OnMainListener;
import vn.com.sonca.Touch.touchcontrol.TouchFragmentBase;
import vn.com.sonca.database.DBInstance.SearchMode;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.modal.fragment.FragmentBase;
import vn.com.sonca.params.Language;
import vn.com.sonca.params.ServerStatus;
import vn.com.sonca.utils.AppSettings;
import vn.com.sonca.zzzzz.MyApplication;

public class FragmentLanguage extends FragmentBase implements OnMainListener {
	
	private List<Language> languageList;
	private TouchIBaseFragment listener;
	private LanguageAdapter adapter;
	private ListView listView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (TouchIBaseFragment) activity;
			listenerLanguage = (OnLanguageFragmentListener)activity;
		} catch (Exception ex) {}
	}

	private OnLanguageFragmentListener listenerLanguage;
	public interface OnLanguageFragmentListener {
		public void OnLanguageFragmentBackList();
		public void OnClickLanguage();
		public void OnCloseLanguage();
	}

	private Context context;
//	private View viewLine;
//	private View viewBackgroup;
//	private LanguageTopModelView topModelView;
//	private LanguageButtonModel buttonModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_songtype, container, false);
		context = getActivity().getApplicationContext();

		if(listener != null){
			listener.OnNameSearch("", "");
		}

		listView = (ListView) view.findViewById(R.id.lstview);
		AppSettings setting = AppSettings.getInstance(getActivity().getApplicationContext());
//		if(setting.loadServerLastUpdate() != 0) {
//		if(setting.isUpdated()) {
			languageList = new ArrayList<Language>();
			languageList = DBInterface.DBSearchSongLanguage("" , SearchMode.MODE_FULL, 0, 0, getActivity());

			ManageList();

			adapter = new LanguageAdapter(getActivity(), languageList);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					if (listener != null) {
						LanguageStore langStore = new LanguageStore(getActivity());
						langStore.setLanguage(languageList.get(position));

						ManageList();
						CheckAllLangugage();
						adapter.notifyDataSetChanged();
						reloadDataSong();
					}
				}
			});
			listView.setAdapter(adapter);
//		}

		changeColorScreen();

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(timerReload != null){
			timerReload.cancel();
			timerReload = null;
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	private void ManageList(){
		LanguageStore langStore = new LanguageStore(getActivity());
		for(Language lang:languageList){
			boolean active = langStore.getActiveLanguage(lang);
			lang.setActive(active);
		}
	}

	private void CheckAllLangugage(){
		if(languageList != null && languageList.size() > 0){
			boolean flagEmpty = true;
			for (Language lang : languageList) {
				if(lang.isActive()){
					flagEmpty = false;
					break;
				}
			}

			if(flagEmpty){
				LanguageStore langStore = new LanguageStore(context);
				langStore.setLanguage(languageList.get(0));
				languageList.get(0).setActive(true);
			}
		}
	}
	
///////////////////////////// - LISTENER - //////////////////////////////

	public String search;
	@Override
	public void OnSearchMain(int state1, int state2, String search) {
//		MyLog.e("FragmentLanguage", state1 + " : " + state2 + " : " + search);
	}

	@Override
	public void OnSK90009() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void UpdateAdapter() {
		// TODO Auto-generated method stub
		
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
		changeColorScreen();
	}
	
	private Timer timerReload;
	private void reloadDataSong(){
		if(timerReload != null){
			timerReload.cancel();
			timerReload = null;
		}
		timerReload = new Timer();
		timerReload.schedule(new TimerTask() {
			@Override public void run() {
				handlerReload.sendEmptyMessage(0);
			}
		}, 1500);		
	}
	
	private Handler handlerReload = new Handler(){
		public void handleMessage(Message msg) {
			listenerLanguage.OnClickLanguage();
		}
	};
	
	private void changeColorScreen(){
//		if(MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI){
//			viewLine.setBackgroundResource(R.drawable.touch_shape_line_ver);
//			viewBackgroup.setBackgroundColor(Color.parseColor("#004c8c"));
//			viewBackgroup.setAlpha(0.95f);
//		}else if(MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT){
//			viewLine.setBackgroundResource(R.drawable.zlight_shape_line_ver);
//			viewBackgroup.setBackgroundColor(Color.argb(255, 193, 255, 232));
//			viewBackgroup.setAlpha(1.0f);
//		}
//		topModelView.invalidate();
//		buttonModel.invalidate();
//		if(adapter != null){
//			adapter.notifyDataSetChanged();
//		}
	}

	@Override
	public void OnClosePopupYouTube(int position) {
		// TODO Auto-generated method stub
		
	}
}