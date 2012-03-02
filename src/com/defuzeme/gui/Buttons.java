package com.defuzeme.gui;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.id;
import com.defuzeme.R.string;

import android.app.Activity;
import android.widget.Button;

public class Buttons {

	public Activity	_activity	= null;

	public Button	_connect	= null;
	public Button	_queueList	= null;
	public Button	_scheduler	= null;
	
	public Buttons(Activity activity) {
		this._activity	= activity;
		
		this._connect	= new Button(this._activity);
		this._queueList	= new Button(this._activity);
		this._scheduler	= new Button(this._activity);
		
		this._connect	= (Button) this._activity.findViewById(id.connect);
		this._queueList	= (Button) this._activity.findViewById(id.queueList);
		this._scheduler	= (Button) this._activity.findViewById(id.scheduler);
		
		this.disconnectedState();
		this.bindListeners();
	}

	public void bindListeners() {
		this._connect.setOnClickListener(DefuzeMe.Events);
		this._queueList.setOnClickListener(DefuzeMe.Events);
		this._scheduler.setOnClickListener(DefuzeMe.Events);
	}

	public void connectedState() {
		this._connect.setText(string.Disconnect);
		this._queueList.setEnabled(true);
		this._scheduler.setEnabled(true);
		Gui._isConnected	= true;
	}

	public void disconnectedState() {
		this._connect.setText(string.Connect);
		this._queueList.setEnabled(false);
		this._scheduler.setEnabled(false);
		Gui._isConnected	= false;
	}

}
