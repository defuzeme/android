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
