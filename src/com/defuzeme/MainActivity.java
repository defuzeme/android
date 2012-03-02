package com.defuzeme;

import com.defuzeme.R;
import com.defuzeme.R.string;
import com.defuzeme.gui.Gui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	protected	DefuzeMe	_application	= null;

	public		Intent		_playQueue		= null;
	public		Intent		_scheduler		= null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);
        this.start();
    }
	
	public void start() {
		this._application		= (DefuzeMe) getApplication();

        DefuzeMe._activity		= this;
        DefuzeMe._mainActivity	= this;
		DefuzeMe.Gui.setActivity(this);
        DefuzeMe.Network.connect();
	}

	public void PlayQueue() {
		this._playQueue	= new Intent(this, com.defuzeme.playqueue.PlayQueue.class);
		this.startActivity(this._playQueue);
	}

	public void Scheduler() {
		this._scheduler	= new Intent(this, com.defuzeme.scheduler.Scheduler.class);
		this.startActivity(this._scheduler);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
	    final MenuInflater inflater	= this.getMenuInflater();
		if (Gui._isConnected == true) {
			inflater.inflate(R.menu.main_activity_menu, menu);
		} else {
			inflater.inflate(R.menu.main_activity_menu_disconnected, menu);
		}
	    return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration config) {
	  super.onConfigurationChanged(config);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.connect:
		        DefuzeMe.Network.connect();
		        return true;
		    case R.id.manualConnection:
		        DefuzeMe.Gui.showInputManualConnectDialog(string.ManualConnection, string.EnterIPAddress);
		        return true;
		    case R.id.disconnect:
		        DefuzeMe.Network.disconnect();
		        return true;
		    case R.id.help:
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void finish() {
		super.finish();
		System.exit(0);
	}

}
