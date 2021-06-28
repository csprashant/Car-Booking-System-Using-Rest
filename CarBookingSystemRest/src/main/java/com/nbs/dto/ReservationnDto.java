package com.nbs.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationnDto {
	
	private String id;
	private String userId;
	private String userName;
	private String fromDate;
	private String toDate;
	private String vehicleId;
	private String vName;
	private String vNumber;
	private String status;
	private String created;
	private String updated;
}

