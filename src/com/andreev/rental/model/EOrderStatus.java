package com.andreev.rental.model;

public enum EOrderStatus {

	AWAITS(0, "Awaits"), 
	ACCEPTED(1, "Accepted"), 
	REJECTED(2, "Rejected"), 
	ISSUED(3, "Issued"), 
	PAID(4, "Paid");

	private final int id;
	private final String name;

	private EOrderStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getStatusId() {
		return this.id;
	}

	public String getStatusName() {
		return this.name;
	}

	public static EOrderStatus getStatus(int id) {
		EOrderStatus[] valus = EOrderStatus.values();
		if (id < 0 && id > valus.length - 1) {
			return valus[0];
		}
		return valus[id];
	}
}
