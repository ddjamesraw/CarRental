package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.CarDescription;
import com.andreev.rental.model.EEntity;

public class CarBuilder extends EntityBuilder {

	private boolean isAvailable = false;
	private String description = "no description";
	private CarDescription carDescription;

	public CarBuilder(MessageManager message) {
		super(EEntity.CAR, message);
		this.carDescription = (CarDescription) FACTORY
				.newEntity(EEntity.CAR_DESCRIPTION);
	}

	public CarBuilder(Car entity, MessageManager message) {
		super(entity, message);
		this.isAvailable = entity.isAvailable();
		this.description = entity.getDescription();
		this.carDescription = entity.getCarDescription();
	}

	@Override
	public boolean build() {
		((Car) entity).setAvailable(isAvailable);
		((Car) entity).setDescription(description);
		((Car) entity).setCarDescription(carDescription);
		return super.build();
	}

	public CarBuilder available(String available) {
		this.isAvailable = Boolean.valueOf(available);
		return this;
	}

	public CarBuilder description(String description) {
		if(description == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_DESCRIPTION);
		if (validator.isValid(description)) {
			this.description = description;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarBuilder carDescription(String carDescription) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ID);
		if (validator.isValid(carDescription)) {
			this.carDescription.setId(Long.valueOf(carDescription));
		} else {
			addMessage(validator);
		}
		return this;
	}
}
