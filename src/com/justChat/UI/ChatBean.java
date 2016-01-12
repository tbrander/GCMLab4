package com.justChat.UI;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@SessionScoped
@ManagedBean(name = "chatBean")
public class ChatBean implements Serializable{

	
	private static final long serialVersionUID = -1281905285370701912L;
	private String partner;
	
	public ChatBean(){
		
	}

	public String getPartner() {
		partner = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partner");
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

}
