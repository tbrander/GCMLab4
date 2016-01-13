package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


@SessionScoped
@ManagedBean(name = "navigationBean")
public class NavigationBean implements Serializable{


	private static final long serialVersionUID = -2033060808027927722L;
	@ManagedProperty(value = "#{friendBean}")
	private FriendBean friendBean;
	
	
	public void setFriendBean(FriendBean friendBean) {
		this.friendBean = friendBean;
	}

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
			
			Map<String,String> friendMap = friendBean.getFriendMap();
			
			String partnerAddress = friendMap.get(partner);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("partner", partner);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("partnerAddress", partnerAddress);
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
			
			Map<String,String> friendMap = friendBean.getFriendMap();
			
			receiver = friendMap.get(receiver);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sendTo", receiver);
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
			Map<String,String> friendMap = friendBean.getFriendMap();
			
			sender = friendMap.get(sender);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("receiveFrom", sender);
			FacesContext.getCurrentInstance().getExternalContext().redirect("readmessage.jsf");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
}
