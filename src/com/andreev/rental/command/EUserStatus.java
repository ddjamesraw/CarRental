package com.andreev.rental.command;

public enum EUserStatus {
	
	ALL(0),
	REGISTERED(1),
	ADMIN(2);
	
	private final int id;
	
	private EUserStatus(int id) {
		this.id = id;
	}
	
	public int getStatusId(){
		return this.id;
	}
	
	public static EUserStatus getStatus(int id) {
		EUserStatus[] valus = EUserStatus.values();
		if(id < 0 && id > valus.length - 1) {
			return valus[0];
		}
		return valus[id];
	}
}
