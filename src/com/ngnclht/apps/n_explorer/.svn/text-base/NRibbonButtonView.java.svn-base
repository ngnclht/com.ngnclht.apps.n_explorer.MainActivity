package com.ngnclht.apps.n_explorer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ngnclht.apps.n_explorer.Nclass.ButtonInfo;

public class NRibbonButtonView extends View{
	
	private ButtonInfo buttonInfo;
	private Bitmap 		image;
	private Resources res;
	
	private  int HEIGHT_PADDING=10;
	private  int HEIGHT;
	private  int WIDTH;
	private boolean active;
	private boolean disable;
	public NRibbonButtonView(Context context,ButtonInfo bi) {
		super(context);
		buttonInfo = bi;
		res					= getResources();
		WIDTH = (int)res.getDimension(R.dimen.amain_ribbonToolbarButton_width);
		HEIGHT = (int)res.getDimension(R.dimen.amain_ribbonToolbarButton_height);
		setFocusable(true);
		setBackgroundResource(R.drawable.selector_ribbon_button_bg);
		setClickable(true);
		setLayoutParams(new LayoutParams(WIDTH,HEIGHT));
		
		init(bi, true, false, false);
	}
	public void init(ButtonInfo bi,boolean callfromOncreate, boolean disable, boolean active){
		if(callfromOncreate){
			this.active  		= bi.activeOnload && bi.activeAble;
			this.disable  		= bi.disableOnload && bi.disableAble;
		}else{
			this.active  		= active;
			this.disable  		= disable;
		}
		this.image = BitmapFactory.decodeResource(res,buttonInfo.imageRid);
		
		if(this.active) 
			this.image = BitmapFactory.decodeResource(res,buttonInfo.activeImageID);
		if(this.disable){ 
			this.image = BitmapFactory.decodeResource(res,buttonInfo.disableImageID);
			setClickable(false);
		}	
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int xStartPos = (int)(WIDTH - image.getWidth())/2;
		int yStartPos = (int)(WIDTH - image.getHeight())/2-HEIGHT_PADDING;
		canvas.drawBitmap(image, xStartPos, yStartPos, null);
		
		Paint textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		String text = (String) getResources().getText(buttonInfo.textRid);
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		xStartPos = (int)(WIDTH-bounds.width())/2;
		yStartPos = image.getHeight() + bounds.height()+HEIGHT_PADDING;
		canvas.drawText(text, xStartPos, yStartPos, textPaint);
	}
	public void updateButton(boolean isDisable, boolean active){
		init(buttonInfo, false, isDisable, active);
		invalidate();
	}
	public String getText(){
		return (String) getResources().getText(buttonInfo.textRid);
	}
	public void setActive(){
		updateButton(this.disable, true);
	}
	public void setText(int textID){
		buttonInfo.textRid = textID;
		invalidate();
	}
	public void setInative(){
		updateButton(this.disable, false);
	}
	public void setDisable(){
		updateButton(true, this.active);
	}
	public void setEnable(){
		updateButton(false, this.active);
	}
	public boolean isDisable(){
		return disable;
	}
	public boolean isActive(){
		return active;
	}
	public void toogleSelected(){
		if(isSelected()) setSelected(false); else setSelected(true);
	}
	public void toogleDisable(){
		if(isDisable()) 
			setEnable(); 
		else 
			setDisable();
	}
	public void toogleActive(){
		if(isActive()) 
			setInative();
		else 
			setActive();
	}
}
