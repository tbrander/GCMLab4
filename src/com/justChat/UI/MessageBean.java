package com.justChat.UI;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.justChat.BO.MailService;


@ViewScoped
@ManagedBean(name = "messageBean")
public class MessageBean implements Serializable{

	private static final long serialVersionUID = 3055904185644491458L;
	private List<Message> msgList= new ArrayList<>();
	private String receiver="", body="";
	private boolean includeMail=false;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	
	
	public MessageBean(){
		receiver = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("receiver");
		getMessages();
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
		// gör db anrop
		return msgList;
	}

	public void setMsgList(List<Message> msgList) {
		this.msgList = msgList;
	}
	
	private void getMessages(){
		// Testmetod.
		msgList.add(new Message("Lelle@gmail.com","me","Tjena! testing testing..","20:43"));
		msgList.add(new Message("Pelle@gmail.com","me","bllalalala..","14:29"));
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
    
    public void resetBody(){
    	body="";
    }
}
