package com.defuzeme.communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.os.AsyncTask;
import android.util.Log;

import com.defuzeme.DefuzeMe;

public class Communication extends AsyncTask<Void, Void, Void>{

	private			DefuzeMe	_application	= null;
	
	public static	Receive		Receive			= null;
	public static	Send		Send			= null;
	
	public Communication(DefuzeMe application) {
		this._application	= application;

		Communication.Receive		= new Receive(this._application);
		Communication.Send			= new Send(this._application);
	}

	@Override
	protected Void doInBackground(Void... params) {
		StreamObject	object	= null;
		Method			method	= null;
		
		while (true) {
				try {
					Thread.sleep(100);
					if (DefuzeMe._requests.size() > 0) {
						object	= DefuzeMe._requests.remove(0);
						method	= Receive.class.getMethod(object.event, StreamObject.class);
						method.invoke(Communication.Receive, object);
					}
				} catch (InterruptedException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (SecurityException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (NoSuchMethodException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (IllegalArgumentException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (IllegalAccessException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (InvocationTargetException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				}
		}
	}
}

