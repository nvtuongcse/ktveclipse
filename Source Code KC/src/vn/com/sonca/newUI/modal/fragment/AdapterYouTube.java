package vn.com.sonca.newUI.modal.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.moonbelly.youtubeFrag.MyYouTubeInfo;

import java.util.ArrayList;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.newUI.main.KTVMainActivity;
import vn.com.sonca.zzzzz.MyApplication;

public class AdapterYouTube extends ArrayAdapter<MyYouTubeInfo> {

	private String TAB = "AdapterYouTube";
	private Context context;
	
	private KTVMainActivity mainActivity;
	
	private ArrayList<MyYouTubeInfo> listData;
	private String searchStr;
	
	private OnAdapterYouTubeListener listener;

	public interface OnAdapterYouTubeListener {		
		public void onClickYouTube(MyYouTubeInfo info, int type, int position, float x, float y);
		public void onClickXemTruoc(MyYouTubeInfo info);
	}

	public void setOnAdapterYouTubeListener(OnAdapterYouTubeListener listener) {
		this.listener = listener;
	}
	
	public AdapterYouTube(Context context, int resource) {
		super(context, resource);
	}
	
	public AdapterYouTube(Context context, int resource, ArrayList<MyYouTubeInfo> listData, String search, KTVMainActivity mainActivity) {
		super(context, resource, listData);
		this.context = context;
		this.listData = listData;
		this.searchStr = search;
		this.mainActivity = mainActivity;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemYouTube myItem = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_youtube_list, null);
			myItem = (ItemYouTube) convertView.findViewById(R.id.itemYouTube);
			convertView.setTag(R.id.itemYouTube, myItem);
		} else {
			myItem = (ItemYouTube) convertView.getTag(R.id.itemYouTube);
		}
		
		if(myItem != null){
			if(position >= listData.size()){
				return convertView;
			}
			
			MyYouTubeInfo info = listData.get(position);
			
			int ordinarily = ((MyApplication) context.getApplicationContext()).CheckSongInPlayListYouTube(info);	
			
			myItem.setContentView(position, info);
			myItem.setOrdinarly(ordinarily);
			myItem.setOnItemYouTubeListener(new ItemYouTube.OnItemYouTubeListener() {
				
				@Override
				public void onClickYouTube(MyYouTubeInfo info, int type, int position,
                                           float x, float y) {
					if(listener != null){
						listener.onClickYouTube(info, type, position, x, y);
					}
				}

				@Override
				public void onClickXemTruoc(MyYouTubeInfo info) {
					if(listener != null){
						listener.onClickXemTruoc(info);
					}
				}

				@Override
				public void onCallPopup(int position, View v) {
					if(mainActivity != null){
						mainActivity.setPopupYouTube(v, position);
					}
				}
			});
			
		}
		
		return convertView;
	}

}
