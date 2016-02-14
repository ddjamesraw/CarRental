<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="tag"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<br />
<div class="panel panel-menu text-center">
	<div class="panel-heading">
		<h3 class="panel-title">
			<fmt:message key="administration.menu" />
		</h3>
	</div>
	<div class="panel-body">
		<ul class="nav nav-pills nav-stacked">
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="userList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.user.list" />" />
				</form>
			</li>
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="orderList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.order.list" />" />
				</form>
			</li>
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="brandList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.brand.list" />" />
				</form>
			</li>
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="modelList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.model.list" />" />
				</form>
			</li>
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="descriptionList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.description.list" />" />
				</form>
			</li>
			<li>
				<form action="/CarRental/rental" method="POST">
					<input type="hidden" name="action" value="carList" /> <input
						type="submit" class="btn-link"
						value="<fmt:message key="administration.car.list" />" />
				</form>
			</li>
		</ul>
	</div>
</div>