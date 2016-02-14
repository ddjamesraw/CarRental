package com.andreev.rental.model;

import java.io.Serializable;

public class Car extends Entity implements Serializable {
	
	private static final long serialVersionUID = 2998267783668737535L;
	
	private boolean isAvailable;
	private String description;
	private CarDescription carDescription;

	public Car() {
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CarDescription getCarDescription() {
		return carDescription;
	}

	public void setCarDescription(CarDescription carDescription) {
		this.carDescription = carDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((carDescription == null) ? 0 : carDescription.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isAvailable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (carDescription == null) {
			if (other.carDescription != null)
				return false;
		} else if (!carDescription.equals(other.carDescription))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [id=" + getId() + ", isAvailable=" + isAvailable + ", description="
				+ description + ", carDescription=" + carDescription + "]";
	}	

}
