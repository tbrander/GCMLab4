package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ViewScoped
@ManagedBean(name = "navigationBean")
public class NavigationBean implements Serializable{


	private static final long serialVersionUID = -2033060808027927722L;

	public void home() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void addNewFriend() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("addFriend.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void friends() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("friends.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void chat() {
		try {
			Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String partner = params.get("chat");
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("partner", partner);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("chat.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void sendMsg() {
		try {
			Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String receiver = params.get("sendmsg");
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("receiver", receiver);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("sendmessage.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void readMsg() {

		try {
			Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String sender = params.get("readmsg");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sender", sender);
			FacesContext.getCurrentInstance().getExternalContext().redirect("readmessage.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
}
