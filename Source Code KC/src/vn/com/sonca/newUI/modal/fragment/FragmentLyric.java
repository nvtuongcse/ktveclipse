package vn.com.sonca.newUI.modal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.Lyric.ImageSinger;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.params.Song;

public class FragmentLyric extends FragmentBase {

    private TextView lyricFull,songName,musician,lyricby ;
    private ImageSinger imgSinger;
    private Context context;
    private Song song = new Song();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        Bundle bundle = getArguments();
        song.setId(bundle.getInt("SongID"));
        song.setTypeABC(bundle.getInt("SongTypeABC"));
        View v = inflater.inflate(R.layout.lyricfragment_layout,container,false);
        lyricFull = (TextView) v.findViewById(R.id.textLyric);
        songName = (TextView) v.findViewById(R.id.textTitleLyric);
        musician = (TextView) v.findViewById(R.id.textS);
        lyricby = (TextView) v.findViewById(R.id.textL);
        imgSinger = (ImageSinger)v.findViewById(R.id.imageSinger);
        MyLog.e("LyricFragment  --  ",imgSinger.toString());
        imgSinger.setData(song);
        setDataLyric(bundle.getString("Lyric"));

        return v;

    }
    public void setSong(Song song){
        this.song = song;
        imgSinger.setData(song);
    }

    public void setDataLyric(String data){
//		String[] lyricFull = data.split("\n\n");
        if (data == null || data.equals("")) {
            songName.setText(context.getString(R.string.lyric_6));
            return;
        }
        //--------------------//
        int start = "Title: ".length();
        int end = data.indexOf("Musician:");
        if (end == -1 && songName != null) {
            songName.setText(context.getString(R.string.lyric_6));
            return;
        }
        String textdata = data.substring(start, end);
        if (songName != null) {
            songName.setText(textdata);
        }
        //--------------------//
        start = end + "Musician:".length();
        end = data.indexOf("Lyrician:");
        if (end == -1 && songName != null) {
            songName.setText(context.getString(R.string.lyric_6));
            return;
        }
        textdata = data.substring(start, end);
        if (musician != null) {
            musician.setText(textdata);
        }
        //--------------------//
        start = end + "Lyrician:".length();
        end = data.indexOf("Singer:");
        if (end == -1 && songName != null) {
            songName.setText(context.getString(R.string.lyric_6));
            return;
        }
        textdata = data.substring(start, end);
        if (lyricFull != null) {
            lyricFull.setText(textdata);
        }
        //--------------------//
        String da = data.substring(end);
        start = da.indexOf("\n");
        if (start == -1 && songName != null) {
            songName.setText(context.getString(R.string.lyric_6));
            return;
        }
        textdata = da.substring(start);
        songName.setText(context.getString(R.string.lyric_1));
        if (lyricFull != null) {
            lyricFull.setText(textdata.replaceAll("\r", "\n"));
        }
    }

    @Override
    protected void UpdateAdapter() {}
}
