package com.justChat.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;



@SessionScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable{

	
	private static final long serialVersionUID = -7271766193819041752L;
	
	
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	private String fullname, mail, friendMail="";
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
	

	// *********** METHODS ************* //
	
	
	public void addFriendX(){
		// tillfällig metod, den under ska användas sen.
	}
	
	public String addFriend() {
		if(friendMail.equals("")){
			FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Specify gmail address!","Specify gmail address!"));
		}else if(friendMail.contains("@gmail.com")){
			FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Not a valid gmail address!","Not a valid gmail address!"));
		}else{
			friendMail=friendMail.concat("@gmail.com");
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getMail()); 
		friend.put("friend", friendMail);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful! Added: "+friendMail,null));
		friendMail="";
	}
		return "";// + paramUser;
	}
	
	public void sendMsg(){
		
	}
	
	public void readMsg(){
		
	}
	
	public void removeFriend(){
		
	}
}
