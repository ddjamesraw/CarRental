package com.andreev.rental.tag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class TokenInputTag extends TagSupport {

	private static final long serialVersionUID = -3721388800322014812L;
	private static final Logger LOG = Logger.getLogger(TokenInputTag.class);
	private static final String PARAM_TOKEN = "token";
	private static final String INPUT = "<input type=\"hidden\" name=\"%s\" value=\"%s\">";

	public TokenInputTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		String token = (String) session.getAttribute(PARAM_TOKEN);
		if(token != null) {
			String tag = String.format(INPUT, PARAM_TOKEN, token);
			JspWriter writer = pageContext.getOut();
			try {
				writer.print(tag);
			} catch (IOException e) {
				LOG.error("Token input tag", e);
			}
		}
		return SKIP_BODY;
	}

}
