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

package com.defuzeme.communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.os.AsyncTask;
import android.util.Log;

import com.defuzeme.DefuzeMe;

public class Communication extends AsyncTask<Void, Void, Void>{

	private			DefuzeMe	_application	= null;
	
	public static	Receive		Receive			= null;
	public static	Send		Send			= null;
	
	public Communication(DefuzeMe application) {
		this._application	= application;

		Communication.Receive		= new Receive(this._application);
		Communication.Send			= new Send(this._application);
	}

	@Override
	protected Void doInBackground(Void... params) {
		StreamObject	object	= null;
		Method			method	= null;
		
		while (true) {
				try {
					Thread.sleep(100);
					if (DefuzeMe._requests.size() > 0) {
						object	= DefuzeMe._requests.remove(0);
						method	= Receive.class.getMethod(object.event, StreamObject.class);
						method.invoke(Communication.Receive, object);
					}
				} catch (InterruptedException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (SecurityException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (NoSuchMethodException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (IllegalArgumentException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (IllegalAccessException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				} catch (InvocationTargetException exception) {
					Log.e(this.getClass().getName(), exception.toString());
				}
		}
	}
}

