package com.andreev.rental.logic.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ValidatorFactory implements IValidatorFactory {
	
	private static class SingletonLoader {
		public static final ValidatorFactory INSTANCE = new ValidatorFactory();
	}
	
	private static final String RESOURCES_PATH="resources.regex";
	
	private static final String MSG_BRAND_NAME = "msg.regex.brand.name";
	private static final String MSG_CAR_DESCRIPTION = "msg.regex.car.description";
	private static final String MSG_CAR_DESCRIPTION_PRICE = "msg.regex.car_description.price";
	private static final String MSG_CAR_DESCRIPTION_CONSUMPTION = "msg.regex.car_description.consumption";
	private static final String MSG_CAR_DESCRIPTION_DESCRIPTION = "msg.regex.car_description.description";
	private static final String MSG_CAR_DESCRIPTION_DOORS = "msg.regex.car_description.doors";
	private static final String MSG_CAR_DESCRIPTION_SEATS = "msg.regex.car_description.seats";
	private static final String MSG_CAR_DESCRIPTION_IMG = "msg.regex.car_description.img";
	private static final String MSG_CAR_MODEL_NAME = "msg.regex.car_model.name";
	private static final String MSG_ID = "msg.regex.id";
	private static final String MSG_ORDER_FROM = "msg.regex.order.from";
	private static final String MSG_ORDER_INFO = "msg.regex.order.info";
	private static final String MSG_ORDER_STATUS = "msg.regex.order.status";
	private static final String MSG_ORDER_TO = "msg.regex.order.to";
	private static final String MSG_ORDER_TOTAL = "msg.regex.order.total";
	private static final String MSG_ORDER_DETAIL_DESCRIPTION = "msg.regex.order_detail.description";
	private static final String MSG_ORDER_DETAIL_PRICE = "msg.regex.order_detail.price";
	private static final String MSG_USER_ADDRESS = "msg.regex.user.address";
	private static final String MSG_USER_EMAIL = "msg.regex.user.email";
	private static final String MSG_USER_NAME = "msg.regex.user.name";
	private static final String MSG_USER_PASSPORT = "msg.regex.user.passport_number";
	private static final String MSG_USER_PASSWORD = "msg.regex.user.password";
	private static final String MSG_USER_PHONE = "msg.regex.user.phone";
	private static final String MSG_USER_SURNAME = "msg.regex.user.surname";
	
	private static final String PATTERN_BRAND_NAME = "brand.name";
	private static final String PATTERN_CAR_DESCRITION = "car.description"; 
	private static final String PATTERN_CAR_DESCRIPTION_DESCRIPTION = "car_description.description";
	private static final String PATTERN_CAR_MODEL_NAME = "car_model.name"; 
	private static final String PATTERN_ID = "id";  
	private static final String PATTERN_NUMBER = "number";
	private static final String PATTERN_ORDER_DATE = "order.date"; 
	private static final String PATTERN_ORDER_INFO = "order.info";
	private static final String PATTERN_ORDER_PRICE = "number";
	private static final String PATTERN_USER_ADDRESS = "user.address"; 
	private static final String PATTERN_USER_EMAIL = "user.email";
	private static final String PATTERN_USER_NAME = "user.name"; 
	private static final String PATTERN_USER_PASSPORT = "user.passport_number"; 
	private static final String PATTERN_USER_PASSWORD = "user.password";
	private static final String PATTERN_USER_PHONE = "user.phone";
	private static final String PATTERN_ORDER_STATUS = "order.status";
	private static final String PATTERN_URL = "url";
	
	private ResourceBundle resources;
	private Map<ERegexValidator, RegexValidator> map;

	private ValidatorFactory() {
		resources = ResourceBundle.getBundle(RESOURCES_PATH);
		map = new HashMap<ERegexValidator, RegexValidator>();
		initValidators();
	}
	
	public static IValidatorFactory getInstance() {
		return SingletonLoader.INSTANCE;
	}
	
	@Override
	public RegexValidator getValidator(ERegexValidator type) {
		if (!map.containsKey(type)) {
			throw new IllegalArgumentException("Incorrect type");
		}
		RegexValidator validator = map.get(type);
		return validator;
	};
	
	private void initValidators() {
		addValidator(ERegexValidator.ID, MSG_ID, PATTERN_ID);
		addValidator(ERegexValidator.BRAND_NAME, MSG_BRAND_NAME, PATTERN_BRAND_NAME);
		addValidator(ERegexValidator.CAR_DESCRIPTION, MSG_CAR_DESCRIPTION, PATTERN_CAR_DESCRITION);
		addValidator(ERegexValidator.CAR_DESCRIPTION_PRICE, MSG_CAR_DESCRIPTION_PRICE, PATTERN_NUMBER);
		addValidator(ERegexValidator.CAR_DESCRIPTION_CONSUMPTION, MSG_CAR_DESCRIPTION_CONSUMPTION, PATTERN_NUMBER);
		addValidator(ERegexValidator.CAR_DESCRIPTION_DESCRIPTION, MSG_CAR_DESCRIPTION_DESCRIPTION, PATTERN_CAR_DESCRIPTION_DESCRIPTION);
		addValidator(ERegexValidator.CAR_DESCRIPTION_DOORS, MSG_CAR_DESCRIPTION_DOORS, PATTERN_NUMBER);
		addValidator(ERegexValidator.CAR_DESCRIPTION_SEATS, MSG_CAR_DESCRIPTION_SEATS, PATTERN_NUMBER);
		addValidator(ERegexValidator.CAR_MODEL_NAME, MSG_CAR_MODEL_NAME, PATTERN_CAR_MODEL_NAME);
		addValidator(ERegexValidator.ORDER_FROM, MSG_ORDER_FROM, PATTERN_ORDER_DATE);
		addValidator(ERegexValidator.ORDER_INFO, MSG_ORDER_INFO, PATTERN_ORDER_INFO);
		addValidator(ERegexValidator.ORDER_STATUS, MSG_ORDER_STATUS, PATTERN_ORDER_STATUS);
		addValidator(ERegexValidator.ORDER_TO, MSG_ORDER_TO, PATTERN_ORDER_DATE);
		addValidator(ERegexValidator.ORDER_TOTAL, MSG_ORDER_TOTAL, PATTERN_ORDER_PRICE);
		addValidator(ERegexValidator.ORDER_DETAIL_DESCRIPTION, MSG_ORDER_DETAIL_DESCRIPTION, PATTERN_ORDER_INFO);
		addValidator(ERegexValidator.ORDER_DETAIL_PRICE, MSG_ORDER_DETAIL_PRICE, PATTERN_ORDER_PRICE);
		addValidator(ERegexValidator.USER_ADDRESS, MSG_USER_ADDRESS, PATTERN_USER_ADDRESS);
		addValidator(ERegexValidator.USER_EMAIL, MSG_USER_EMAIL, PATTERN_USER_EMAIL);
		addValidator(ERegexValidator.USER_NAME, MSG_USER_NAME, PATTERN_USER_NAME);
		addValidator(ERegexValidator.USER_PASSPORT, MSG_USER_PASSPORT, PATTERN_USER_PASSPORT);
		addValidator(ERegexValidator.USER_PASSWORD, MSG_USER_PASSWORD, PATTERN_USER_PASSWORD);
		addValidator(ERegexValidator.USER_PHONE, MSG_USER_PHONE, PATTERN_USER_PHONE);
		addValidator(ERegexValidator.USER_SURNAME, MSG_USER_SURNAME, PATTERN_USER_NAME);
		addValidator(ERegexValidator.CAR_DESCRIPTION_IMG, MSG_CAR_DESCRIPTION_IMG, PATTERN_URL);
	}
	
	private void addValidator(ERegexValidator key, String message, String pattern) {
		String regex = resources.getString(pattern);
		RegexValidator validator = new RegexValidator(regex, message);
		map.put(key, validator);
	}
}
