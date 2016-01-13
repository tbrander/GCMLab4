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
import com.justChat.BO.MailService;

@SessionScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable {

	private static final long serialVersionUID = -7271766193819041752L;

	private String path = "http://130.237.84.211:8080/justchat/rest/";
	private String fullname, mail, friendMail = "";
	private Map<String,String>friendList = new HashMap<>();
	
	private boolean includeMail = false;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	public FriendBean() {
		
	}

	// ************* GETTERS / SETTERS ************** //

	public void setFriendList(Map<String, String> friendList) {
		this.friendList = friendList;
	}

	public Map<String,String> getFriendMap() {
		
		return friendList;
	}



	public boolean isIncludeMail() {
		return includeMail;
	}

	public void setIncludeMail(boolean includeMail) {
		this.includeMail = includeMail;
	}

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
		Resource res = client.resource(path + "friend/friendlist?user="
				+ userBean.getMail());
		String jsonFriends = res.accept("application/json").get(String.class);
		Gson gson = new Gson();
		List<String> friendFrDB = new ArrayList<>();
		friendFrDB = gson.fromJson(jsonFriends, ArrayList.class);
		if(friendFrDB == null){
			return new ArrayList<String>();
		}
		
		splitMailAddress(friendFrDB);
		List<String> list = new ArrayList<String>(friendList.keySet());
		return list;
	}

	private void splitMailAddress(List<String> friendFrDB) {
		
		  for (String s : friendFrDB) {
			  friendList.put((s.split("@"))[0],s);
		  }
	}


	// *********** METHODS ************* //


	public void addFriend() {
		if (friendMail.equals("")) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL,
									"Specify gmail address!",
									"Specify gmail address!"));
		} else if (friendMail.contains("@")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Not a valid gmail address!",
							"Not a valid gmail address!"));
		} else {
			friendMail = friendMail.concat("@gmail.com");
			Map<String, String> friend = new HashMap<String, String>();
			friend.put("user", userBean.getMail());
			friend.put("friend", friendMail);
			Gson gson = new Gson();
			String json = gson.toJson(friend);
			RestClient client = new RestClient();
			Resource resource = client.resource(path + "friend/addfriend");
			resource.contentType("application/json").accept("text/plain")
					.post(String.class, json);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Successful! Added: "
					+ friendMail, null));
			if (includeMail) {
				MailService.sendMail(userBean.getFullname(),userBean.getMail(), friendMail,
						"JustChat - You have a new friend!",
						userBean.getFullname() + " has added you as a friend.\nVisit the JustChat forum at 1-dot-amazing-craft-117312.appspot.com");
			}
			friendMail = "";
			includeMail=false;
		}
	}

}
