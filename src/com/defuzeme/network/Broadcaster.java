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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.string;
import android.os.AsyncTask;
import android.util.Log;

public class Broadcaster extends AsyncTask <Void, Void, Void> {

	private	InetAddress		_address		= null;
	private	byte[]			_buffer			= null;
	private	DatagramPacket	_packet			= null;
	
	private	MulticastSocket	_rSocket		= null;
	private	DatagramSocket	_sSocket		= null;

	@Override
	protected void onPreExecute() {
		DefuzeMe.Gui.showProgress(string.SearchingForClients);
		
		try {
			this._address	= InetAddress.getByName(Settings._BCastSendAddress);
	        this._rSocket	= new MulticastSocket(Settings._BCastRecvPort);
			this._sSocket	= new DatagramSocket();
			
	        this._rSocket.joinGroup(InetAddress.getByName(Settings._BCastRecvAddress));
			this._rSocket.setSoTimeout(Settings._TimeOut);
		} catch (UnknownHostException exception) {
			Log.w(this.getClass().getName(), "Unknown host : " + exception.toString());
		} catch (SocketException exception) {
			Log.e(this.getClass().getName(), "Socket exception : " + exception.toString());
		} catch (IOException exception) {
			Log.w(this.getClass().getName(), "IO Exception : " + exception.toString());
		}
	}

	@Override
	protected Void doInBackground(Void... args) {
		for (int attempts = 0; attempts < 2; attempts += 1) {
			try {
				this.sendRequest();
				this.getResponse();
			} catch (IOException exception) {
				Log.w(this.getClass().getName(), "IO Exception : " + exception.toString());
			}
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void args) {
		this._rSocket.close();
		this._sSocket.close();
		this._sSocket.disconnect();
		finalize();
	}
	
	@Override
	protected void finalize() {
		final String hostName = DefuzeMe.Preferences.get(string.hostName);

		DefuzeMe.Gui.hideProgress();
		
		if (Clients.Hosts.size() <= 0)
			DefuzeMe.Network.onNetworkComplete(Settings._BCastNoClient);
		else if (Clients.Hosts.containsKey(hostName) == false)
			DefuzeMe.Network.onNetworkComplete(Settings._SelectClient);
		else
			DefuzeMe.Network.onNetworkComplete(Settings._ConnectClient);

		try {
			super.finalize();
		} catch (Throwable exception) {
			Log.e(this.getClass().getName(), "IO Exception : " + exception.toString());
		}
	}

	private void getResponse() throws IOException {
		this._buffer	= new byte[Settings._BuffLength];
        this._packet	= new DatagramPacket(this._buffer, Settings._BuffLength);
		
		while (true) {
			this._rSocket.receive(this._packet);
			Network._clients.add(this._packet);
		}
	}
	
	private void sendRequest() throws IOException {
		this._buffer	= new byte[Settings._BuffLength];
		this._packet	= new DatagramPacket(this._buffer, Settings._BuffLength);
		this._buffer	= Settings._Discover.getBytes();
		
		this._packet.setAddress(this._address);
		this._packet.setPort(Settings._BCastSendPort);
		this._packet.setData(this._buffer);
		
		this._sSocket.setBroadcast(true);
		this._sSocket.setReuseAddress(true);
		this._sSocket.send(this._packet);
	}

}
