package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;

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
	
	public void messages() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("messages.jsf");
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
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("chat.xhtml");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
}
