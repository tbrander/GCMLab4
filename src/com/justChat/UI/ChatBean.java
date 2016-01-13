package com.justChat.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import com.google.gson.Gson;
import com.justChat.BO.Message;

@ViewScoped
@ManagedBean(name = "chatBean")
public class ChatBean implements Serializable{

	
	private static final long serialVersionUID = -1281905285370701912L;
	private String partner, partnerAddress, body="";
	private List<Message> chatMsgList = new ArrayList<>();
	private String path = "http://130.237.84.211:8080/justchat/rest/";
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	
	public ChatBean(){
		
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

	public String getPartnerAddress() {
		partnerAddress = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partnerAddress");
		return partnerAddress;
	}

	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}

	public List<Message> getChatMsgList() {
		return chatMsgList;
	}

	public void setChatMsgList(List<Message> chatMsgList) {
		this.chatMsgList = chatMsgList;
	}

	public String getPartner() {
		partnerAddress = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partnerAddress");
		partner = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partner");
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	public void sendMsg(ActionEvent actionEvent){
		partnerAddress = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partnerAddress");
		partner = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partner");
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
			newMessage.put("receiver", partnerAddress);
			newMessage.put("body", body);
			
			Gson gson = new Gson();
			String json = gson.toJson(newMessage);
			RestClient client = new RestClient();
			Resource resource = client.resource(path + "message/sendmessage");
			resource.contentType("application/json").accept("text/plain").post(String.class, json);
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sent to: "	+ partner, null));
			context.addMessage(null, new FacesMessage("Message: "+body, null));

			body="";
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> fetchNewMessages() {
		partnerAddress = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("partnerAddress");
		
		Gson gson = new Gson();
		RestClient client = new RestClient();
		
		Resource resource = client.resource(path+"message/history?sender="+partnerAddress+"&receiver="+userBean.getMail());
		String jsonMsg=resource.get(String.class);
		chatMsgList= gson.fromJson(jsonMsg, ArrayList.class);
		
		return chatMsgList;
		
	}

}
