<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="tag"%>
<tag:tokenGenerate />
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/CarRental/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/CarRental/css/site.css" />
<title><fmt:message key="administration.order.add" /></title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/jspf/header.jsp"></jsp:include>
		<div id="columns">
			<div class="column_1">
				<jsp:include page="/jspf/adminmenu.jsp" />
			</div>
			<div class="column_2">
				<tag:messages />
				<form action="/CarRental/rental" method="POST" class="form"
					style="width: initial;">
					<tag:tokenInput />
					<input type="hidden" name="action" value="orderAdd" />
					<div class="panel panel-default text-center">
						<div class="panel-heading">
							<fmt:message key="administration.order.add" />
						</div>
						<div class="panel-body">
							<div class="text-center">
								<fmt:message key="administration.order.status" />
								: <select name="status"
									class="btn btn-default btn-xs dropdown-toggle">
									<c:forEach var="type" items="${statusList }">
										<option value="${type.statusId}">
											<c:out value="${type.statusName }"></c:out>
										</option>
									</c:forEach>
								</select>
							</div>
							<br />
							<div class="text-center">
								<fmt:message key="administration.car" />
								: <select name="carId"
									class="btn btn-default btn-xs dropdown-toggle">
									<c:forEach var="car" items="${carList }">
										<option value="${car.id }">
											<c:out
												value="${car.carDescription.model.brand.name } ${car.carDescription.model.name }"></c:out>
										</option>
									</c:forEach>
								</select>
							</div>
							<br />
							<div class="text-center">
								<fmt:message key="administration.user" />
								: <select name="userId"
									class="btn btn-default btn-xs dropdown-toggle">
									<c:forEach var="user" items="${userList }">
										<option value="${user.id }">
											<c:out value="${user.email }"></c:out>
										</option>
									</c:forEach>
								</select>
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.order.from" /></span> <input type="date"
									name="from" class="form-control" />
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><fmt:message
										key="administration.order.to" /></span> <input type="date" name="to"
									class="form-control" />
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-info-sign"></span></span> <input
									type="text" name="info" class="form-control"
									placeholder="<fmt:message key="administration.order.info" />" />
							</div>
							<br /> <input type="submit" class="btn btn-m btn-danger"
								value="<fmt:message key="administration.button.add" />" />
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="hFooter"></div>
	</div>
	<jsp:include page="/jspf/footer.jsp"></jsp:include>

	<script src="/CarRental/js/jquerry.min.js" type="text/javascript"></script>
	<script src="/CarRental/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>