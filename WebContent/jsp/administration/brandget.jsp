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
<title><fmt:message key="administration.brand" /></title>
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
				<div class="panel panel-default form">
					<div class="panel-heading">
						<fmt:message key="administration.brand" />
					</div>
					<div class="panel-body">
						<ul class="list-group">
							<li class="list-group-item"><fmt:message
									key="administration.id" /><span class="pull-right"> <c:out
										value="${brandById.id }" />
							</span></li>
							<li class="list-group-item"><fmt:message
									key="administration.brand.name" /><span class="pull-right">
									<c:out value="${brandById.name }" />
							</span></li>
						</ul>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-left"> <input type="hidden"
								name="action" value="brandToEdit" /> <input type="hidden"
								name="brandId" value="${brandById.id }" /> <input type="submit"
								value="<fmt:message key="administration.button.edit" />"
								class="btn-link" />
							</span>
						</form>
						<form action="/CarRental/rental" method="POST">
							<span class="pull-right"> <tag:tokenInput /> <input
								type="hidden" name="action" value="brandDelete" /> <input
								type="hidden" name="brandId" value="${brandById.id }" /> <input
								type="submit"
								value="<fmt:message key="administration.button.delete" />"
								class="btn-link" />
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