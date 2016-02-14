package com.andreev.rental.logic.util;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

	private List<String> messages = new ArrayList<String>();

	public MessageManager() {
	}

	public void addMessage(String message) {
		if (message != null) {
			this.messages.add(message);
		}
	}

	public void addAllMessages(List<String> messages) {
		if (messages != null) {
			this.messages.addAll(messages);
		}
	}

	public List<String> getMessages() {
		return this.messages;
	}

}
