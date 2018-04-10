package vn.com.sonca.newUI.modal.view.keyboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.MyLog.MyLog;
import vn.com.sonca.Touch.touchcontrol.OnMainListener;
import vn.com.sonca.database.DBInstance;
import vn.com.sonca.database.DBInterface;
import vn.com.sonca.newUI.main.KTVMainActivity;
import vn.com.sonca.newUI.modal.DB.KeyboarMappingData;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnClickBaseKeyListener;
import vn.com.sonca.newUI.modal.KtvListenerInteface.OnClickKeyBoardListener;
import vn.com.sonca.newUI.modal.fragment.FragmentBase;
import vn.com.sonca.params.ServerStatus;
import vn.com.sonca.zzzzz.MyApplication;

public class GroupKeyBoard extends ViewGroup implements OnClickBaseKeyListener,KeyboarMappingData.OnKeyBoardMappingResult {

    private final String TAB = "GroupKeyBoard : ";
    public static final String CLEAR = "CLEAR";
    public static final String SPACE = "SPACE";
    private String strSearch = "";
    private KeyboarMappingData keyboarMappingData = null;
    private FragmentBase.FragmentType searchType;
    private int searchState = TouchSearchView.TATCA;
    private int state2 = TouchSearchView.TATCA;
    private int state1 = TouchSearchView.TATCA;
    private int languageID = 0 ;


    public String getStrSearch() {
        return strSearch;
    }
    public void setSearchType(FragmentBase.FragmentType searchType){
        this.searchType = searchType;
    }
    public void setStrSearch(String strSearch) {
        this.strSearch = strSearch;
    }

    private String[] NAMECODE1 = {
            "1","2","3","4","5","6","7","8","9","0",
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
            SPACE, "Z", "X", "C", "V", "B", "N", "M", CLEAR
    };

    private ArrayList<String> NAMECODE = new ArrayList<String>();

    public GroupKeyBoard(Context context) {
        super(context);
        initView(context);
    }

    public GroupKeyBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public GroupKeyBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private Drawable drawAC;
    private Drawable drawIN;
    private Drawable drawClearAC;
    private Drawable drawClearIN;
    private Drawable drawSpaceAC;
    private Drawable drawSpaceIN;

    public void initView(Context context) {
        Collections.addAll(NAMECODE, NAMECODE1);
        drawClearAC = getResources().getDrawable(R.drawable.del_active);
        drawClearIN = getResources().getDrawable(R.drawable.del_inactive);
        drawSpaceAC = getResources().getDrawable(R.drawable.space_active);
        drawSpaceIN = getResources().getDrawable(R.drawable.space_inactive);
        drawAC = getResources().getDrawable(R.drawable.btn_active);
        drawIN = getResources().getDrawable(R.drawable.btn_inactive);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidth = MeasureSpec.getSize(widthMeasureSpec);
        int myHeight = 336*myWidth/688;
        setMeasuredDimension(myWidth , myHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    //space between vertical 12
    // space between horizontal 18
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getWidth();
        int widthview  = 58*width/688;
        int heightview = 66*width/688;
        int left_margin = 8*width/688;
        int top_margin = 16*width/688;

        int l = left + left_margin*3;
        int t = top + top_margin;
        int r = l+ widthview;
        int b = t + heightview;

        for (String character: NAMECODE) {
            BaseKey view = (BaseKey) getChildAt(NAMECODE.indexOf(character));
//            MyLog.e("--Basekey-- : ",  "___"+ NAMECODE.indexOf(character)+" ___");
            if(view!=null) {
                view.setOnClickBaseKeyListener(this);
                view.setImage(drawAC, drawIN, character);
                switch (character){
                    case "1":
                        l = left + left_margin*3;
                        r = l + widthview;
                        t = top_margin;
                        b = t + heightview;
                        break;
                    case "Q":
                        l = left + left_margin*3;
                        r = l+widthview;
                        t = heightview + top_margin*2;
                        b = t+ heightview;
                        break;
                    case SPACE:
                        l = left + left_margin*3;
                        r =  l + widthview*95/55;
                        t =  (heightview+top_margin)*3 + top_margin;
                        b = t + heightview;
                        view.setImage(drawSpaceAC,drawSpaceIN,"");
                        break;
                    case CLEAR:
                        l = widthview*7 + left + left_margin*3 + widthview*95/55 + left_margin*8;
                        r =  l + widthview*95/55;
                        view.setImage(drawClearAC,drawClearIN,"");
                        break;
                    case "A":
                        l = left_margin*3 + widthview/2;
                        r = l + widthview;
                        t = 2*(heightview + top_margin) + top_margin;
                        b = t + heightview;
                        break;
                    default:
                        l = r + left_margin;
                        r = l + widthview;
                        break;
                }
                view.layout(l, t, r, b);
            }
        }
    }

    private OnClickKeyBoardListener listener;
    public void setOnClickKeyBoardListener(OnClickKeyBoardListener listener){
        this.listener = listener;
    }

    @Override
    public void OnNameKey(String namekey, View view) {
        switch (namekey){
            case SPACE :
                break;
            case CLEAR:
                if(strSearch.length()!=0)
                strSearch = strSearch.substring(0,strSearch.length()-1);
                break;
            default:
                strSearch += namekey;
                break;
        }
        if(keyboarMappingData==null) {
            keyboarMappingData = new KeyboarMappingData(getContext(), strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }
        else {
            keyboarMappingData.cancel(true);
            keyboarMappingData=null;
            keyboarMappingData = new KeyboarMappingData(getContext(), strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }


        if(listener != null){
            listener.OnTextShow(namekey);
        }
    }
    public void changeColorScreen(String datamapping){
        for (String character: NAMECODE) {
            BaseKey view = (BaseKey) getChildAt(NAMECODE.indexOf(character));

            if(datamapping.contains(character)) {
                view.setEnable(true,drawAC, drawIN, character);

            }else if(character.equals(SPACE)){
                view.setEnable(false,drawSpaceAC,drawSpaceIN,character);
            }
            else if(character.equals(CLEAR)){
                view.setEnable(true,drawClearAC,drawClearIN,character);
            }
            else {
                view.setEnable(false,drawIN,drawIN,character);
            }

        }
    }
    public void disableKeyboard(){
        for (String character: NAMECODE) {
            BaseKey view = (BaseKey) getChildAt(NAMECODE.indexOf(character));
            switch (character) {
                case SPACE:
                    view.setEnable(false, drawSpaceAC, drawSpaceIN, character);
                    break;
                case CLEAR:
                    view.setEnable(false, drawClearAC, drawClearIN, character);
                    break;
                default:
                    view.setEnable(false, drawIN, drawIN, character);
                    break;
            }

        }
    }

    @Override
    public void OnKeyBoardMapping(String res) {
        changeColorScreen(res);
    }

    public void clearAllSearch() {
        strSearch = "";
        if(keyboarMappingData==null) {
            keyboarMappingData = new KeyboarMappingData(getContext(), strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }
        else {
            keyboarMappingData.cancel(true);
            keyboarMappingData=null;
            keyboarMappingData = new KeyboarMappingData(getContext(),strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }

    }

    public void setStateSearch(int stateSearch) {
        this.state2 = stateSearch;
        if(keyboarMappingData==null) {
            keyboarMappingData = new KeyboarMappingData(getContext(), strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }
        else {
            keyboarMappingData.cancel(true);
            keyboarMappingData=null;
            keyboarMappingData = new KeyboarMappingData(getContext(),strSearch.length(), strSearch,searchType,languageID,state2);
            keyboarMappingData.setOnKeyBoardMappingResult(this);
            keyboarMappingData.execute();
        }
    }
}
