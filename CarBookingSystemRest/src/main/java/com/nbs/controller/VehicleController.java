package com.nbs.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbs.dto.VehicleDto;
import com.nbs.model.Vehicle;
import com.nbs.service.IVehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	private IVehicleService vehicleService;
	
	ModelMapper mapper=new ModelMapper();

	
	/**
	 * Performs insertion operation on VehicleDto class
	 * @param vehicle {@link VehicleDto} class object must provide value for vehicle name, color and for vehicle number
	 * @param result  {@link BindingResult}
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the user saved
	 */
	
	@PostMapping("/add-vehicle")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String addVehicle(@Valid @RequestBody  VehicleDto  vehicleDto, BindingResult result, HttpServletRequest request) {
	var vehicle = new Vehicle();
	if (!result.hasErrors()) {
	mapper.map(vehicleDto,vehicle);
	vehicleService.saveVehicle(vehicle);
	return "Data saved ";
	}
	else
				return result.getAllErrors().toString();
}

	/**
	 * Display all VehicleDto information
	 * @return List<VehicleDto> contains vehicles information
	 */
	
	@GetMapping("/display-all-vehicles")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')" )
	public List<VehicleDto> getVehicles(HttpServletRequest request) {
			  List<Vehicle>  vehicle  =vehicleService.getAllVehicleInfo();
			  List<VehicleDto> listdto=new ArrayList<>();
			 VehicleDto dto;
			 while(vehicle.iterator().hasNext()){ 
				 dto=new VehicleDto();
				 mapper.map(dto,vehicle);
				 listdto.add(dto); 
			 }
			 return listdto;
}
	
	/**
	 * Performs update operation on VehicleDto class
	 * @param vehicle {@link VehicleDto} class must provide value for id ,and the information which you want to change
	 * @param request {@link HttpServletRequest}
	 * @return returns success message if the vehicle information updated.
	 */
	
	@PutMapping("/update-vehicle/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Vehicle updateVehicle(@PathVariable Integer id,@Valid @RequestBody VehicleDto vehicledto) {
			var  vehicle = new Vehicle();
			mapper.map(vehicledto, vehicle);
			return vehicleService.updateVehicle(id,vehicle);	
	}
	
	/**
	 * Performs Deletion on vehicles 
	 * @param id      vehicle id which you want to delete
	 * @param request {@link HttpServletRequest}
	 * @return return success message if the record deleted
	 */
	
	@DeleteMapping("/delete-vehicle/{id}")
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String  deleteVehicle(@PathVariable Integer id, HttpServletRequest request) {
			 vehicleService.deleteVehicle(id);
			 return "Deleted";
}

}
