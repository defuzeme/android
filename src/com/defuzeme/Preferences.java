package com.defuzeme;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.defuzeme.R.string;

public class Preferences {

	private DefuzeMe			_application	= null;
	
	private Editor				_editor			= null;
	private SharedPreferences	_preferences	= null;

	public Preferences(DefuzeMe application) {
		this._application		= application;
		
		String applicationName	= new String();
		applicationName			= this._application.getString(string.Application);
		
		this._preferences		= this._application.getSharedPreferences(applicationName, 0);
		this._editor			= this._preferences.edit();
	}

	/*
	 * Shared Preferences methods
	 */
	public void delete() {
		this._editor.clear();
		this._editor.commit();
	}

	public String get(int item) {
		return this._preferences.getString(this._application.getString(item), null);
	}

	public void save(int item, String value) {
		final String itemName	= new String(this._application.getString(item));
		
		this._editor.putString(itemName, value);
		this._editor.commit();
	}
}
