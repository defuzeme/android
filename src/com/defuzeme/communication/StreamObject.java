package com.defuzeme.communication;

public class StreamObject {

	public	Data	data 	= new Data();
	public	String	event;
	public	Integer	uid;
	public	Integer	replyTo;

	public StreamObject() {
		this.data	= new Data();
	}
	
	/*
	 * Data values
	 */
	public static class Data {
		public	Content	content;

		public	String	type;
		public	String	token;
		public	String	message;
		public	Integer	position;
		public	String	appVersion;
		public	String	deviceName;
		public	Integer	oldPosition;
		
		public Data() {
			this.content	= new Content();
		}
	}

	/*
	 * attributes values
	 */
	public static class Attributes {
		public	Integer	id;
		public	String	end;
	}
	
	/*
	 * track values
	 */
	public static class Content {
		public	String	title;
		public	String	artist;
		public	String	album;
		public	String	album_artist;
		public	Integer duration;
		public	String	genre;
		public	String	track;
		public	String	year;
		public	Integer	id;
	}
	
}
