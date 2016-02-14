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
<title><fmt:message key="administration.car" /></title>
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
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<fmt:message key="administration.car" />
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<li class="list-group-item"><fmt:message
									key="administration.id" /> <span class="pull-right"> <c:out
										value="${carById.id }" />
							</span></li>
							<li class="list-group-item"><fmt:message
									key="administration.car.available" /> <span class="pull-right">
									<input type="checkbox" disabled
									<c:if test="${carById.available }">checked</c:if> />
							</span></li>
							<li class="list-group-item"><fmt:message
									key="administration.description" /> <span class="pull-right">
									<a
									href="/CarRental/rental?action=descriptionGet&descriptionId=${carById.carDescription.id }">
										<c:out
											value="${carById.carDescription.model.brand.name } ${carById.carDescription.model.name }" />
								</a>
							</span></li>
							<li class="list-group-item"><fmt:message
									key="administration.car.info" /> <span class="pull-right">
									<c:out value="${carById.description }" />
							</span></li>
						</ul>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-left"> <input type="hidden"
								name="action" value="carToEdit" /> <input type="hidden"
								name="carId" value="${carById.id }" /> <input type="submit"
								class="btn-link"
								value="<fmt:message key="administration.button.edit" />" />
							</span>
						</form>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-right"> <tag:tokenInput /> <input
								type="hidden" name="action" value="carDelete" /> <input
								type="hidden" name="carId" value="${carById.id }" /> <input
								type="submit" class="btn-link"
								value="<fmt:message key="administration.button.delete" />" />
							</span>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="hFooter"></div>
	</div>
	<jsp:include page="/jspf/footer.jsp"></jsp:include>

	<script src="/CarRental/js/jquerry.min.js" type="text/javascript"></script>
	<script src="/CarRental/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>