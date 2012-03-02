package com.defuzeme.communication;

import java.util.ArrayList;
import java.util.Vector;

import com.defuzeme.DefuzeMe;
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
