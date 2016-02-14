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
<title><fmt:message key="administration.order" /></title>
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
						<fmt:message key="administration.order" />
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<li class="list-group-item"><fmt:message
									key="administration.id" /><span class="pull-right"><c:out
										value="${orderById.id }" /> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.order.status" /><span class="pull-right"><c:out
										value="${orderById.status.statusName }" /> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.order.from" /><span class="pull-right"><fmt:formatDate
										value="${orderById.from }" /> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.order.to" /><span class="pull-right"><fmt:formatDate
										value="${orderById.to }" /> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.order.total" /><span class="pull-right"><fmt:formatNumber
										value="${orderById.total }" type="currency" currencySymbol="$" />
							</span></li>
							<li class="list-group-item"><fmt:message
									key="administration.order.info" /><span class="pull-right"><c:out
										value="${orderById.info }" /> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.user" /><span class="pull-right"><a
									href="/CarRental/rental?action=userGet&userId=${orderById.user.id }">
										<c:out value="${orderById.user.email }" />
								</a> </span></li>
							<li class="list-group-item"><fmt:message
									key="administration.car" /><span class="pull-right"><a
									href="/CarRental/rental?action=carGet&carId=${orderById.car.id }">
										<c:out
											value="${orderById.car.carDescription.model.name } ${orderById.car.carDescription.model.brand.name }" />
								</a> </span></li>
						</ul>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-left"> <input type="hidden"
								name="action" value="orderToEdit" /> <input type="hidden"
								name="orderId" value="${orderById.id }" /> <input type="submit"
								class="btn-link"
								value="<fmt:message key="administration.button.edit" />" />
							</span>
						</form>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-right"> <tag:tokenInput /> <input
								type="hidden" name="action" value="orderDelete" /> <input
								type="hidden" name="orderId" value="${orderById.id }" /> <input
								type="submit" class="btn-link"
								value="<fmt:message key="administration.button.delete" />" />
							</span>
						</form>
						<br /> <br />
						<div class="panel panel-default">
							<div class="panel-heading text-center">
								<fmt:message key="administration.detail" />
							</div>
							<div class="panel-body">
								<table class="table">
									<thead>
										<tr>
											<th><fmt:message key="administration.id" /></th>
											<th><fmt:message key="administration.detail.price" /></th>
											<th><fmt:message key="administration.detail.description" /></th>
											<th>
												<form action="/CarRental/rental" method="POST">
													<input type="hidden" name="action" value="detailToAdd" />
													<input type="hidden" name="orderId"
														value="${orderById.id }" /> <input type="submit"
														class="btn-link"
														value="<fmt:message key="administration.button.add" />" />
												</form>
											</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${not empty requestScope.detailList }">
											<c:forEach var="detail" items="${requestScope.detailList }">
												<tr>
													<td><c:out value="${detail.id }" /></td>
													<td><fmt:formatNumber value="${detail.price }"
															type="currency" currencySymbol="$" /></td>
													<td><c:out value="${detail.description }" /></td>
													<td>
														<form action="/CarRental/rental" method="POST">
															<tag:tokenInput />
															<input type="hidden" name="action" value="detailDelete" />
															<input type="hidden" name="detailId"
																value="${detail.id }" /> <input type="submit"
																class="btn-link"
																value="<fmt:message key="administration.button.delete" />" />
														</form>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
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