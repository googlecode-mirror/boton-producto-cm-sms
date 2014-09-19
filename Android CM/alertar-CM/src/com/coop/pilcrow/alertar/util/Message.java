package com.coop.pilcrow.alertar.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Clase Message.
 * @author ggontaruk
 *
 */
public class Message {
	private Context context;
	
	public Message(Context context){
		this.context = context;
	}
	
	public Context getContext(){
		return this.context;
	}
	
	public void showShortTimeMessage(String message){
		Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void showLongTimeMessage(String message){
		Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}