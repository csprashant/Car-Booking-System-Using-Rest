package com.nbs.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.nbs.exception.VehicleNotFoundException;
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
	{	Optional<Vehicle> vehicle =  repository.findById(vehicleId);
		if (vehicle.isPresent())
			return vehicle.get();
		else
			throw new VehicleNotFoundException("");
	}
	
	/**
		*Deletes single VehicleDto
		*@param vehicleId  a Integer value represents vehicleId 
	 */
	
		public String  deleteVehicle(Integer vehicleId){
			Optional<Vehicle> vehicle = repository.findById(vehicleId);
			if(vehicle.isPresent()) {
				repository.deleteById(vehicleId);
				return " Record Deleted";}
			else
				throw new VehicleNotFoundException("Vehicle  not found");
	}
		/**
		 * 
		 * @param vehicle VehicleDto class object
		 * @return vehicle class updated object
		 */
		
		public String  updateVehicle(Integer id,Vehicle vehicle) {
				Optional<Vehicle>existingVehicle=repository.findById(id);
				if(existingVehicle.isPresent()) {
					existingVehicle.get().setvName(vehicle.getvName());
					existingVehicle.get().setvColor(vehicle.getvColor());
					existingVehicle.get().setvNumber(vehicle.getvNumber());
					repository.save(existingVehicle.get());
					return "Record updated";
				}else
					throw new VehicleNotFoundException("Vehicle not found");			
		}
}
