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
