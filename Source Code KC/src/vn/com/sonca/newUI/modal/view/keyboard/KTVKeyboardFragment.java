package vn.com.sonca.newUI.modal.view.keyboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.Touch.Keyboard.*;
import vn.com.sonca.Touch.touchcontrol.OnClearTextListener;
import vn.com.sonca.Touch.touchcontrol.OnMainKeyboardListener;
import vn.com.sonca.Touch.touchcontrol.OnMainListener;
import vn.com.sonca.Touch.touchcontrol.TouchMainActivity;
import vn.com.sonca.database.DBInstance;
import vn.com.sonca.newUI.main.KTVMainActivity;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnClickKeyBoardListener;
import vn.com.sonca.newUI.modal.fragment.FragmentBase;
import vn.com.sonca.params.ServerStatus;


/**
 * Created by tuong on 22/01/2018.
 */

public class KTVKeyboardFragment extends Fragment implements OnClearTextListener,OnClickKeyBoardListener,OnMainKeyboardListener {

    private String TAB = "KeyBoardFragment";
    private KTVMainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainActivity = (KTVMainActivity) activity;
            listener = (OnKeyBoardFragmenttListener) activity;

        } catch (Exception ex) {}
    }

    private OnKeyBoardFragmenttListener listener;

    @Override
    public void OnTextShow(String search) {
        listener.OnKeyboardView(search);
    }

    public void setStateSearch(int stateSearch) {
        keyBoard.setStateSearch(stateSearch);
    }

    public interface OnKeyBoardFragmenttListener {
        public void OnKeyboardView(String key);
    }
    public void disableKeyboard(){
        keyBoard.disableKeyboard();

    }
    public void setOnKeyBoardFragmenttListener(OnKeyBoardFragmenttListener listener){
        this.listener = listener;
    }
    private GroupKeyBoard keyBoard;
    public void OnNameKey(String name){
        keyBoard.OnNameKey(name,null);
    }
    public void ClearAllSearch(){
        keyBoard.clearAllSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ktv_keyboard_fragment, container, false);
        keyBoard = (GroupKeyBoard)view.findViewById(R.id.ktv_keyboard);
        keyBoard.setOnClickKeyBoardListener(this);

        return view;
    }

    public void setSearchType(FragmentBase.FragmentType searchType){
        keyBoard.setSearchType(searchType);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void OnClearText() {

    }

    @Override
    public void OnTextShowing(String search) {

    }

    @Override
    public void OnDrawerSlide() {

    }

    @Override
    public void OnUpdateView() {

    }
}

