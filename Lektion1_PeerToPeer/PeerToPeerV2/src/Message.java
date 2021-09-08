import java.io.Serializable;

public class Message implements Serializable {
	private String text;
	private String from;
	private String to;

	public Message(String text, String from, String to) {
		this.text = text;
		this.from = from;
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
