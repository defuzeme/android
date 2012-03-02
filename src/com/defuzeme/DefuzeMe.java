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
