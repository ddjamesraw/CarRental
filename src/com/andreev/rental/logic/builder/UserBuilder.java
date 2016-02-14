package com.andreev.rental.logic.builder;

import com.andreev.rental.logic.util.ERegexValidator;
import com.andreev.rental.logic.util.MessageManager;
import com.andreev.rental.logic.util.PasswordEncoder;
import com.andreev.rental.logic.util.RegexValidator;
import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.User;

public class UserBuilder extends EntityBuilder {

	private final static PasswordEncoder ENCODER = new PasswordEncoder();

	private String email;
	private int password;
	private boolean isAdmin;
	private String name;
	private String surname;
	private String passportNumber;
	private String address;
	private String phone;

	public UserBuilder(MessageManager message) {
		super(EEntity.USER, message);
	}

	public UserBuilder(User entity, MessageManager message) {
		super(entity, message);
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		this.isAdmin = entity.isAdmin();
		this.name = entity.getName();
		this.surname = entity.getSurname();
		this.passportNumber = entity.getPassportNumber();
		this.address = entity.getAddress();
		this.phone = entity.getPhone();
	}

	@Override
	public boolean build() {
		((User) entity).setEmail(email);
		((User) entity).setPassword(password);
		((User) entity).setAdmin(isAdmin);
		((User) entity).setName(name);
		((User) entity).setSurname(surname);
		((User) entity).setPassportNumber(passportNumber);
		((User) entity).setAddress(address);
		((User) entity).setPhone(phone);
		return super.build();
	}

	public UserBuilder email(String email) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_EMAIL);
		if (validator.isValid(email)) {
			this.email = email;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder password(String password) {
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_PASSWORD);
		if (validator.isValid(password)) {
			this.password = encodePassword(password);
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder admin(String admin) {
		this.isAdmin = Boolean.valueOf(admin);
		return this;
	}

	public UserBuilder name(String name) {
		if (name == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_NAME);
		if (validator.isValid(name)) {
			this.name = name;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder surname(String surname) {
		if (surname == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_SURNAME);
		if (validator.isValid(surname)) {
			this.surname = surname;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder passportNumber(String passportNumber) {
		if (passportNumber == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_PASSPORT);
		if (validator.isValid(passportNumber)) {
			this.passportNumber = passportNumber;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder address(String address) {
		if (address == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_ADDRESS);
		if (validator.isValid(address)) {
			this.address = address;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public UserBuilder phone(String phone) {
		if (phone == null) {
			return this;
		}
		RegexValidator validator = VALIDATOR_FACTORY
				.getValidator(ERegexValidator.USER_PHONE);
		if (validator.isValid(phone)) {
			this.phone = phone;
		} else {
			addMessage(validator);
		}
		return this;
	}

	public int encodePassword(String password) {
		return ENCODER.encode(password);
	}
}
