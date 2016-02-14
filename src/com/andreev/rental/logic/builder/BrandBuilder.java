package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.Brand;
import com.andreev.rental.model.EEntity;

public class BrandBuilder extends EntityBuilder {

	private String name = "no name";

	public BrandBuilder(MessageManager message) {
		super(EEntity.BRAND, message);
	}

	public BrandBuilder(Brand entity, MessageManager message) {
		super(entity, message);
		this.name = entity.getName();
	}

	@Override
	public boolean build() {
		((Brand) entity).setName(name);
		return super.build();
	}

	public EntityBuilder name(String name) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.BRAND_NAME);
		if (validator.isValid(name)) {
			this.name = name;
		} else {
			addMessage(validator);
		}
		return this;
	}

}
