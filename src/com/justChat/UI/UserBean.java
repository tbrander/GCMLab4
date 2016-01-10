package com.justChat.UI;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/***
 * 
 * @author Mattias och PACKMASTER
 *
 */
@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean  implements Serializable{


	private static final long serialVersionUID = 1074935276878595593L;

	private String fullname;
	private String mail;
	
	
	public UserBean(){
		
	}
	
	public String getMail() {
		return mail;
	}
 

	public void setMail(String mail) {
		this.mail = mail;
	}

	
	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


}
