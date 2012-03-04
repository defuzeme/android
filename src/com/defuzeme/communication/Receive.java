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

import java.util.ArrayList;
import java.util.Vector;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R;
import com.defuzeme.R.string;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.util.Log;

@SuppressWarnings("unused")
public class Receive {

	private DefuzeMe	_application	= null;
	
	public Receive(DefuzeMe application) {
		this._application				= application;
		DefuzeMe._requests				= new Vector<StreamObject>();
	}

	/*
	 * Actions methods
	 */
	public void ok(StreamObject object) {}

	public void authenticationRequest(StreamObject object) {
	    DefuzeMe._handler.post(DefuzeMe.Gui.showAuthenticatingProgress);
	    Communication.Send.sendAuthenticationObject(object);
	}

	public void authenticated(StreamObject object) {
		DefuzeMe.Gui.hideProgress();
	}
	
	public void authenticationFailed(StreamObject object) {
		DefuzeMe.Gui.hideProgress();
		DefuzeMe._handler.post(DefuzeMe.Gui.toastAuthenticationFailed);
		DefuzeMe._handler.post(DefuzeMe.Network.disconnect);
	}
	
	public void newQueueElem(StreamObject object) {
		final Integer position	= object.data.position;
		DefuzeMe._queueTracks.add(object.data.position, object);
	}
	
	public void popQueue(StreamObject object) { 
		/*
		 * DEPRECATED
		 * Don't use popQueue,
		 * removeQueueElem do all the job
		 */
	}

	public void removeQueueElem(StreamObject object) {
		final int index	= object.data.position;
		
		if (DefuzeMe._queueTracks.get(object.data.position) != null) {
			if (DefuzeMe._queueTracks.remove(index) == null) {
				Log.e(this.getClass().getName(), "Can't remove item Queue Track item");
			}
		}
	}
	
	public void play(StreamObject object) {
		DefuzeMe._playingStatus = true;
	}
	
	public void pause(StreamObject object) {
		DefuzeMe._playingStatus = false;
	}
	
	/*
	 * Re-index positions
	 */
	public void reIndex() {
		for (int index = 0; index < DefuzeMe._queueTracks.size(); index += 1) {
			final StreamObject newObject	= DefuzeMe._queueTracks.get(index);
			newObject.data.position			= index;
			DefuzeMe._queueTracks.set(index, newObject);
		}
	 }
	
}
