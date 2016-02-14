package com.andreev.rental.model;

public class EntityFactory implements IEntityFactory {
	
	private static class SingletonLoader{
		public final static IEntityFactory INSTANCE = new EntityFactory();
	}

	private EntityFactory() {
	}
	
	public static IEntityFactory getInstance() {
		return SingletonLoader.INSTANCE;
	}

	@Override
	public Entity newEntity(EEntity type) {
		switch(type) {
		case USER:
			return new User();
		case BRAND:
			return new Brand();
		case CAR_MODEL:
			return new CarModel();
		case CAR_DESCRIPTION:
			return new CarDescription();
		case CAR:
			return new Car();
		case ORDER:
			return new Order();
		case ORDER_DETAIL:
			return new OrderDetail();
		default:
			throw new IllegalArgumentException();
		}
	}
}
