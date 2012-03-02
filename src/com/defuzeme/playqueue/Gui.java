package com.defuzeme.playqueue;

import com.defuzeme.R.id;
import com.defuzeme.R.string;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.widget.ImageView;

public class Gui {

	public			Activity		_activity		= null;

	public static	Builder			_builder		= null;
	public static	ProgressDialog	_progress		= null;
	
	public			int				_selectedVal	= 0;
	private			Boolean			_progressShow	= false;

	public Gui(Activity activity) {
		this._activity	= activity;
	}

	public Activity getActivity() {
		return this._activity;
	}
	
	/*
	 * AlertDialog methods
	 */
	public void showAlertDialog(int title, int content) {
		Gui._builder	= new AlertDialog.Builder(this._activity);
		Gui._builder.setTitle(title); Gui._builder.setMessage(content);
		Gui._builder.setPositiveButton(android.R.string.ok, null);
		Gui._builder.setCancelable(false);
		Gui._builder.create().show();
	}

	public void showAlertDialog(int title, String content) {
		Gui._builder	= new AlertDialog.Builder(this._activity);
		Gui._builder.setTitle(title); Gui._builder.setMessage(content);
		Gui._builder.setPositiveButton(android.R.string.ok, null);
		Gui._builder.setCancelable(false);
		Gui._builder.create().show();
    }

	/*
	 * Progress methods
	 */
	public void hideProgress() {
		if (Gui._progress != null) {
			Gui._progress.dismiss();
			this._progressShow	= false;
		}
	}

	public void showProgress(int content) {
		if (this._progressShow == false) {
			this._progressShow	= true;
			Gui._progress		= new ProgressDialog(this._activity);
			
			Gui._progress.setTitle(null);
			Gui._progress.setMessage(this._activity.getString(content));
			Gui._progress.setCancelable(true);
			Gui._progress.show();
		}
	}

	public void showUpdatingProgress() {
		if (this._progressShow == false) {
			this._progressShow	= true;
			Gui._progress		= new ProgressDialog(_activity);
			
			Gui._progress.setTitle(null);
			Gui._progress.setMessage(_activity.getString(string.UpdatingList));
			Gui._progress.setCancelable(true);
			Gui._progress.show();
		}
	}
	
	public Runnable setPlayStatus = new Runnable() {
        public void run() {
			((ImageView) _activity.findViewById(id.playPauseButton)).setImageResource(drawable.ic_media_pause);
        }
    };
	
	public Runnable setPauseStatus = new Runnable() {
        public void run() {
			((ImageView) _activity.findViewById(id.playPauseButton)).setImageResource(drawable.ic_media_play);
        }
    };

}
