package com.nbs.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nbs.model.Vehicle;
import com.nbs.repository.VehicleRepository;
@Service
public class VehicleServiceImpl implements IVehicleService {
	private final VehicleRepository repository;
	public VehicleServiceImpl(VehicleRepository vehicleRepository) {
		this.repository=vehicleRepository;
	}
	
	/**
	 * save a new vehicle class object  
	 *@param vehilce  VehicleDto class object 
	 */
	
	public Vehicle saveVehicle(Vehicle vehilce){
		vehilce.setUpdated(new Timestamp(new Date().getTime()));
		return repository.save(vehilce);
	
		}

	/**
	 *  Returns all vehicle details
	 *@return retruns List<VehicleDto> List of vehicles 
	 */
	
	public List<Vehicle> getAllVehicleInfo() {
		return (List<Vehicle>)repository.findAll();
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
		*Deletes single VehicleDto
		*@param vehicleId  a Integer value represents vehicleId 
	 */
	
		public void  deleteVehicle(Integer vehicleId)
	{	
			repository.deleteById(vehicleId);
	}
		/**
		 * 
		 * @param vehicle VehicleDto class object
		 * @return vehicle class updated object
		 */
		
		public String  updateVehicle(Vehicle vehicle) {
			var existingVehicle=repository.findById(vehicle.getId()).get();
			if(existingVehicle!=null) {
				existingVehicle.setvName(vehicle.getvName());
				existingVehicle.setvColor(vehicle.getvColor());
				existingVehicle.setvNumber(vehicle.getvNumber());
				repository.save(existingVehicle);
				return "Record updated successfully";
			}
			else
				return "Record not found";
		}
}
