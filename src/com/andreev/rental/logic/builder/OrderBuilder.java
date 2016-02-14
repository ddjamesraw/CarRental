package com.andreev.rental.logic.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.Car;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.EOrderStatus;
import com.andreev.rental.model.Order;
import com.andreev.rental.model.User;

public class OrderBuilder extends EntityBuilder {

	private static final String DATA_PATTERN = "yyyy-MM-dd";

	private transient EOrderStatus status;
	private Date from;
	private Date to;
	private int total = 0;
	private String info = "no info";
	private Car car;
	private User user;

	public OrderBuilder(MessageManager message) {
		super(EEntity.ORDER, message);
		this.status = EOrderStatus.AWAITS;
		this.from = new Date();
		this.to = new Date();
		this.car = (Car) FACTORY.newEntity(EEntity.CAR);
		this.user = (User) FACTORY.newEntity(EEntity.USER);
	}

	public OrderBuilder(Order entity, MessageManager message) {
		super(entity, message);
		this.status = entity.getStatus();
		this.from = entity.getFrom();
		this.to = entity.getTo();
		this.total = entity.getTotal();
		this.info = entity.getInfo();
		this.car = entity.getCar();
		this.user = entity.getUser();
	}

	@Override
	public boolean build() {
		((Order) entity).setStatus(status);
		((Order) entity).setFrom(from);
		((Order) entity).setTo(to);
		((Order) entity).setTotal(total);
		((Order) entity).setInfo(info);
		((Order) entity).setCar(car);
		((Order) entity).setUser(user);
		return super.build();
	}

	public OrderBuilder status(String status) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_STATUS);
		if (validator.isValid(status)) {
			this.status = EOrderStatus.getStatus(Integer.valueOf(status));
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder from(String from) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_FROM);
		if (validator.isValid(from)) {
			SimpleDateFormat format = new SimpleDateFormat(DATA_PATTERN);
			try {
				this.from = format.parse(from);
			} catch (ParseException e) {
			}
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder to(String to) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_TO);
		if (validator.isValid(to)) {
			SimpleDateFormat format = new SimpleDateFormat(DATA_PATTERN);
			try {
				this.to = format.parse(to);
			} catch (ParseException e) {
			}
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder total(String total) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_TOTAL);
		if (validator.isValid(total)) {
			this.total = Integer.valueOf(total);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder info(String info) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.ORDER_INFO);
		if (validator.isValid(info)) {
			this.info = info;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder car(String car) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(car)) {
			this.car.setId(Long.valueOf(car));
		} else {
			addMessage(validator);
		}
		return this;
	}

	public OrderBuilder user(String user) {
		RegexValidator validator = VALIDATOR_FACTORY.getValidator(ERegexValidator.ID);
		if (validator.isValid(user)) {
			this.user.setId(Long.valueOf(user));
		} else {
			addMessage(validator);
		}
		return this;
	}
}
