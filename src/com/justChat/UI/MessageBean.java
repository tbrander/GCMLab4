package com.justChat.UI;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.google.gson.Gson;
import com.justChat.BO.MailService;
import com.justChat.BO.Message;


@SessionScoped
@ManagedBean(name = "messageBean")
public class MessageBean implements Serializable{

	private static final long serialVersionUID = 3055904185644491458L;
	private List<Message> msgList= new ArrayList<>();
	private String receiver="", body="", sender="";
	private boolean includeMail=false;
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	
	
	public MessageBean(){
		
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean isIncludeMail() {
		return includeMail;
	}

	public void setIncludeMail(boolean includeMail) {
		this.includeMail = includeMail;
	}

	public String getReceiver() {
		receiver = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sendTo");
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public UserBean getUserBean() {
		return userBean;
	}


	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	
	@SuppressWarnings("unchecked")
	public List<Message> getMsgList() {
		sender = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("receiveFrom");
		
		Gson gson = new Gson();
		RestClient client = new RestClient();
		Resource resource = client.resource(path+"message/history?sender="+sender+"&receiver="+userBean.getMail());
		String jsonMsg=resource.get(String.class);
		msgList= gson.fromJson(jsonMsg, ArrayList.class);
		
		return msgList;
	}

	public void setMsgList(List<Message> msgList) {
		this.msgList = msgList;
	}
	
	
	public void sendMsg(){
		if(body.equals("")){
			FacesContext.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"The message has no content!",
							"The message has no content!"));
		}else{
			
			Map<String, String> newMessage = new HashMap<String, String>();
			newMessage.put("sender", userBean.getMail());
			newMessage.put("receiver", receiver);
			newMessage.put("body", body);
			
			Gson gson = new Gson();
			String json = gson.toJson(newMessage);
			RestClient client = new RestClient();
			Resource resource = client.resource(path + "message/sendmessage");
			resource.contentType("application/json").accept("text/plain").post(String.class, json);
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sent to: "	+ receiver, null));
			context.addMessage(null, new FacesMessage("Message: "+body, null));

			if (includeMail) {
				MailService.sendMail(userBean.getFullname(),userBean.getMail(), receiver,
						"JustChat - You've got mail from "+ userBean.getFullname() + ".",body+"\n\n\nVisit the JustChat forum at http://1-dot-amazing-craft-117312.appspot.com");
			}
			includeMail=false;
			body="";
		}
	}
	
    private String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(time);
    }
    
    private static String dateFormater(String date) {
        Date d = new Date();
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"), sqf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        try {
            d = sqf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(d);
    }
    
    public void resetBody(){
    	body="";
    }
    
    private String createMsg(Message message){
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("sender", message.getSender());
            msgMap.put("receiver", message.getReceiver());
            msgMap.put("body", message.getBody());
            msgMap.put("timestamp", message.getTimestamp());
            Gson gson = new Gson();
            String json = gson.toJson(msgMap);
            return json;
    }
}
