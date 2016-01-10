package com.justChat.UI;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;

@SessionScoped
@ManagedBean (name = "googleLogin")
public class GoogleLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mail = "";
	

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void onPush() {

		FacesMessage message = new FacesMessage("Inne i bönan! msg: " + mail);
		if(!mail.contains("@")){
			message = new FacesMessage("Ingen e-post! : " + mail);
		}
		
		FacesContext.getCurrentInstance().addMessage(null, message);

	}


}