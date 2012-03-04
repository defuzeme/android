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

import java.util.ArrayList;
import java.util.Vector;

import com.defuzeme.communication.Communication;
import com.defuzeme.communication.StreamObject;
import com.defuzeme.gui.Events;
import com.defuzeme.gui.Gui;
import com.defuzeme.network.Network;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

public class DefuzeMe extends Application {
	
	/*
	 * GuiInterface
	 */
	public static	Events					Events			= null;
	public static	Gui						Gui				= null;

	/*
	 * Network
	 */
	public static	Network					Network			= null;

	/*
	 * Communication
	 */
	public static	Communication			Communication	= null;
	
	public static	Vector<StreamObject>	_requests		= null;
	public static	Vector<StreamObject>	_responses		= null;
	
	public static	ArrayList<StreamObject>	_queueTracks	= null;
	public static	boolean					_playingStatus	= false;
	
	/*
	 * Tools
	 */
	public static	Activity				_activity		= null;
	public static	MainActivity			_mainActivity	= null;
	public static	Handler					_handler		= null;
	
	public static	Gson					Gson			= null;
	public static	Preferences				Preferences		= null;
	
	/*
	 * Methods
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		DefuzeMe._handler		= new Handler();
		
        DefuzeMe.Preferences	= new Preferences(this);
        DefuzeMe.Events			= new Events(this);
        DefuzeMe.Gson			= new Gson();
        DefuzeMe.Gui			= new Gui();

        DefuzeMe.Communication	= new Communication(this);
		DefuzeMe.Network		= new Network(this);
	}

}
