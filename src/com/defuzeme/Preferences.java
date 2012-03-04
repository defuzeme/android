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

package com.defuzeme;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.defuzeme.R.string;

public class Preferences {

	private DefuzeMe			_application	= null;
	
	private Editor				_editor			= null;
	private SharedPreferences	_preferences	= null;

	public Preferences(DefuzeMe application) {
		this._application		= application;
		
		String applicationName	= new String();
		applicationName			= this._application.getString(string.Application);
		
		this._preferences		= this._application.getSharedPreferences(applicationName, 0);
		this._editor			= this._preferences.edit();
	}

	/*
	 * Shared Preferences methods
	 */
	public void delete() {
		this._editor.clear();
		this._editor.commit();
	}

	public String get(int item) {
		return this._preferences.getString(this._application.getString(item), null);
	}

	public void save(int item, String value) {
		final String itemName	= new String(this._application.getString(item));
		
		this._editor.putString(itemName, value);
		this._editor.commit();
	}
}
