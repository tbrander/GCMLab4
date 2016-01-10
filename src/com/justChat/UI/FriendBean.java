package com.justChat.UI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;






import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;



@SessionScoped
@ManagedBean(name = "friendBean")
public class FriendBean implements Serializable{

	private String path = "http://130.237.84.211:8080/justchat/rest/";
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public String addFriend() {
		Map<String, String> friend = new HashMap<String, String>();
		friend.put("user", userBean.getGmail());
		//friend.put("friend", paramUser);
		Gson gson = new Gson();
		String json = gson.toJson(friend);
		RestClient client = new RestClient();
		Resource resource = client.resource(path + "friend/addfriend");
		resource.contentType("application/json").accept("text/plain").post(String.class, json);
		return "profile.xhtml?faces-redirect=true" + "&user=";// + paramUser;
	}
}
