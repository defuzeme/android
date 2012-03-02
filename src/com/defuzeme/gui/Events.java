package com.defuzeme.gui;

import com.defuzeme.DefuzeMe;
import com.defuzeme.R.id;
import com.defuzeme.R.string;

import android.view.View;
import android.view.View.OnClickListener;

public class Events implements OnClickListener {

	public DefuzeMe	_application	= null;
	
	public Events(DefuzeMe application) {
		this._application			= application;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case id.connect :
				if (DefuzeMe.Gui._buttons._connect.getText() == DefuzeMe._activity.getString(string.Connect)) {
					DefuzeMe.Network.connect();
				} else {
					DefuzeMe.Network.disconnect();
				}
				break;
			case id.queueList :
				DefuzeMe._mainActivity.PlayQueue();
				break;
			case id.scheduler :
				DefuzeMe._mainActivity.Scheduler();
				break;
		}
	}

}
