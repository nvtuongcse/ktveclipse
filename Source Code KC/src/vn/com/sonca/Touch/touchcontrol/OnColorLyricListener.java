package vn.com.sonca.Touch.touchcontrol;

public interface OnColorLyricListener {
	public void OnMain_PausePlay(boolean flagPlay);
	public void OnMain_NewSong(int songID, int intMediaType, int midiShifTime, int typeABC);
	public void OnMain_Dance(boolean flagDance);
	public void OnMain_GetTimeAgain();
	public void OnMain_setCntPlaylist(int i);
	public void OnMain_setCurrSongPlaylist(String resultName);
	public void OnMain_setNextSongPlaylist(String resultName, int resultID);
	public void OnMain_VocalSinger(boolean flagVocalSinger);
	public void OnMain_CallVideoDefault();
	public void OnMain_RemoveSocket();
	public void OnMain_UpdateSocket(String serverName);
	public void OnMain_UpdateWifi();
	public void OnMain_StartTimerAutoConnect();
	public void OnMain_StopTimerAutoConnect();
	public void OnMain_UpdateControl();
	public void OnMain_DownloadMidiResult(boolean result, int id);
}
