package com.andreev.rental.model;

import java.io.Serializable;


public class CarDescription extends Entity implements Serializable {
	
	private static final long serialVersionUID = -6386560326677382101L;
	
	private int price;
	private int doors;
	private int seats;
	private int consumption;
	private boolean isAirCondition;
	private boolean isAirBags;
	private boolean isAutomatic;
	private String description;
	private String imgUrl;
	private CarModel model;

	public CarDescription() {
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDoors() {
		return doors;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getConsumption() {
		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	public boolean isAirCondition() {
		return isAirCondition;
	}

	public void setAirCondition(boolean isAirCondition) {
		this.isAirCondition = isAirCondition;
	}

	public boolean isAirBags() {
		return isAirBags;
	}

	public void setAirBags(boolean isAirBags) {
		this.isAirBags = isAirBags;
	}

	public boolean isAutomatic() {
		return isAutomatic;
	}

	public void setAutomatic(boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public CarModel getModel() {
		return model;
	}

	public void setModel(CarModel model) {
		this.model = model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + consumption;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + doors;
		result = prime * result + ((imgUrl == null) ? 0 : imgUrl.hashCode());
		result = prime * result + (isAirBags ? 1231 : 1237);
		result = prime * result + (isAirCondition ? 1231 : 1237);
		result = prime * result + (isAutomatic ? 1231 : 1237);
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + price;
		result = prime * result + seats;
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
		CarDescription other = (CarDescription) obj;
		if (consumption != other.consumption)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (doors != other.doors)
			return false;
		if (imgUrl == null) {
			if (other.imgUrl != null)
				return false;
		} else if (!imgUrl.equals(other.imgUrl))
			return false;
		if (isAirBags != other.isAirBags)
			return false;
		if (isAirCondition != other.isAirCondition)
			return false;
		if (isAutomatic != other.isAutomatic)
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (price != other.price)
			return false;
		if (seats != other.seats)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarDescription [id=" + getId() + ", price=" + price + ", doors=" + doors
				+ ", seats=" + seats + ", consumption=" + consumption
				+ ", isAirCondition=" + isAirCondition + ", isAirBags="
				+ isAirBags + ", isAutomatic=" + isAutomatic + ", description="
				+ description + ", imgUrl=" + imgUrl + ", model=" + model + "]";
	}

}
