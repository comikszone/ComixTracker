package mbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "successBean")
@ViewScoped
public class SuccessBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public SuccessBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.name = (String) session.getAttribute("username");

	}

	public void receiveMessage(ActionEvent event) {
		System.out.println("Message received at " + new Date());
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String msgFromAdmin = requestParameterMap.get("msgData");
		FacesMessage facesMessage = new FacesMessage("New Notification from Admin", msgFromAdmin);
		facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage("growSubscriber", facesMessage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
