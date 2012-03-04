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

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Iterator;

import com.defuzeme.DefuzeMe;
import com.defuzeme.communication.StreamAuthObject;

public class Clients {
	
	public static		HashMap<String, StreamAuthObject[]>	Hosts	= null;

	public Clients() {
		Clients.Hosts	= new HashMap<String, StreamAuthObject[]>();
	}
	
	public void add(DatagramPacket packet) {
		final String				stream	= new String(packet.getData(), 0, packet.getLength());
		final StreamAuthObject[]	object	= DefuzeMe.Gson.fromJson(stream, StreamAuthObject[].class);
		final String				host	= object[0].host;
		
		if (Clients.Hosts.containsKey(host) == false)
			Clients.Hosts.put(host, object);
	}
	
	public StreamAuthObject[] getByHostName(String hostName) {
		return Clients.Hosts.get(hostName);
	}
	
	public void select() {
		final int				index		= 0;
		final String[]			hostNames	= new String[Clients.Hosts.size()];
		final Iterator<String>	it			= Clients.Hosts.keySet().iterator(); 
		
		while (it.hasNext())
			hostNames[index] = it.next();

		DefuzeMe.Gui.showSelectClientDialog(hostNames);
	}
	
}
