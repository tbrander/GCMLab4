package com.justChat.BO;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 6451724710165233824L;
	private String sender;
    private String receiver;
    private String body;
    private String timestamp;
    private int id;

    public Message() {
    }
    
    public Message(String sender, String receiver, String body, String timestamp) {
		this.sender = sender;
		this.receiver = receiver;
		this.body = body;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return body;
    }
}
