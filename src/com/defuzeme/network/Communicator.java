package com.defuzeme.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import com.defuzeme.DefuzeMe;
import com.defuzeme.R.string;
import com.defuzeme.communication.StreamObject;
import com.google.gson.JsonSyntaxException;

import android.os.AsyncTask;
import android.util.Log;

public class Communicator extends AsyncTask <Void, Void, Integer> {

	@Override
	protected void onPreExecute() {
	    Network._buffer			= new BufferedReader(Network._input);
	}

	@Override
	protected Integer doInBackground(Void... args) {
		try {
			while (true) {
				Thread.sleep(100);
				
				this.receive();
				this.send();
			}
		} catch (InterruptedException exception) {
			Log.e(this.getClass().getName(), exception.toString());
		}
		
		return 1;
	}

	@Override
	protected void onPostExecute(Integer result) {
		try {
			if (Network._output != null)
				Network._output.close();
		} catch (IOException exception) {
			Log.e(this.getClass().getName(), exception.toString());
		}
		
		if (result == 0)
			DefuzeMe.Gui.toast(string.Disconnected);
		
		DefuzeMe.Network.disconnect();
	}

	
	/*
	 * Receive & Send methods
	 */
	private boolean receive() {
	    int ch;
	    final StringBuilder	message	= new StringBuilder();

		try {
			while ((ch = Network._buffer.read()) != -1) {
			    if (ch == '\0') {
			    	final StreamObject object	= DefuzeMe.Gson.fromJson(message.toString(), StreamObject.class);
			        DefuzeMe._requests.add(object);
			        break;
			    } else
			    	message.append((char) ch);
			}
			
			if (ch == -1)
				DefuzeMe._handler.post(DefuzeMe.Network.disconnect);

		} catch (JsonSyntaxException exception) {
			Log.e(this.getClass().getName(), "Json syntax error : " + exception.toString());
		} catch (InterruptedIOException exception) {
			
		} catch (IOException exception) {
			
		}
		
		return true;
	}
	
	private void send() {
		if ((DefuzeMe._responses != null) && (DefuzeMe._responses.size() > 0)) {
			final StreamObject object = DefuzeMe._responses.remove(0);
			
			try {
				Network._output.writeBytes(DefuzeMe.Gson.toJson(object, StreamObject.class) + '\0');
			} catch (IOException exception) {
				Log.e(this.getClass().getName(), "I/O Exception : " + exception.toString());
			}
		}
	}

}
