package vn.com.sonca.newUI.modal.view.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import vn.com.hanhphuc.karremote.R;
import vn.com.sonca.params.Language;
import vn.com.sonca.zzzzz.MyApplication;

public class LanguageFirstItemView extends View  {
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Path path = new Path();
	//private Context context;
	
	private float widthLayout;
    private float heightLayout;
    private float xPadding;
    private float yPadding;
    
    private float RECTANGLE_STROKE_WIDTH;
    private float LINE_WIDTH;
    private float LINE_HEIGHT;
    private String TEXT_COLOR = "#ffffff";
    private String text = "";
    private float textScale;
    private float textDX;
    private int padCheckXLeft, padCheckXRight, padCheckY;
    
    private Drawable drawable;
    
	public LanguageFirstItemView(Context context) {
		super(context);
		initView(context);
	}

	public LanguageFirstItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initView(context);
	}

	public LanguageFirstItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		lang= new Language();
		lang.setActive(false);
	}
	
	private Language lang;
	private boolean isLang = false;
	public void setLanguage(Language lang){
		isLang = true;
		this.lang = lang;
	}
	
	public void setText(String text)
	{
		this.text = text;
		invalidate();
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int height = (int) (0.09*getResources().getDisplayMetrics().heightPixels);
		setMeasuredDimension(widthMeasureSpec, height);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (MyApplication.intColorScreen == MyApplication.SCREEN_BLUE || MyApplication.intColorScreen == MyApplication.SCREEN_KTVUI) {

			xPadding = 0.0f * getHeight();
			yPadding = 0.0f * getHeight();

			widthLayout = getWidth() - xPadding * 2;
			heightLayout = getHeight() - yPadding * 2;

			RECTANGLE_STROKE_WIDTH = getHeight() / 35;
			LINE_WIDTH = LINE_HEIGHT = RECTANGLE_STROKE_WIDTH * 5;

			textDX = widthLayout / 10;
			textScale = heightLayout / 2.5f;

			if (isLang) {
				if (lang.isActive()) {

					drawable = getResources().getDrawable(
							R.drawable.bg_song);
					drawable.setBounds(0, 0, (int) widthLayout,
							(int) heightLayout);
					drawable.draw(canvas);
				}
			}

			resetPaint();
//
			drawable = getFlagIcon(lang);
			if(drawable != null){
				int tamY = (int) (0.35*heightLayout);
				int tamX = (int)(125 * heightLayout / 135);
				int hI = (int)(38 * heightLayout / 135);
				int wI = 64/30*38*hI/48;
				Rect rectImage = new Rect(tamX - wI, tamY - hI, tamX + wI, tamY + hI);
									
				drawable.setBounds(rectImage);
				drawable.draw(canvas);
				
				textDX = textDX + rectImage.right ;
			}
			
			mPaint.setTextSize(textScale);
			mPaint.setColor(Color.parseColor(TEXT_COLOR));
			Rect boundRect = new Rect();
			mPaint.getTextBounds(text, 0, text.length(), boundRect);
			canvas.drawText(text, textDX, 2 * heightLayout / 3, mPaint);

			padCheckXLeft = (int) (150 * heightLayout / 135);
			padCheckXRight = (int) (60 * heightLayout / 135);
			padCheckY = (int) (30 * heightLayout / 135);

			if (isLang) {
				if (lang.isActive()) {
					drawable = getResources()
							.getDrawable(R.drawable.check_icon);
					drawable.setBounds((int) widthLayout - padCheckXLeft,
							padCheckY, (int) widthLayout - padCheckXRight,
							(int) heightLayout - padCheckY);
					drawable.draw(canvas);
				}
			}

		} else if (MyApplication.intColorScreen == MyApplication.SCREEN_LIGHT) {

		}
	}
	
	private Drawable getFlagIcon(Language mLang){
    	if(mLang == null){
    		return null;
    	}
    	
    	Drawable drawLang = null;
    	
    	switch (mLang.getID()) {
		case 0:
			drawLang = getResources().getDrawable(R.drawable.flag_vietnam);
			break;
		case 1:
			drawLang = getResources().getDrawable(R.drawable.flag_anh);
			break;
		case 2:
			drawLang = getResources().getDrawable(R.drawable.flag_phap);
			break;
		case 3:
			drawLang = getResources().getDrawable(R.drawable.flag_china);
			break;
		case 4:
			drawLang = getResources().getDrawable(R.drawable.flag_philip);
			break;
		case 5:
			drawLang = getResources().getDrawable(R.drawable.flag_han);
			break;
		case 6:
			drawLang = getResources().getDrawable(R.drawable.flag_nhat);
			break;
		default:
			break;
		}	
    	
    	return drawLang;
    }

    private void resetPaint()
    {
    	mPaint.reset();
    	mPaint.setAntiAlias(true);
    	mPaint.setShader(null);
    }
}
