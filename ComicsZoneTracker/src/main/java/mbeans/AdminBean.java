package mbeans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String htmlMessage;

	public AdminBean() {

	}

	public void pushMessage(ActionEvent event) {

		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		eventBus.publish("/subscriber", htmlMessage);
		System.out.println("Message Sent at " + new Date());

	}

	public String getHtmlMessage() {
		return htmlMessage;
	}

	public void setHtmlMessage(String htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

}
