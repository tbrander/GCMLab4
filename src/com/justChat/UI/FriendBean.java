package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	
	private static final long serialVersionUID = -7271766193819041752L;
	
	
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	@SuppressWarnings("unused")
	private String fullname, mail, friendMail;
	private List<String> friendList;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	
	public FriendBean(){
		friendList = new ArrayList<>();
	}
	
	// ************* GETTERS / SETTERS ************** //
	
	
	public String getFriendMail() {
		return friendMail;
	}

	public void setFriendMail(String friendMail) {
		this.friendMail = friendMail;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getMail() {
		return userBean.getMail();
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
	
	@SuppressWarnings("unchecked")
	public List<String> getFriendList() {
		RestClient client = new RestClient();
		Resource res = client.resource(path + "friend/friendlist?user=" + userBean.getMail());
		String jsonFriends = res.accept("application/json").get(String.class);
		Gson gson = new Gson();
		friendList = gson.fromJson(jsonFriends, ArrayList.class);
		return friendList;
	}
	
	public void setFriendList(List<String> friendList) {
		this.friendList = friendList;
	}
	
	// *********** REDIRECT ************ //
	
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
	
	
	
	// *********** METHODS ************* //
	
	
	public void addFriendX(){
		// tillfällig metod, den under ska användas sen.
	}
	
	public String addFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getMail()); 
		friend.put("friend", friendMail);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "profile.xhtml?faces-redirect=true" + "&user=";// + paramUser;
	}
	
	public void sendMsg(){
		
	}
	
	public void readMsg(){
		
	}
	
	public void removeFriend(){
		
	}
}
