package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;


/***
 * 
 * @author Mattias och Thomas
 *
 */


@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean  implements Serializable{


	private static final long serialVersionUID = 1074935276878595593L;

	private String fullname;
	private String mail;
	
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	
	public UserBean(){	}
	
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

	
	public String login() { // Login + reg
		  HashMap<String, String> user = new HashMap<String, String>();
		  user.put("username", mail);
		  user.put("regid", mail);
		  user.put("phonenumber", fullname);
		  
		  Gson gson = new Gson();
		  String json = gson.toJson(user);
		  RestClient client = new RestClient();
		  Resource resource = client.resource(path+"user/register");
		  resource.contentType("application/json").accept("text/plain").post(String.class, json); // 200 OK
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("name", fullname);
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mail", mail);
		  return "";
		 }
	
	
	public void friends() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("friends.xhtml");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void home() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.xhtml");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
