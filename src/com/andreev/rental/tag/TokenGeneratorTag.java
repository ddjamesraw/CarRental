package com.andreev.rental.tag;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TokenGeneratorTag extends TagSupport {

	private static final long serialVersionUID = -3193032414814796539L;
	private static final String PARAM_TOKEN = "token";

	public TokenGeneratorTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		saveToken(session);
		return SKIP_BODY;
	}
	
	protected void saveToken(HttpSession session) {
		String token = generateToken();
		if(token != null) {
			session.setAttribute(PARAM_TOKEN, token);
		}
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString();
	}

}
