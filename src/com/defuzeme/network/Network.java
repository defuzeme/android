package com.defuzeme.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.defuzeme.R.string;
import com.defuzeme.communication.Communication;
import com.defuzeme.communication.StreamAuthObject;
import com.defuzeme.communication.StreamObject;
import com.defuzeme.DefuzeMe;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class Network extends Activity implements NetworkListener {

	protected		DefuzeMe			_application	= null;

	public static	Broadcaster			_broadcaster	= null;
	public static	Communicator		_communicator	= null;
	public static	Connector			_connector		= null;
	public static	Settings			_settings		= null;
	public static	Clients				_clients		= null;
	
	protected		ConnectivityManager	_connectivity	= null;
	protected		NetworkInfo			_network		= null;

	public static	Socket				_socket			= null;
	public static	InetSocketAddress	_socketAddress	= null;

	public static	BufferedReader		_buffer			= null;
	public static	InputStreamReader	_input			= null;
	public static	DataOutputStream	_output			= null;
	
	public Network(DefuzeMe defuzeMe) {
		this._application		= defuzeMe;
		
		Network._clients		= new Clients();
		Network._settings		= new Settings();
		
	}

	public void connect() {
		if (checkConnectivity() == Settings.WiFiActive) {
			Network._clients		= new Clients();
			Network._broadcaster	= new Broadcaster();
			Network._communicator	= new Communicator();
			Network._connector		= new Connector();
			DefuzeMe.Communication	= new Communication(this._application);
			
			Network._broadcaster.execute();
			DefuzeMe._queueTracks	= new ArrayList<StreamObject>();
		}
	}

	public void connectIP(String IPAddress) {
		Network._clients				= new Clients();
		
		final StreamAuthObject[] object	= new StreamAuthObject[1];
		object[0]						= new StreamAuthObject();

		object[0].host	= IPAddress;
		object[0].ip	= IPAddress;
		object[0].port	= Settings._SocketPort;
		
		Clients.Hosts.put(IPAddress, object);
		
		Network._communicator	= new Communicator();
		Network._connector		= new Connector();
		DefuzeMe.Communication	= new Communication(this._application);
		
		Network._connector.execute();
		DefuzeMe._queueTracks	= new ArrayList<StreamObject>();
	}
	
	public void disconnect() {
		DefuzeMe.Gui.hideProgress();
		Network._broadcaster.cancel(isFinishing());
		Network._connector.cancel(isFinishing());
		
		Network._communicator.cancel(true);
		DefuzeMe.Communication.cancel(true);
		
		try {
			Network._socket.close();
		} catch (IOException e) {
			Log.e(this.getClass().getName(), DefuzeMe._activity.getString(string.Error));
		}
		
		DefuzeMe._queueTracks = null;
		DefuzeMe.Gui.disconnectedState();
	}
	
	public Runnable	disconnect	= new Runnable() {
		public void run() {
			DefuzeMe.Gui.hideProgress();
			Network._broadcaster.cancel(isFinishing());
			Network._connector.cancel(isFinishing());
			
			Network._communicator.cancel(true);
			DefuzeMe.Communication.cancel(true);
			
			try {
				Network._socket.close();
			} catch (IOException e) {
				Log.e(this.getClass().getName(), DefuzeMe._activity.getString(string.Error));
			}
			
			DefuzeMe._queueTracks = null;
			DefuzeMe.Gui.disconnectedState();
			DefuzeMe.Gui.showAlertDialog(string.Error, string.Disconnected);
		}
	};
	
	private int checkConnectivity() {
		switch (this.isWiFiEnabled()) {
			case Settings.WiFiError : 
				DefuzeMe.Gui.showAlertDialog(R.string.dialog_alert_title, string.WiFiCantConnect);
				return Settings.WiFiError;
			case Settings.WiFiInactive :
				DefuzeMe.Gui.showAlertDialog(R.string.dialog_alert_title, string.WiFiDisabled);
				return Settings.WiFiInactive;
			default : return Settings.WiFiActive;
		}
	}

	public int isWiFiEnabled() {
		this._connectivity	= (ConnectivityManager) _application.getSystemService(Context.CONNECTIVITY_SERVICE);
		this._network		= this._connectivity.getActiveNetworkInfo();
		
		if ((this._network != null) && (this._network.getType() == Settings.WiFiMode))
			return (this._network.getState() == State.CONNECTED) ? Settings.WiFiActive : Settings.WiFiError;
		else
			return Settings.WiFiInactive;
	}

	@Override
	public void onNetworkComplete(int value) {
		switch (value) {
			case Settings._BCastNoClient :
				DefuzeMe.Gui.toast(string.NoClientFound);
				break;
			case Settings._SelectClient :
				Network._clients.select();
				break;
			case Settings._ConnectClient :
				Network._connector.execute();
				break;
			case Settings._Connected :
				DefuzeMe.Gui.connectedState();
				break;
			case Settings._CantConnect :
				DefuzeMe.Gui.toast(string.CantConnect);
				break;
			case Settings._CantConnectIP :
				DefuzeMe.Gui.toast(string.CantConnect);
				break;
			case Settings._Disconnected :
				DefuzeMe.Network.disconnect();
				DefuzeMe.Gui.toast(string.Disconnected);
				break;
		}
	}
	
}
