package vn.com.sonca.Touch.touchcontrol;

import vn.com.sonca.params.ServerStatus;

public interface OnMainListener {
	public void OnLoadSucessful();

	public void OnUpdateImage();

	public void OnUpdateCommad(ServerStatus status);

	public void OnSearchMain(int state1, int state2, String search);

	public void OnSK90009();

	public void OnUpdateView();

	public void OnClosePopupYouTube(int position);
}
