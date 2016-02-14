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
<title><fmt:message key="administration.model.edit" /></title>
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
				<form action="/CarRental/rental" method="POST"
					class="form form-207px">
					<tag:tokenInput />
					<input type="hidden" name="action" value="modelEdit" /> <input
						type="hidden" name="modelId" value="${modelById.id }" />
					<div class="panel panel-default text-center">
						<div class="panel-heading">
							<fmt:message key="administration.model.edit" />
						</div>
						<div class="panel-body">
							<div class="text-center">
								<fmt:message key="administration.brand" />
								: <select name="brandId"
									class="btn btn-default btn-xs dropdown-toggle">
									<c:forEach var="brand" items="${brandList }">
										<option value="${brand.id }"
											<c:if test="${brand.id == modelById.brand.id }">selected</c:if>>
											<c:out value="${brand.name }"></c:out>
										</option>
									</c:forEach>
								</select>
							</div>
							<br />
							<div class="input-group">
								<input type="text" name="name" class="form-control"
									value="${modelById.name }"
									placeholder="<fmt:message key="administration.model.name" />" />
							</div>
							<br /> <input type="submit" class="btn btn-m btn-danger"
								value="<fmt:message key="administration.button.edit" />" />
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