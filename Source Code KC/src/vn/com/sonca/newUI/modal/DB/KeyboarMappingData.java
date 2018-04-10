package vn.com.sonca.newUI.modal.DB;

import android.content.Context;
import android.os.AsyncTask;

import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.database.DBInstance;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.modal.fragment.FragmentBase;
import vn.com.sonca.newUI.modal.view.keyboard.GroupKeyBoard;

/**
 * Created by tuong on 30/01/2018.
 */

public class KeyboarMappingData extends AsyncTask<Void, Void, Void> {


    private  int lenght;
    private  String keyword;
    private  Context context;
    private OnKeyBoardMappingResult listener;
    private FragmentBase.FragmentType searchType;
    private int languageID;
    private int searchState;


    public KeyboarMappingData(Context context, int length, String keyword, FragmentBase.FragmentType searchType,int languageID,int searchState) {
        this.context = context;
        this.lenght = length;
        this.keyword = keyword;
        this.searchType = searchType;
        this.searchState = searchState;
        this.languageID = languageID;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    public void setOnKeyBoardMappingResult(OnKeyBoardMappingResult listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.OnKeyBoardMapping(DBInterface.datamapping(context,lenght,keyword,searchType,searchState,languageID));

    }

    public interface OnKeyBoardMappingResult {
        void OnKeyBoardMapping(String res);
    }
}
