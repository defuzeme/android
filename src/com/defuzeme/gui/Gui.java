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

package com.defuzeme.gui;

import com.defuzeme.DefuzeMe;
import com.defuzeme.MainActivity;
import com.defuzeme.R;
import com.defuzeme.R.string;
import com.defuzeme.network.Network;
import com.defuzeme.network.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.InputType;

@SuppressWarnings("unused")
public class Gui {

	public			Activity		_activity		= null;

	public			Buttons			_buttons		= null;

	public static	Builder			_builder		= null;
	public static	ProgressDialog	_progress		= null;
	private			EditText		_inputText		= null;
	
	public static	boolean			_isConnected	= false;
	public			int				_selectedVal	= 0;

	public void setActivity(Activity activity) {
		this._activity	= activity;
		this._buttons	= new Buttons(this._activity);
	}

	public Activity getActivity() {
		return this._activity;
	}

	public void connectedState() {this._buttons.connectedState();}
	
	public void disconnectedState() {this._buttons.disconnectedState();}
	
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
	 * InpuDialog methods
	 */
	public void showInputManualConnectDialog(int title, int content) {
		this._inputText	= new EditText(this._activity);
		this._inputText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		Gui._builder	= new AlertDialog.Builder(this._activity);
		
		Gui._builder.setView(this._inputText);
		Gui._builder.setTitle(title); Gui._builder.setMessage(content);
		Gui._builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (isValidIPAddress(_inputText.getText().toString()) == true) {
					DefuzeMe.Network.connectIP(_inputText.getText().toString());
				} else {
					DefuzeMe.Gui.toast("\"" + _inputText.getText().toString() + "\" " + DefuzeMe._mainActivity.getString(string.NotValidIPAddress));
				}
			}
		});
		Gui._builder.setNegativeButton(android.R.string.cancel, null);
		Gui._builder.setCancelable(true);
		Gui._builder.create().show();
    }

	/*
	 * Progress methods
	 */
	public void hideProgress() {
		if (Gui._progress != null) {
			Gui._progress.dismiss();
		}
	}

	public void showProgress(int content) {
		Gui._progress	= new ProgressDialog(this._activity);
		
		Gui._progress.setTitle(null);
		Gui._progress.setMessage(this._activity.getString(content));
		Gui._progress.setCancelable(false);
		Gui._progress.show();
		
		this.timerDelayRemoveDialog(Settings._TimeOut * 3, Gui._progress);
	}
	
	public void timerDelayRemoveDialog(float time, final ProgressDialog progress) {
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        public void run() {
	        	progress.dismiss();
	        }
	    }, (long) time);
	}

	public void showConnectingProgress() {
		Gui._progress	= new ProgressDialog(_activity);
		
		Gui._progress.setTitle(string.Connecting);
		Gui._progress.setIcon(android.R.drawable.ic_dialog_info);
		Gui._progress.setMessage(_activity.getString(string.WaitConnecting));
		Gui._progress.setCancelable(true);
		Gui._progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				DefuzeMe.Network.disconnect();
			}
		});
		Gui._progress.show();
	}

	public Runnable	showAuthenticatingProgress	= new Runnable() {
		public void run() {
			Gui._progress	= new ProgressDialog(_activity);
			
			Gui._progress.setTitle(string.Authenticating);
			Gui._progress.setIcon(android.R.drawable.ic_dialog_info);
			Gui._progress.setMessage(_activity.getString(string.WaitAuthenticating));
			Gui._progress.setCancelable(false);
			Gui._progress.show();
		}
	};

	public void showProgress(int title, int content) {
		Gui._progress	= new ProgressDialog(this._activity);
		
		Gui._progress.setTitle(title);
		Gui._progress.setMessage(this._activity.getString(content));
		Gui._progress.setCancelable(false);
		Gui._progress.show();
	}

	public void showSelectClientDialog(final String[] hostnames) {
		this._selectedVal	= 0;
		Gui._builder		= new AlertDialog.Builder(this._activity);
		
		Gui._builder.setTitle(string.SelectHost);
		Gui._builder.setSingleChoiceItems(hostnames, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_selectedVal = which; } });
		Gui._builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String hostNamePref = DefuzeMe.Preferences.get(string.hostName);
				if (hostNamePref != hostnames[_selectedVal]) {
					DefuzeMe.Preferences.delete();
					DefuzeMe.Preferences.save(string.hostName, hostnames[_selectedVal]);
				}
				DefuzeMe.Network.onNetworkComplete(Settings._ConnectClient); } });
		Gui._builder.setNegativeButton(android.R.string.cancel, null);
		Gui._builder.create();
		Gui._builder.show();
	}

	public void showSelectDialog(int title, final String[] items) {
		this._selectedVal	= 0;
		Gui._builder		= new AlertDialog.Builder(this._activity);
		
		Gui._builder.setTitle(title);
		Gui._builder.setSingleChoiceItems(items, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_selectedVal = which; } });
		Gui._builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				/* Do what you want here */ } });
		Gui._builder.setNegativeButton(android.R.string.cancel, null);
		Gui._builder.create();
		Gui._builder.show();
	}
	
	/*
	 * Toast methods
	 */
	public void shortToast(int message) {
		Toast.makeText(this._activity, message, Toast.LENGTH_SHORT).show();
	}

	public void toast(int message) {
		Toast.makeText(DefuzeMe._activity, message, Toast.LENGTH_LONG).show();
	}

	public void toast(String message) {
		Toast.makeText(DefuzeMe._activity, message, Toast.LENGTH_LONG).show();
	}

	public Runnable	toastAuthenticationFailed	= new Runnable() {
		public void run() {
			Toast.makeText(DefuzeMe._activity, string.AuthenticationFailed, Toast.LENGTH_LONG).show();
		}
	};

	/*
	 * Tools
	 */

	
	public boolean isValidIPAddress (String ip)
    {
        String[]	parts = ip.split ("\\.");
        
        if (parts.length < 4)
        	return false;

        for (String s : parts) {
            final int i = Integer.parseInt (s);
            if (i < 0 || i > 255)
                return false;
        }
        
        return true;
    }
	
}
