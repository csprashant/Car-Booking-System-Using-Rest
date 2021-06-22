package com.nbs.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nbs.model.User;
import com.nbs.model.Vehicle;
import com.nbs.repository.VehicleRepository;

@Service
public class VehicleService {
	private final VehicleRepository repository;
	
	public VehicleService(VehicleRepository vehicleRepository) {
		this.repository=vehicleRepository;
	}
	/**
	 * save a new vehicle class object  
	 *@param vehilce  Vehicle class object 
	 */
	public String saveVehicle(Vehicle vehilce){
		try{vehilce.setUpdated(new Timestamp(new Date().getTime()));
		repository.save(vehilce);
		return "Record saved ";}
		catch(Exception e)
		{
			throw new RuntimeException("Internal problem");
		}
	}

	/**
	 *  Returns all vehicle details
	 *@return retruns List<Vehicle> List of vehicles 
	 */
	public List<Vehicle> getAllVehicleInfo() {
		List<Vehicle> listVehicle=(List<Vehicle>)repository.findAll();
		return listVehicle;	
	}
	
	/**
	 	*Returns single vehicle class object
	 	*@param vehicleId a Integer value represents vehicleId 
	 */
	public Vehicle getVehicleInfo(Integer vehicleId)
	{	Optional<Vehicle> vehicle = repository.findById(vehicleId);
		return vehicle.get();
	}
	/**
		*Deletes single Vehicle
		*@param vehicleId  a Integer value represents vehicleId 
	 */
		public String  deleteVehicle(Integer vehicleId)
	{	Vehicle v=repository.findById(vehicleId).get();
	if(v!=null) {
			repository.deleteById(vehicleId);
			return "Record deleted";
	}
	else
		return "Record not found";
	}
		/**
		 * 
		 * @param vehicle Vehicle class object
		 * @return vehicle class updated object
		 */
		public Vehicle  updateVehicle(Vehicle vehicle) {
			Vehicle existingVehicle=repository.findById(vehicle.getId()).get();
			if(existingVehicle!=null) {
				existingVehicle.setvName(vehicle.getvName());
				existingVehicle.setvColor(vehicle.getvColor());
				existingVehicle.setvNumber(vehicle.getvNumber());
			return repository.save(existingVehicle);
			}
			else
				return null;
		}
}
