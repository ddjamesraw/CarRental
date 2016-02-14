package com.andreev.rental.database;

import com.andreev.rental.database.dao.IBrandDao;
import com.andreev.rental.database.dao.ICarDao;
import com.andreev.rental.database.dao.IDescriptionDao;
import com.andreev.rental.database.dao.IModelDao;
import com.andreev.rental.database.dao.IOrderDao;
import com.andreev.rental.database.dao.IDetailDao;
import com.andreev.rental.database.dao.IUserDao;

public interface IDaoFactory {

	public IUserDao getUserDao();

	public IBrandDao getBrandDao();

	public IModelDao getCarModelDao();

	public IDescriptionDao getCarDescriptionDao();

	public ICarDao getCarDao();

	public IOrderDao getOrderDao();

	public IDetailDao getOrderDetailDao();

	public void dispose();

}
