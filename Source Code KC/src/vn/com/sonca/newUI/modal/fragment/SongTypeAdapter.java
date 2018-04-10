package vn.com.sonca.newUI.modal.fragment;

import java.util.List;
import java.util.Locale;
import vn.com.sonca.Touch.CustomView.TouchSongTypeFirstItemView;
import vn.com.sonca.Touch.CustomView.TouchSongTypeRemainItemView;
import vn.com.sonca.Touch.SongType.TouchSongTypeViewV2;
import vn.com.sonca.newUI.modal.view.listview.ItemSong;
import vn.com.sonca.newUI.modal.view.listview.ItemSongType;
import vn.com.sonca.params.SongType;
import vn.com.hanhphuc.karremote.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SongTypeAdapter extends BaseAdapter {

    private final Drawable bg_in;
    private final Drawable bg_ac;
    private Context context;
    private List<SongType> songTypeList;

    public SongTypeAdapter(Context context, List<SongType> singerList)
    {
        this.context = context;
        this.songTypeList = singerList;
        bg_in = context.getResources().getDrawable(R.drawable.bg_song);
        bg_ac = context.getResources().getDrawable(R.drawable.bg_song_active);
    }

    @Override
    public int getCount() {
        return songTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return songTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSongType songType = null;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ktv_item_song_type, null);
            songType = (ItemSongType) convertView.findViewById(R.id.itemsongtype);
            songType.setDrawable(bg_ac,bg_in,null,null,null,null,null,null,null,null,null,null,null,null,null);
            convertView.setTag(R.id.item_song, songType);
        }else{
            songType = (ItemSongType) convertView.getTag(R.id.item_song);
        }
        if(songType != null){
            SongType type = songTypeList.get(position);
            songType.setIdSong(String.valueOf(position+1));
            songType.setData(type);
        }
        return convertView;
    }

    private String changeEnglishName(String defaultStr) {
        String resultStr = defaultStr;
        if(defaultStr.equalsIgnoreCase("Song Ca") || defaultStr.equalsIgnoreCase("Remix") || defaultStr.equalsIgnoreCase("Duet")){
            return resultStr;
        }

        String name = context.getResources().getString(R.string.change_type_1);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Modern Song";
        }

        name = context.getResources().getString(R.string.change_type_2);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Children Song";
        }

        name = context.getResources().getString(R.string.change_type_3);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Prewar Song";
        }

        name = context.getResources().getString(R.string.change_type_4);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Folk Song";
        }

        name = context.getResources().getString(R.string.change_type_5);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Love Song";
        }

        name = context.getResources().getString(R.string.change_type_6);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Sounding Folk";
        }

        name = context.getResources().getString(R.string.change_type_7);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Revolution Song";
        }

        name = context.getResources().getString(R.string.change_type_8);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Bolero";
        }

        name = context.getResources().getString(R.string.change_type_9);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Trinh Cong Son";
        }

        name = context.getResources().getString(R.string.change_type_10);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Vietnamese Lyric For Chinese Song";
        }

        name = context.getResources().getString(R.string.change_type_11);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Vietnamese Lyric For English Song";
        }

        name = context.getResources().getString(R.string.change_type_12);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Vietnamese Lyric For French Song";
        }

        name = context.getResources().getString(R.string.change_type_13);
        if(name.equalsIgnoreCase(defaultStr)){
            return "Modern-Traditional Song";
        }
        return resultStr;
    }

    private class ViewHolder
    {
        private TouchSongTypeFirstItemView firstItem;
        private TouchSongTypeRemainItemView remainItem;

        public ViewHolder(TouchSongTypeFirstItemView firstItem, TouchSongTypeRemainItemView remainItem)
        {
            setFirstItem(firstItem);
            setRemainItem(remainItem);
        }

        public TouchSongTypeFirstItemView getFirstItem() {
            return firstItem;
        }

        public void setFirstItem(TouchSongTypeFirstItemView firstItem) {
            this.firstItem = firstItem;
        }

        public TouchSongTypeRemainItemView getRemainItem() {
            return remainItem;
        }

        public void setRemainItem(TouchSongTypeRemainItemView remainItem) {
            this.remainItem = remainItem;
        }
    }
}