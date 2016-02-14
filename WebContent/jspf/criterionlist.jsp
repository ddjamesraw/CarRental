<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.i18n.strings" />
<c:if test="${not empty requestScope.criterionList }">
	<br />
	<div class="panel panel-menu text-center">
		<div class="panel-heading">
			<h3 class="panel-title text-center">
				<fmt:message key="main.criterion.brand" />
			</h3>
		</div>
		<div class="panel-body">
			<ul class="nav nav-pills nav-stacked text-center">
				<c:forEach var="brand" items="${requestScope.criterionList }">
					<li>
						<form action="/CarRental/rental" method="POST">
							<input type="hidden" name="action" value="criterionList" /> <input
								type="hidden" name="brandId" value="${brand.id }" /> <input
								type="submit" class="btn-link"
								value="<c:out value="${brand.name }" />" />
						</form>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>