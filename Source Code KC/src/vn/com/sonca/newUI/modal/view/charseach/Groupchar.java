package vn.com.sonca.newUI.modal.view.charseach;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import vn.com.sonca.MyLog.MyLog;



public class Groupchar extends ViewGroup implements OnCharacterSearchListener {

    private String[] NAMECODE1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
             "Z", "X", "C", "V", "B", "N", "M"
    };
    private ArrayList<String> NAMECODE = new ArrayList<>();

    public void setOnGroupCharSearchListener(OnGroupCharSearchListener listener) {
        this.listener = listener;
    }

    private OnGroupCharSearchListener listener;


    public Groupchar(Context context) {
        super(context);
        init();
    }

    private void init() {
        Arrays.sort(NAMECODE1);
        Collections.addAll(NAMECODE, NAMECODE1);
    }

    public Groupchar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Groupchar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = widthMeasureSpec;
        int h = 26*58*w/90;
        setMeasuredDimension(w,h);
    }

    @Override
    protected void onLayout(boolean i, int left, int top, int right, int bottom) {
        int l  = left;
        int r = right;
        int t= top;
        int height = (bottom-top)/26;
        int b  = t + height;
        for (String s: NAMECODE) {
            Character character = (Character) getChildAt(NAMECODE.indexOf(s));
            MyLog.e("Groupchar","---"+s+"----");
            character.setCharacter(s);
            character.setOnCharClickedListener(this);
            character.layout(l,t,r,b);
            t = b;
            b= t+height;
        }
    }

    @Override
    public void OnCharacterCLicked(String character) {
        changeColor(character);
        if(listener!=null) listener.OnGroupCharSearch(character);
    }
    public void OnCharacterClicked(String character) {
        changeColor(character);
    }

    private void changeColor(String s) {
        if(s.equals("")){
            for (int i = 0; i<26;i++) {
                Character character = (Character) getChildAt(i);
                character.setSelect(false);
            }
        }
        else{
            for (int i = 0; i<26;i++){
                Character character = (Character) getChildAt(i);
                if (s.equals(character.getCharacter())) character.setSelect(true);
                else character.setSelect(false);
            }
        }
    }
}
