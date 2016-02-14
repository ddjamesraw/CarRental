package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.OrderDetail;

public class OrderDetailBuilder extends EntityBuilder {

	private long orderId = 0l;
	private int price = 0;
	private String description = "no description";

	public OrderDetailBuilder(MessageManager message) {
		super(EEntity.ORDER_DETAIL, message);
	}

	public OrderDetailBuilder(OrderDetail entity, MessageManager message) {
		super(entity, message);
		this.orderId = entity.getOrderId();
		this.price = entity.getPrice();
		this.description = entity.getDescription();
	}

	@Override
	public boolean build() {
		((OrderDetail) entity).setOrderId(orderId);
		((OrderDetail) entity).setPrice(price);
		((OrderDetail) entity).setDescription(description);
		return super.build();
	}

	public OrderDetailBuilder orderId(String orderId) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(orderId)) {
			this.orderId = Long.valueOf(orderId);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderDetailBuilder price(String price) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_DETAIL_PRICE);
		if (validator.isValid(price)) {
			this.price = Integer.valueOf(price);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderDetailBuilder description(String description) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_DETAIL_DESCRIPTION);
		if (validator.isValid(description)) {
			this.description = description;
		} else {
			addMessage(validator);
		}
		return this;
	}
}
