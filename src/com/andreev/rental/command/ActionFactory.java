package com.andreev.rental.command;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.andreev.rental.command.administration.BrandAddAction;
import com.andreev.rental.command.administration.BrandDeleteAction;
import com.andreev.rental.command.administration.BrandEditAction;
import com.andreev.rental.command.administration.BrandGetAction;
import com.andreev.rental.command.administration.BrandListAction;
import com.andreev.rental.command.administration.BrandToAddAction;
import com.andreev.rental.command.administration.BrandToEditAction;
import com.andreev.rental.command.administration.CarAddAction;
import com.andreev.rental.command.administration.CarDeleteAction;
import com.andreev.rental.command.administration.CarEditAction;
import com.andreev.rental.command.administration.CarGetAction;
import com.andreev.rental.command.administration.CarListAction;
import com.andreev.rental.command.administration.CarToAddAction;
import com.andreev.rental.command.administration.CarToEditAction;
import com.andreev.rental.command.administration.DescriptionAddAction;
import com.andreev.rental.command.administration.DescriptionDeleteAction;
import com.andreev.rental.command.administration.DescriptionEditAction;
import com.andreev.rental.command.administration.DescriptionGetAction;
import com.andreev.rental.command.administration.DescriptionListAction;
import com.andreev.rental.command.administration.DescriptionToAddAction;
import com.andreev.rental.command.administration.DescriptionToEditAction;
import com.andreev.rental.command.administration.DetailAddAction;
import com.andreev.rental.command.administration.DetailDeleteAction;
import com.andreev.rental.command.administration.DetailToAddAction;
import com.andreev.rental.command.administration.ModelAddAction;
import com.andreev.rental.command.administration.ModelDeleteAction;
import com.andreev.rental.command.administration.ModelEditAction;
import com.andreev.rental.command.administration.ModelGetAction;
import com.andreev.rental.command.administration.ModelListAction;
import com.andreev.rental.command.administration.ModelToAddAction;
import com.andreev.rental.command.administration.ModelToEditAction;
import com.andreev.rental.command.administration.OrderAddAction;
import com.andreev.rental.command.administration.OrderDeleteAction;
import com.andreev.rental.command.administration.OrderEditAction;
import com.andreev.rental.command.administration.OrderGetAction;
import com.andreev.rental.command.administration.OrderListAction;
import com.andreev.rental.command.administration.OrderToAddAction;
import com.andreev.rental.command.administration.OrderToEditAction;
import com.andreev.rental.command.administration.UserAddAction;
import com.andreev.rental.command.administration.UserDeleteAction;
import com.andreev.rental.command.administration.UserEditAction;
import com.andreev.rental.command.administration.UserGetAction;
import com.andreev.rental.command.administration.UserListAction;
import com.andreev.rental.command.administration.UserToAddAction;
import com.andreev.rental.command.administration.UserToEditAction;
import com.andreev.rental.command.authentication.ChangePasswordAction;
import com.andreev.rental.command.authentication.EditPersonalDataAction;
import com.andreev.rental.command.authentication.LoginAction;
import com.andreev.rental.command.authentication.LogoutAction;
import com.andreev.rental.command.authentication.OrderDetailAction;
import com.andreev.rental.command.authentication.ProfileAction;
import com.andreev.rental.command.authentication.RegisterAction;
import com.andreev.rental.command.park.CritarionListAction;
import com.andreev.rental.command.park.OrderingAction;
import com.andreev.rental.command.park.ParkGetAction;
import com.andreev.rental.command.park.ParkListAction;
import com.andreev.rental.command.park.ToOrderingAction;
import com.andreev.rental.model.User;

public class ActionFactory implements IActionFactory {

	public static final String PARAM_ACTION = "action";
	public static final String PARAM_USER = "user";
	public static final String TO_PAGE_ACTION = "toPage";
	public static final String REGISTER_ACTION = "register";
	public static final String LOGIN_ACTION = "login";
	public static final String LOGOUT_ACTION = "logout";
	public static final String ERROR_ACTION = "error";
	public static final String EDIT_PROFILE_ACTION = "editProfile";
	public static final String CHANGE_PASSWORD_ACTION = "changePassword";
	public static final String USER_LIST_ACTION = "userList";
	public static final String BRAND_LIST_ACTION = "brandList";
	public static final String MODEL_LIST_ACTION = "modelList";
	public static final String DESCRIPTION_LIST_ACTION = "descriptionList";
	public static final String CAR_LIST_ACTION = "carList";
	public static final String ORDER_LIST_ACTION = "orderList";
	public static final String USER_GET_ACTION = "userGet";
	public static final String BRAND_GET_ACTION = "brandGet";
	public static final String MODEL_GET_ACTION = "modelGet";
	public static final String DESCRIPTION_GET_ACTION = "descriptionGet";
	public static final String CAR_GET_ACTION = "carGet";
	public static final String ORDER_GET_ACTION = "orderGet";
	public static final String BRAND_TO_ADD_ACTION = "brandToAdd";
	public static final String BRAND_ADD_ACTION = "brandAdd";
	public static final String BRAND_DELETE_ACTION = "brandDelete";
	public static final String BRAND_TO_EDIT_ACTION = "brandToEdit";
	public static final String BRAND_EDIT_ACTION = "brandEdit";
	public static final String USER_TO_ADD_ACTION = "userToAdd";
	public static final String USER_ADD_ACTION = "userAdd";
	public static final String USER_DELETE_ACTION = "userDelete";
	public static final String USER_TO_EDIT_ACTION = "userToEdit";
	public static final String USER_EDIT_ACTION = "userEdit";
	public static final String MODEL_TO_ADD_ACTION = "modelToAdd";
	public static final String MODEL_ADD_ACTION = "modelAdd";
	public static final String MODEL_DELETE_ACTION = "modelDelete";
	public static final String MODEL_TO_EDIT_ACTION = "modelToEdit";
	public static final String MODEL_EDIT_ACTION = "modelEdit";
	public static final String DESCRIPTION_TO_ADD_ACTION = "descriptionToAdd";
	public static final String DESCRIPTION_ADD_ACTION = "descriptionAdd";
	public static final String DESCRIPTION_DELETE_ACTION = "descriptionDelete";
	public static final String DESCRIPTION_TO_EDIT_ACTION = "descriptionToEdit";
	public static final String DESCRIPTION_EDIT_ACTION = "descriptionEdit";
	public static final String CAR_TO_ADD_ACTION = "carToAdd";
	public static final String CAR_ADD_ACTION = "carAdd";
	public static final String CAR_DELETE_ACTION = "carDelete";
	public static final String CAR_TO_EDIT_ACTION = "carToEdit";
	public static final String CAR_EDIT_ACTION = "carEdit";
	public static final String ORDER_TO_ADD_ACTION = "orderToAdd";
	public static final String ORDER_ADD_ACTION = "orderAdd";
	public static final String ORDER_DELETE_ACTION = "orderDelete";
	public static final String ORDER_TO_EDIT_ACTION = "orderToEdit";
	public static final String ORDER_EDIT_ACTION = "orderEdit";
	public static final String DETAIL_TO_ADD_ACTION = "detailToAdd";
	public static final String DETAIL_ADD_ACTION = "detailAdd";
	public static final String DETAIL_DELETE_ACTION = "detailDelete";
	public static final String PARK_LIST_ACTION = "parkList";
	public static final String PARK_GET_ACTION = "parkGet";
	public static final String TO_ORDERING_ACTION = "toOrdering";
	public static final String ORDERING_ACTION = "ordering";
	public static final String PROFILE_ACTION = "profile";
	public static final String ORDER_DETAIL_ACTION = "detail";
	public static final String CRITERION_LIST_ACTION = "criterionList";

	private static final Lock LOCK = new ReentrantLock();
	private static ActionFactory instance;

	private Map<String, AbstractAction> actionMap;

	private ActionFactory() {
		this.actionMap = new HashMap<String, AbstractAction>();
		initActions();
	}

	public static IActionFactory getInstance() {
		if (instance == null) {
			LOCK.lock();
			if (instance == null) {
				instance = new ActionFactory();
			}
			LOCK.unlock();
		}
		return instance;
	}

	@Override
	public AbstractAction getAction(HttpServletRequest request) {
		String actionString = request.getParameter(PARAM_ACTION);
		AbstractAction action = getAction(actionString);
		User user = (User) request.getSession().getAttribute(PARAM_USER);
		if (!cheackPermission(user, action.getStatus())) {
			action = actionMap.get(ERROR_ACTION);
		}
		return action;
	}

	private AbstractAction getAction(String key) {
		AbstractAction action = null;
		if (key == null) {
			action = actionMap.get(TO_PAGE_ACTION);
		} else {
			if (actionMap.containsKey(key)) {
				action = actionMap.get(key);
			} else {
				action = actionMap.get(ERROR_ACTION);
				return action;
			}
		}
		return action;
	}
	
	private boolean cheackPermission(User user, EUserStatus status) {
		switch (status) {
		case ADMIN:
			if(user != null && user.isAdmin()){
				return true;
			}
			break;
		case REGISTERED:
			if(user != null) {
				return true;
			}
			break;
		case ALL:
			return true;			
		}
		return false;
	}

	private void initActions() {
		actionMap.put(TO_PAGE_ACTION, new ToPageAction());
		actionMap.put(ERROR_ACTION, new ErrorAction());
		initAuthActions();
		initAdminActions();
		initParkActions();
	}

	private void initAuthActions() {
		actionMap.put(REGISTER_ACTION, new RegisterAction());
		actionMap.put(LOGIN_ACTION, new LoginAction());
		actionMap.put(LOGOUT_ACTION, new LogoutAction());
		actionMap.put(EDIT_PROFILE_ACTION, new EditPersonalDataAction());
		actionMap.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction());
		actionMap.put(PROFILE_ACTION, new ProfileAction());
		actionMap.put(ORDER_DETAIL_ACTION, new OrderDetailAction());
	}

	private void initAdminActions() {
		initAdminUserActions();
		initAdminBrandActions();
		initAdminModelActions();
		initAdminDescriptionActions();
		initAdminCarActions();
		initAdminOrderActions();
		initAdminDetailActions();
	}

	private void initAdminUserActions() {
		actionMap.put(USER_LIST_ACTION, new UserListAction());
		actionMap.put(USER_GET_ACTION, new UserGetAction());
		actionMap.put(USER_TO_ADD_ACTION, new UserToAddAction());
		actionMap.put(USER_ADD_ACTION, new UserAddAction());
		actionMap.put(USER_DELETE_ACTION, new UserDeleteAction());
		actionMap.put(USER_TO_EDIT_ACTION, new UserToEditAction());
		actionMap.put(USER_EDIT_ACTION, new UserEditAction());
	}

	private void initAdminBrandActions() {
		actionMap.put(BRAND_LIST_ACTION, new BrandListAction());
		actionMap.put(BRAND_GET_ACTION, new BrandGetAction());
		actionMap.put(BRAND_TO_ADD_ACTION, new BrandToAddAction());
		actionMap.put(BRAND_ADD_ACTION, new BrandAddAction());
		actionMap.put(BRAND_DELETE_ACTION, new BrandDeleteAction());
		actionMap.put(BRAND_TO_EDIT_ACTION, new BrandToEditAction());
		actionMap.put(BRAND_EDIT_ACTION, new BrandEditAction());
	}

	private void initAdminModelActions() {
		actionMap.put(MODEL_LIST_ACTION, new ModelListAction());
		actionMap.put(MODEL_GET_ACTION, new ModelGetAction());
		actionMap.put(MODEL_TO_ADD_ACTION, new ModelToAddAction());
		actionMap.put(MODEL_ADD_ACTION, new ModelAddAction());
		actionMap.put(MODEL_DELETE_ACTION, new ModelDeleteAction());
		actionMap.put(MODEL_TO_EDIT_ACTION, new ModelToEditAction());
		actionMap.put(MODEL_EDIT_ACTION, new ModelEditAction());
	}

	private void initAdminDescriptionActions() {
		actionMap.put(DESCRIPTION_LIST_ACTION, new DescriptionListAction());
		actionMap.put(DESCRIPTION_GET_ACTION, new DescriptionGetAction());
		actionMap.put(DESCRIPTION_TO_ADD_ACTION, new DescriptionToAddAction());
		actionMap.put(DESCRIPTION_ADD_ACTION, new DescriptionAddAction());
		actionMap.put(DESCRIPTION_DELETE_ACTION, new DescriptionDeleteAction());
		actionMap.put(DESCRIPTION_TO_EDIT_ACTION, new DescriptionToEditAction());
		actionMap.put(DESCRIPTION_EDIT_ACTION, new DescriptionEditAction());
	}

	private void initAdminCarActions() {
		actionMap.put(CAR_LIST_ACTION, new CarListAction());
		actionMap.put(CAR_GET_ACTION, new CarGetAction());
		actionMap.put(CAR_TO_ADD_ACTION, new CarToAddAction());
		actionMap.put(CAR_ADD_ACTION, new CarAddAction());
		actionMap.put(CAR_DELETE_ACTION, new CarDeleteAction());
		actionMap.put(CAR_TO_EDIT_ACTION, new CarToEditAction());
		actionMap.put(CAR_EDIT_ACTION, new CarEditAction());
	}

	private void initAdminOrderActions() {
		actionMap.put(ORDER_LIST_ACTION, new OrderListAction());
		actionMap.put(ORDER_GET_ACTION, new OrderGetAction());
		actionMap.put(ORDER_TO_ADD_ACTION, new OrderToAddAction());
		actionMap.put(ORDER_ADD_ACTION, new OrderAddAction());
		actionMap.put(ORDER_DELETE_ACTION, new OrderDeleteAction());
		actionMap.put(ORDER_TO_EDIT_ACTION, new OrderToEditAction());
		actionMap.put(ORDER_EDIT_ACTION, new OrderEditAction());
	}
	
	private void initAdminDetailActions() {
		actionMap.put(DETAIL_TO_ADD_ACTION, new DetailToAddAction());
		actionMap.put(DETAIL_ADD_ACTION, new DetailAddAction());
		actionMap.put(DETAIL_DELETE_ACTION, new DetailDeleteAction());
	}
	
	private void initParkActions() {
		actionMap.put(PARK_LIST_ACTION, new ParkListAction());
		actionMap.put(PARK_GET_ACTION, new ParkGetAction());
		actionMap.put(TO_ORDERING_ACTION, new ToOrderingAction());
		actionMap.put(ORDERING_ACTION, new OrderingAction());
		actionMap.put(CRITERION_LIST_ACTION, new CritarionListAction());
	}

}
