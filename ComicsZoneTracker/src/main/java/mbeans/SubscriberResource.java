package mbeans;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint(value = "/a")
public class SubscriberResource {

	@OnMessage(encoders = { JSONEncoder.class })
	public String onMessage(String message) {
            System.err.println("message_________________"+message);
		return message;
	}

}
