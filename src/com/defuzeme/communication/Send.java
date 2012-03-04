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

package com.defuzeme.communication;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.string;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Send {
	
	private DefuzeMe		_application	= null;
	
	private PackageManager	_packageManager	= null;
	private PackageInfo		_packageInfo	= null;
	
	private int				_uid			= 0;
	
	public Send(DefuzeMe application) {
		this._application	= application;
		DefuzeMe._responses	= new Vector<StreamObject>();
	}

	public void sendAuthenticationObject(StreamObject received) {
	    final StreamObject	object	= new StreamObject();
	    
	    object.uid					= ++this._uid;
	    object.replyTo				= received.uid;
	    object.event				= "authentication";

	    object.data.token 			= this.generateToken();
	    object.data.appVersion		= this.getPackageVersion();
	    object.data.deviceName		= android.os.Build.DEVICE;
		
		DefuzeMe._responses.add(object);
	}

	public void moveQueueElem(int from, int to) {
		final StreamObject	object	= new StreamObject();
		
		object.uid		= ++this._uid;
		object.event	= "moveQueueElem";
		
		object.data.oldPosition	= from + 1;
		object.data.position	= to + 1;
		
		DefuzeMe._responses.add(object);
	}

	public void next() {
		final StreamObject	object	= new StreamObject();
		
		object.uid		= ++this._uid;
		object.event	= "next";
		
		DefuzeMe._responses.add(object);
	}

	public void play() {
		final StreamObject	object	= new StreamObject();
		
		object.uid		= ++this._uid;
		object.event	= "play";
		
		DefuzeMe._responses.add(object);
	}

	public void pause() {
		final StreamObject	object	= new StreamObject();
		
		object.uid		= ++this._uid;
		object.event	= "pause";
		
		DefuzeMe._responses.add(object);
	}
	
	/*
	 * Tools
	 */
	private String generateToken() {
		SecureRandom	random	= null;
		String			token	= null;
		
		if ((token	= DefuzeMe.Preferences.get(string.authToken)) == null) {
			random	= new SecureRandom();
			token	= new BigInteger(130, random).toString(32).substring(0, 16);
			DefuzeMe.Preferences.save(string.authToken, token);
		}
		return token;
	}
	
	private String getPackageVersion() {
		try {
			this._packageManager	= this._application.getPackageManager();
			this._packageInfo		= _packageManager.getPackageInfo(this._application.getPackageName(), 0);
		} catch (Throwable exception) {
			Log.e(this.getClass().getName(), exception.toString());
		}
		return this._packageInfo.versionName;
	}
	
}
