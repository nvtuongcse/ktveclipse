package vn.com.sonca.newUI.modal.view.listview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import vn.com.sonca.params.SKServer;
import vn.com.sonca.params.ServerStatus;

public class ItemDevice extends View {


    private Paint paintSimple = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintGlow = new Paint(Paint.ANTI_ALIAS_FLAG);

    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int widthLayout = 0;
    private int heightLayout = 0;
    private SKServer skServer;

    private int stateWifi = SKServer.SAVED;
    private boolean isPassword = true;
    private int modelDevice;
    private String nameRemote;



    private Drawable drawGlow;
    private Drawable drawKetNoi;
    private Drawable drawKetNoiHover;

    private Drawable drawSaveSK9;
    private Drawable drawConnectSK9;
    private Drawable drawBroadcastSK9;
    private Drawable drawSaveKar;
    private Drawable drawConnectKar;
    private Drawable drawBroadcastKar;
    private Drawable drawSaveKM1;
    private Drawable drawConnectKM1;
    private Drawable drawBroadcastKM1;
    private Drawable drawSaveKM2;
    private Drawable drawConnectKM2;
    private Drawable drawBroadcastKM2;
    private Drawable drawSaveKTV;
    private Drawable drawConnectKTV;
    private Drawable drawBroadcastKTV;

    private String dalua = "";
    private String xoalua = "";
    private String ketnoi = "";
    private String dangketnoi = "";
    private String chuaketnoi = "";
    private String chonModel = "";
    private String dauMay = "";
    private String caidat = "";
    private String daukaraoke = "";




    public ItemDevice(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {



    }

    public ItemDevice(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemDevice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
}
