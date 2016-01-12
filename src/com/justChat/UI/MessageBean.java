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
	private String receiver="", body="";
	private boolean includeMail=false;
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	
	
	public MessageBean(){
		
	}
	
	public boolean isIncludeMail() {
		return includeMail;
	}

	public void setIncludeMail(boolean includeMail) {
		this.includeMail = includeMail;
	}

	public String getReceiver() {
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
	
	public List<Message> getMsgList() {
		receiver = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sender");
		
		Gson gson = new Gson();
		RestClient client = new RestClient();
		Resource resource = client.resource(path+"message/history?sender="+userBean.getMail()+"&receiver="+receiver);
		String jsonMsg=resource.get(String.class); // 200 OK
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
			
			Message newMsg = new Message(userBean.getMail(),receiver,body, formatTime(Calendar.getInstance().getTimeInMillis()));
			
			Gson gson = new Gson();
			String json = gson.toJson(createMsg(newMsg));
		/*	RestClient client = new RestClient();
			Resource resource = client.resource(path+"message/history?sender="+userBean.getMail()+"&receiver="+receiver);
			resource.contentType("application/json").accept("text/plain").post(String.class, json); // 200 OK*/
			
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Message sent to: "	+ receiver +" from: "+userBean.getMail(), null));
			context.addMessage(null, new FacesMessage(newMsg.getTimestamp()+" - " + "Message: "+body, null));
			
			//  anrop till db -->
			
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
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"), sqf = new SimpleDateFormat("yyyy-mm-dd HH:mm:SSSS");
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
    
    
    private Map<String, String> createMsg(Message message){
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("sender", message.getSender());
            msgMap.put("receiver", message.getReceiver());
            msgMap.put("body", message.getBody());
            msgMap.put("timestamp", message.getTimestamp());
            
            return msgMap;
    }
}
