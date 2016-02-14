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
<c:if test="${empty user }">
	<form action="/CarRental/rental" method="POST" class="form text-center">
		<tag:tokenInput />
		<input type="hidden" name="action" value="login" />
		<h2 class="form-signin-heading">
			<fmt:message key="authentication.login" />
		</h2>
		<tag:messages />
		<br />
		<div class="input-group">
			<span class="input-group-addon">@</span> <input type="email"
				class="form-control"
				placeholder='<fmt:message key="authentication.login.email" />'
				name="email">
		</div>
		<br />
		<div class="input-group">
			<span class="input-group-addon"><span
				class="glyphicon glyphicon-lock"></span></span> <input type="password"
				class="form-control"
				placeholder="<fmt:message key="authentication.login.password" />"
				name="password">
		</div>
		<br />
		<button class="btn btn-m btn-danger" type="submit">
			<fmt:message key="authentication.login.signin" />
		</button>
		<br /> <br /> <a style="cursor: pointer;" class="pull-right"
			onclick="register_popup();"><fmt:message
				key="authentication.register.signup" /></a>
	</form>
</c:if>