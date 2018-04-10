package vn.com.sonca.zktv.main;

import vn.com.sonca.params.ServerStatus;

public interface OnKTVMainListener {
	public void OnKTVSearch(String search);
	public void OnLayoutFrag(ServerStatus status);
	public void OnSK90009();
}
