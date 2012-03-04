/*******************************************************************************
**  defuze.me - modern radio automation software suite
**  
**  Copyright © 2012
**    Athena Calmettes - Jocelyn De La Rosa - Francois Gaillard
**    Adrien Jarthon - Alexandre Moore - Luc Peres - Arnaud Sellier
**
**  website: http://defuze.me
**  contact: team@defuze.me
**
**  This program is free software: you can redistribute it and/or modify it
**  under the terms of the GNU Lesser General Public License as published by
**  the Free Software Foundation, either version 3 of the License, or
**  (at your option) any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU Lesser General Public License for more details.
**
**  You should have received a copy of the GNU Lesser General Public License
**  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*******************************************************************************/

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
