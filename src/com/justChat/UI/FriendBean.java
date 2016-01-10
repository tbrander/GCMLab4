package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import com.google.gson.Gson;



@ViewScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271766193819041752L;
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	@SuppressWarnings("unused")
	private String fullname, mail;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	
	public FriendBean(){
		fullname = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("name");
		mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mail");
	}

	
	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getFullname() {
		return userBean.getFullname();
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	public void home() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.xhtml");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}


	public String addFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getMail());
		//friend.put("friend", paramUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "profile.xhtml?faces-redirect=true" + "&user=";// + paramUser;
	}
	
	
	
}
