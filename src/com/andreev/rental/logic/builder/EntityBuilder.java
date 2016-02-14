package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.IValidatorFactory;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.logic.util.ValidatorFactory;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.EntityFactory;
import com.andreev.rental.model.IEntityFactory;

public abstract class EntityBuilder {

	protected static final IEntityFactory FACTORY = EntityFactory.getInstance();
	protected static final IValidatorFactory VALIDATOR_FACTORY = ValidatorFactory
			.getInstance();

	protected Entity entity;
	private boolean isValid;
	private MessageManager message;
	private long id = 0l;

	public EntityBuilder(EEntity model, MessageManager message) {
		entity = FACTORY.newEntity(model);
		init(entity, message);
	}

	public EntityBuilder(Entity entity, MessageManager message) {
		init(entity, message);
		this.id = entity.getId();
	}

	public boolean build() {
		entity.setId(id);
		return isValid;
	};

	public Entity getEntity() {
		return entity;
	}

	public EntityBuilder id(String id) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(id)) {
			this.id = Long.valueOf(id);
		} else {
			addMessage(validator);
		}
		return this;
	}

	protected void addMessage(RegexValidator validator) {
		isValid = false;
		message.addMessage(validator.getMessage());
	}

	private void init(Entity entity, MessageManager message) {
		this.message = message;
		this.entity = entity;
		this.isValid = true;
	}

}
