package vn.com.sonca.newUI.modal.fragment;


import java.util.ArrayList;
import vn.com.sonca.Touch.CustomView.TouchSingerItemView;
import vn.com.sonca.newUI.modal.view.listview.ItemSinger;
import vn.com.sonca.params.Musician;
import vn.com.hanhphuc.karremote.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MusicianAdapter extends ArrayAdapter<Musician> {

    private Context context;
    private String search = "";
    private ArrayList<Musician> musicianList;

    public MusicianAdapter(Context context, int resource, String search, ArrayList<Musician> musicianList) {
        super(context, resource, musicianList);
        this.context = context;
        this.musicianList = musicianList;
        if(search != null){
            this.search = search;
        }else{
            this.search = "";
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSinger singerItemView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.singer_gridview_item, null);
            singerItemView = (ItemSinger) convertView.findViewById(R.id.singer_gridview_item);
            convertView.setTag(singerItemView);
        }else{
            singerItemView = (ItemSinger) convertView.getTag();
            singerItemView.setOnSelected(false);
        }
        if(singerItemView != null){
            Musician musician = musicianList.get(position);
            singerItemView.setData(musician.getName(), search, musician.getCoverID());
        }
        return convertView;
    }

}