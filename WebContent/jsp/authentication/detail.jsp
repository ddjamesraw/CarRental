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
<title><fmt:message key="main.title" /></title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/jspf/header.jsp"></jsp:include>
		<div id="columns">
			<div class="column_1">
				<c:if test="${not empty user}">
					<c:if test="${user.admin}">
						<jsp:include page="/jspf/adminmenu.jsp" />
					</c:if>
				</c:if>
			</div>
			<div class="column_2">
				<tag:messages />
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<fmt:message key="authentication.profile.order" />
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.status" /><span
								class="pull-right"><c:out
										value="${orderById.status.statusName }" /></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.from" /><span
								class="pull-right"><fmt:formatDate
										value="${orderById.from }" /></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.to" /><span
								class="pull-right"><fmt:formatDate
										value="${orderById.to }" /></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.total" /><span
								class="pull-right"><fmt:formatNumber
										value="${orderById.total }" type="currency" currencySymbol="$" /></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.info" /><span
								class="pull-right"><c:out value="${orderById.info }" /></span></li>
							<li class="list-group-item"><fmt:message
									key="authentication.profile.order.car" /><span
								class="pull-right"><a
									href="/CarRental/rental?action=parkGet&descriptionId=${orderById.car.carDescription.id }">
										<c:out
											value="${orderById.car.carDescription.model.brand.name } ${orderById.car.carDescription.model.name }" />
								</a></span></li>
						</ul>
						<c:if test="${not empty requestScope.detailList }">
							<div class="panel panel-default">
								<table class="table">
									<thead>
										<tr>
											<th><fmt:message
													key="authentication.profile.order.price" /></th>
											<th><fmt:message
													key="authentication.profile.order.description" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="detail" items="${requestScope.detailList }">
											<tr>
												<td><fmt:formatNumber value="${detail.price }"
														type="currency" currencySymbol="$" /></td>
												<td><c:out value="${detail.description }" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
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