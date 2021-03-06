/*******************************************************************************
**  defuze.me - modern radio automation software suite
**  
**  Copyright � 2012
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
