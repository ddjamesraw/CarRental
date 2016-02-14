package com.andreev.rental.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class MessageTag extends TagSupport {

	private static final long serialVersionUID = 1534243121774152829L;
	private static final Logger LOG = Logger.getLogger(MessageTag.class);
	private static final String RESOURCE_PATH = "resources.i18n.strings";
	private static final String PARAM_LANGUAGE = "language";
	private static final String PARAM_MESSAGE = "message";
	private static final String BEGIN_TAG = "<div class=\"alert alert-danger fade in\">";
	private static final String BUTTON = "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">%s</button>";
	private static final String ICON = "<span class=\"glyphicon glyphicon-remove\"></span>";
	private static final String NEW_LINE = "<br/>";
	private static final String END_TAG = "</div>";
	private static final String MISSIND_RESOURSE = "???%s???";

	public MessageTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		Object language = pageContext.getSession().getAttribute(PARAM_LANGUAGE);
		Locale locale;
		if (language != null) {
			locale = new Locale(language.toString());
		} else {
			locale = pageContext.getRequest().getLocale();
		}
		ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH,
				locale);
		@SuppressWarnings("unchecked")
		ArrayList<String> messageList = (ArrayList<String>) pageContext
				.getRequest().getAttribute(PARAM_MESSAGE);
		if (messageList != null && !messageList.isEmpty()) {
			JspWriter writer = pageContext.getOut();
			try {
				writer.print(BEGIN_TAG);
				writer.print(String.format(BUTTON, ICON));
				for (String message : messageList) {
					addMessage(writer, resource, message);
				}
				writer.print(END_TAG);
			} catch (IOException e) {
				LOG.error("Message tag", e);
			}
		}
		return SKIP_BODY;
	}

	private void addMessage(JspWriter writer, ResourceBundle resource,
			String message) throws IOException {
		try {
			writer.print(resource.getString(message));
		} catch (MissingResourceException e) {
			writer.print(String.format(MISSIND_RESOURSE, message));
		}
		writer.print(NEW_LINE);
	}

}
