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

import com.defuzeme.DefuzeMe;
import com.defuzeme.network.Network;

import android.os.AsyncTask;
import android.util.Log;

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
