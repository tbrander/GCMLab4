package com.justChat.UI;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ManagedBean (name = "mainBean")
@SessionScoped
public class MainBean implements Serializable {

	private static final long serialVersionUID = -2725177498666007117L;

	private String fullname, mail;
	
	private final String URL = "http://193.10.39.196:8080/";

	public MainBean() {
		fullname = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("name");
		mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mail");
	}









	/*
	 * public List<MessageVM> getFeedList() { return feedList; }
	 * 
	 * public void setFeedList(List<MessageVM> feedList) { this.feedList =
	 * feedList; }
	 * 
	 * public void fetchNewStatusFeed(){ // hämta lista med ny status (poll med
	 * intervall 3), alltså senast skapad status för varje vän feedList.clear();
	 * 
	 * Gson gson = new Gson(); RestClient client = new RestClient(); Resource
	 * res = client .resource(URL+"Community/rest/messages/fetchFeed?userId=" +
	 * currentUser.getId()); String str =
	 * res.accept("application/json").get(String.class);
	 * 
	 * Type listType = new TypeToken<ArrayList<MessageVM>>() { }.getType();
	 * feedList.addAll(gson.fromJson(str, listType)); }
	 */

}