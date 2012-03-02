package com.defuzeme.network;

public class Settings {

	public static final	int 	WiFiActive			= 0;
	public static final	int 	WiFiInactive		= 1;
	public static final	int 	WiFiError 			= 2;

	public static final	int 	OperatorMode		= 0;
	public static final	int 	WiFiMode 			= 1;

	public static final	int		_BuffLength			= 4096;
	public static final	int		_TimeOut			= 2500;

	public static final	int		_SocketPort			= 3456;
	public static final	int		_BCastSendPort		= 3457;
	public static final	int		_BCastRecvPort		= 3458;

	public static final	String	_BCastRecvAddress	= "224.0.0.1";
	public static final	String	_BCastSendAddress	= "255.255.255.255";

	public static final	String	_CharSet			= "UTF-8";

	public static final int		_Disconnected		= -4;
	public static final int		_CantConnectIP		= -3;
	public static final int		_CantConnect		= -2;
	public static final	int		_BCastNoClient		= -1;
	public static final	int		_SelectClient		= 0;
	public static final	int		_ConnectClient		= 1;
	public static final	int		_Connected			= 2;
	
	public static final	String	_Discover			= "\"defuze.me:discovery\"";

}
