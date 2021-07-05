package com.nbs.service;

import java.util.List;

import com.nbs.model.Vehicle;

public interface IVehicleService {
	public Vehicle saveVehicle(Vehicle vehilce);
	public List<Vehicle> getAllVehicleInfo();
	public Vehicle getVehicleInfo(Integer vehicleId);
	public void  deleteVehicle(Integer vehicleId);
	public String  updateVehicle(Vehicle vehicle);

}
