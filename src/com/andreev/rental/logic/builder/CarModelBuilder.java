package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.CarModel;
import com.andreev.rental.model.EEntity;

public class CarModelBuilder extends EntityBuilder {

	private String name = "no name";
	private Brand brand;

	public CarModelBuilder(MessageManager message) {
		super(EEntity.CAR_MODEL, message);
		this.brand = (Brand) FACTORY.newEntity(EEntity.BRAND);
	}

	public CarModelBuilder(CarModel entity, MessageManager message) {
		super(entity, message);
		this.name = entity.getName();
		this.brand = entity.getBrand();
	}

	@Override
	public boolean build() {
		((CarModel) entity).setName(name);
		((CarModel) entity).setBrand(brand);
		return super.build();
	}

	public CarModelBuilder name(String name) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.CAR_MODEL_NAME);
		if (validator.isValid(name)) {
			this.name = name;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public CarModelBuilder brand(String brand) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(brand)) {
			this.brand.setId(Long.valueOf(brand));
		} else {
			addMessage(validator);
		}
		return this;
	}
}
