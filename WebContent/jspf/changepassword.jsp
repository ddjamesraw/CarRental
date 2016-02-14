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
<c:if test="${empty user}">
	<jsp:forward page="/rental">
		<jsp:param name="action" value="toPage" />
		<jsp:param name="forward" value="jsp.authentication.login" />
	</jsp:forward>
</c:if>
<form action="/CarRental/rental" method="POST" class="form form-207px">
	<tag:messages />
	<input type="hidden" name="action" value="changePassword" />
	<tag:tokenInput />
	<div class="panel panel-default">
		<div class="panel-body text-center">
			<div class="input-group">
				<input id="old-password" type="password" name="password"
					class="form-control"
					placeholder="<fmt:message
						key="authentication.changepassword.old" />" />
			</div>
			<br />
			<div class="input-group">
				<input id="new-password" type="password" name="newPassword"
					class="form-control"
					placeholder="<fmt:message
						key="authentication.changepassword.new" />" />
			</div>
			<br />
			<div class="input-group">
				<input id="comfirm-password" type="password" name="confirmPassword"
					class="form-control"
					placeholder="<fmt:message
						key="authentication.changepassword.confirm" />" />
			</div>
			<br /> <input type="hidden" name="action" value="cangePassword" />
			<button class="btn btn-m btn-danger" type="submit">
				<fmt:message key="authentication.changepassword" />
			</button>
		</div>
	</div>
</form>