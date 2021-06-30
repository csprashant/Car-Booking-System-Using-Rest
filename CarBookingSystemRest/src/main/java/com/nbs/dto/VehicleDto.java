package com.nbs.dto;
import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class VehicleDto implements Serializable {
	private String id;
	@NotBlank(message="*Make sure you have not entered Vehicle Name")
	private String vName;
	@NotBlank(message="*Make sure you have not entered Vehicle color")
	private String vColor;
	@NotBlank(message="*Make sure you have not entered Vehicle number")
	private String vNumber;
	private String created;
	private String updated;
	
	public VehicleDto(String id, String vName, String vColor, String vNumber, String created, String updated) {
		super();
		this.id = id;
		this.vName = vName;
		this.vColor = vColor;
		this.vNumber = vNumber;
		this.created = created;
		this.updated = updated;
	}

	public VehicleDto() {
		 super();
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvColor() {
		return vColor;
	}

	public void setvColor(String vColor) {
		this.vColor = vColor;
	}

	public String getvNumber() {
		return vNumber;
	}

	public void setvNumber(String vNumber) {
		this.vNumber = vNumber;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "VehicleDto [id=" + id + ", vName=" + vName + ", vColor=" + vColor + ", vNumber=" + vNumber
				+ ", created=" + created + ", updated=" + updated + "]";
	}


}
