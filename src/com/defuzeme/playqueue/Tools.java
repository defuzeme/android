package com.defuzeme.playqueue;

public class Tools {

	public String secondsToTimeString(int time) {
		String	result	= new String();

		result	= String.format("%02d", time / 3600) + ":";
		result	+= String.format("%02d", (time % 3600) / 60) + ":";
		result	+= String.format("%02d", (time % 3600) % 60);
		
		return result;
	}
	
}
