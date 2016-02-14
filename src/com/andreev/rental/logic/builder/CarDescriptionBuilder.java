package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.EEntity;

public class CarDescriptionBuilder extends EntityBuilder {

	private int price = 0;
	private int doors = 0;
	private int seats = 0;
	private int consumption = 0;
	private boolean isAirCondition = false;
	private boolean isAirBags = false;
	private boolean isAutomatic = false;
	private String description = "no description";
	private String imgUrl = "";
	private CarModel model;

	public CarDescriptionBuilder(MessageManager message) {
		super(EEntity.CAR_DESCRIPTION, message);
		this.model = (CarModel) FACTORY.newEntity(EEntity.CAR_MODEL);
	}

	public CarDescriptionBuilder(CarDescription entity, MessageManager message) {
		super(entity, message);
		this.price = entity.getPrice();
		this.doors = entity.getDoors();
		this.seats = entity.getSeats();
		this.consumption = entity.getConsumption();
		this.isAirCondition = entity.isAirCondition();
		this.isAirBags = entity.isAirBags();
		this.isAutomatic = entity.isAutomatic();
		this.description = entity.getDescription();
		this.model = entity.getModel();
		this.imgUrl = entity.getImgUrl();
	}

	@Override
	public boolean build() {
		((CarDescription) entity).setPrice(price);
		((CarDescription) entity).setDoors(doors);
		((CarDescription) entity).setSeats(seats);
		((CarDescription) entity).setConsumption(consumption);
		((CarDescription) entity).setAirCondition(isAirCondition);
		((CarDescription) entity).setAirBags(isAirBags);
		((CarDescription) entity).setAutomatic(isAutomatic);
		((CarDescription) entity).setDescription(description);
		((CarDescription) entity).setImgUrl(imgUrl);
		((CarDescription) entity).setModel(model);
		return super.build();
	}
	
	public CarDescriptionBuilder price(String price) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_PRICE);
		if (validator.isValid(price)) {
			this.price = Integer.valueOf(price);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarDescriptionBuilder doors(String doors) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_DOORS);
		if (validator.isValid(doors)) {
			this.doors = Integer.valueOf(doors);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarDescriptionBuilder seats(String seats) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_SEATS);
		if (validator.isValid(seats)) {
			this.seats = Integer.valueOf(seats);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarDescriptionBuilder consumption(String consumption) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_CONSUMPTION);
		if (validator.isValid(consumption)) {
			this.consumption = Integer.valueOf(consumption);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarDescriptionBuilder airCondition(String airCondition) {
		this.isAirCondition = Boolean.valueOf(airCondition);
		return this;
	}

	public CarDescriptionBuilder airBags(String airBags) {
		this.isAirBags = Boolean.valueOf(airBags);
		return this;
	}

	public CarDescriptionBuilder automatic(String automatic) {
		this.isAutomatic = Boolean.valueOf(automatic);
		return this;
	}

	public CarDescriptionBuilder description(String description) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_DESCRIPTION);
		if (validator.isValid(description)) {
			this.description = description;
		} else {
			addMessage(validator);
		}
		return this;
	}
	
	public CarDescriptionBuilder imgUrl(String imgUrl) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION_IMG);
		if (validator.isValid(imgUrl)) {
			this.imgUrl = imgUrl;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarDescriptionBuilder model(String model) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(model)) {
			this.model.setId(Long.valueOf(model));
		} else {
			addMessage(validator);
		}
		return this;
	}
}
