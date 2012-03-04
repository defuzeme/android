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
