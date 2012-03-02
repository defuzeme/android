/**
 * 
 */
package com.defuzeme.playqueue;

import com.defuzeme.DefuzeMe;
import com.defuzeme.network.Network;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author François Gaillard
 */
public class Updater extends AsyncTask<PlayQueue, Void, Void> {

	private int	_tracksHashCode	= 0;
	
	protected Void doInBackground(PlayQueue... playQueue) {
		boolean	playingStatus	= DefuzeMe._playingStatus;
		
		while (true) {
			try {
				Thread.sleep(100);
				
				if (playingStatus != DefuzeMe._playingStatus) {
					if (DefuzeMe._playingStatus == false)
						DefuzeMe._handler.post(PlayQueue.Gui.setPauseStatus);
					else
						DefuzeMe._handler.post(PlayQueue.Gui.setPlayStatus);
					
					playingStatus = DefuzeMe._playingStatus;
				}
				
				if (Network._socket.isClosed()) {
					playQueue[0].finish();
					return null;
				}
				
				if (DefuzeMe._queueTracks != null) {
					if (this._tracksHashCode != DefuzeMe._queueTracks.hashCode()) {
						DefuzeMe._handler.post(playQueue[0].updaterHandler);
						DefuzeMe._handler.post(playQueue[0].hideProgressHandler);
						
						this._tracksHashCode	= DefuzeMe._queueTracks.hashCode();
					}
				}
			} catch (InterruptedException exception) {
				Log.e(this.getClass().getName(), exception.toString());
			}
		}
	}
    
}
