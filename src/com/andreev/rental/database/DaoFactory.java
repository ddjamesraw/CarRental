package com.andreev.rental.database;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.andreev.rental.database.behavior.ExtractBehavior;
import com.andreev.rental.database.behavior.ExtractBrand;
import com.andreev.rental.database.behavior.ExtractCar;
import com.andreev.rental.database.behavior.ExtractCarDescription;
import com.andreev.rental.database.behavior.ExtractCarModel;
import com.andreev.rental.database.behavior.ExtractOrder;
import com.andreev.rental.database.behavior.ExtractOrderDetail;
import com.andreev.rental.database.behavior.ExtractUser;
import com.andreev.rental.database.dao.BrandDao;
import com.andreev.rental.database.dao.CarDao;
import com.andreev.rental.database.dao.DescriptionDao;
import com.andreev.rental.database.dao.DetailDao;
import com.andreev.rental.database.dao.IBrandDao;
import com.andreev.rental.database.dao.ICarDao;
import com.andreev.rental.database.dao.IDescriptionDao;
import com.andreev.rental.database.dao.IDetailDao;
import com.andreev.rental.database.dao.IModelDao;
import com.andreev.rental.database.dao.IOrderDao;
import com.andreev.rental.database.dao.IUserDao;
import com.andreev.rental.database.dao.ModelDao;
import com.andreev.rental.database.dao.OrderDao;
import com.andreev.rental.database.dao.UserDao;
import com.andreev.rental.database.pool.ConnectionPool;
import com.andreev.rental.database.pool.IConnectionPool;

public class DaoFactory implements IDaoFactory {

	private static final Logger LOG = Logger.getLogger(DaoFactory.class);
	private static final Lock LOCK = new ReentrantLock();
	private static volatile DaoFactory instance;

	private IConnectionPool connectionPool;	
	private IUserDao userDao;
	private IBrandDao brandDao;
	private IModelDao carModelDao;
	private IDescriptionDao carDescriptionDao;
	private ICarDao carDao;
	private IOrderDao orderDao;
	private IDetailDao orderDetailDao;

	private DaoFactory() throws DatabaseException {
		init();
	}

	public static IDaoFactory getInstance() throws DatabaseException {
		if (instance == null) {
			try {
				LOCK.lock();
				if (instance == null) {
					instance = new DaoFactory();
					LOG.info("Dao factory has been initialized");
				}
			} finally {
				LOCK.unlock();
			}
		}
		return instance;
	}

	@Override
	public IUserDao getUserDao() {
		return this.userDao;
	}

	@Override
	public IBrandDao getBrandDao() {
		return this.brandDao;
	}

	@Override
	public IModelDao getCarModelDao() {
		return this.carModelDao;
	}

	@Override
	public IDescriptionDao getCarDescriptionDao() {
		return this.carDescriptionDao;
	}

	@Override
	public ICarDao getCarDao() {
		return this.carDao;
	}

	@Override
	public IOrderDao getOrderDao() {
		return this.orderDao;
	}

	@Override
	public IDetailDao getOrderDetailDao() {
		return this.orderDetailDao;
	}

	@Override
	public void dispose() {
		if (instance != null) {
			try {
				LOCK.lock();
				if (instance != null) {
					this.connectionPool.dispose();
					instance = null;
					LOG.info("Dao factory was disposed");
				}
			} finally {
				LOCK.unlock();
			}
		}
	}

	private void init() throws DatabaseException {
		this.connectionPool = ConnectionPool.getInstance();
		initUserDao();
		initBrandDao();
		initCarModelDao();
		initCarDescriptionDao();
		initCarDao();
		initOrderDao();
		initOrderDetailDao();
	}

	private void initUserDao() {
		ExtractBehavior extract = new ExtractUser();
		this.userDao = new UserDao(connectionPool, extract);
	}

	private void initBrandDao() {
		ExtractBehavior extract = new ExtractBrand();
		this.brandDao = new BrandDao(connectionPool, extract);
	}

	private void initCarModelDao() {
		ExtractBehavior extract = new ExtractCarModel();
		this.carModelDao = new ModelDao(connectionPool, extract);
	}

	private void initCarDescriptionDao() {
		ExtractBehavior extract = new ExtractCarDescription();
		this.carDescriptionDao = new DescriptionDao(connectionPool,
				extract);
	}

	private void initCarDao() {
		ExtractBehavior extract = new ExtractCar();
		this.carDao = new CarDao(connectionPool, extract);
	}

	private void initOrderDao() {
		ExtractBehavior extract = new ExtractOrder();
		this.orderDao = new OrderDao(connectionPool, extract);
	}

	private void initOrderDetailDao() {
		ExtractBehavior extract = new ExtractOrderDetail();
		this.orderDetailDao = new DetailDao(connectionPool, extract);
	}

}
