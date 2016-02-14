package com.andreev.rental.database.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.andreev.rental.model.EEntity;
import com.andreev.rental.model.Entity;
import com.andreev.rental.model.OrderDetail;

public class ExtractOrderDetail extends ExtractBehavior {
	
	private static final int COUNT = 4;

	public ExtractOrderDetail() {
	}

	@Override
	public Entity build(ResultSet resultSet, int position) throws SQLException {
		OrderDetail orderDetail = (OrderDetail) FACTORY
				.newEntity(EEntity.ORDER_DETAIL);
		orderDetail.setId(resultSet.getLong(position++));
		orderDetail.setOrderId(resultSet.getLong(position++));
		orderDetail.setPrice(resultSet.getInt(position++));
		orderDetail.setDescription(resultSet.getString(position++));
		return orderDetail;
	}

	@Override
	public int getCount() {
		return COUNT;
	}

}
