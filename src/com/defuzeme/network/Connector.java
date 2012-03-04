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

package com.defuzeme.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Iterator;

import javax.net.SocketFactory;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.string;
import com.defuzeme.communication.StreamAuthObject;

import android.os.AsyncTask;
import android.util.Log;

public class Connector extends AsyncTask <Void, Void, Integer> {

	@Override
	protected void onPreExecute() {
		DefuzeMe.Gui.showConnectingProgress();
	}

	@Override
	protected Integer doInBackground(Void... params) {
		final String				hostname	= DefuzeMe.Preferences.get(string.hostName);
		final StreamAuthObject[]	objects		= Clients.Hosts.get(hostname);
		
		if (objects != null) {
			for (int index = 0; index < objects.length; index += 1) {
				if (connectSocket(objects[index]) == true)
					return Settings._Connected;
			}
		} else if (Clients.Hosts.size() > 0) {
			final Collection<?>	collection	= Clients.Hosts.values();
			final Iterator<?>		it		= collection.iterator();
			
			while(it.hasNext()) {
				final StreamAuthObject[] client	= (StreamAuthObject[]) it.next();

				for (int index = 0; index < client.length; index += 1)
					if (connectSocket(client[index]) == true)
						return Settings._Connected;
			}
			
			return Settings._CantConnectIP;
		}
		
		return Settings._CantConnect;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		DefuzeMe.Gui.hideProgress();

		switch (result) {
			case Settings._Connected:
				DefuzeMe.Communication.execute();
				Network._communicator.execute();
				DefuzeMe.Network.onNetworkComplete(Settings._Connected);
				break;
			case Settings._CantConnect:
				DefuzeMe.Network.onNetworkComplete(Settings._CantConnect);
				break;
			case Settings._CantConnectIP:
				DefuzeMe.Network.onNetworkComplete(Settings._CantConnectIP);
				break;
		}
	}
	
	private boolean connectSocket(StreamAuthObject object) {
		Network._socketAddress	= new InetSocketAddress(object.ip, object.port);
		
		try {
			Network._socket		= SocketFactory.getDefault().createSocket();
			Network._socket.connect(Network._socketAddress, Settings._TimeOut);
			Network._socket.setSoTimeout(Settings._TimeOut);
			
			Network._input		= new InputStreamReader(Network._socket.getInputStream());
			Network._output		= new DataOutputStream(Network._socket.getOutputStream());
			
			return true;
		} catch (IOException exception) {
			Log.w(this.getClass().getName(), exception.toString());
		}
		
		return false;
	}
	
	@Override
	protected void onCancelled() {
		if (Network._socket != null) {
			try {
				Network._socket.shutdownInput();
				Network._socket.shutdownOutput();
				Network._socket.close();
			} catch (IOException exception) {
				Log.e(this.getClass().getName(), exception.toString());
			}
		}
		
		try {
			super.finalize();
		} catch (Throwable exception) {
			Log.e(this.getClass().getName(), exception.toString());
		}
	}
	
}
