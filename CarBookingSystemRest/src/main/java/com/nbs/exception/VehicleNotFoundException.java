package com.nbs.exception;

public class VehicleNotFoundException  extends RuntimeException {
	public VehicleNotFoundException(String msg) {
		super(msg);
	}
}
