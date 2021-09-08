public class Message
{
    private String text;
    private Peer from;
    private Peer to;

    public Message(String text, Peer from, Peer to)
    {
        this.text = text;
        this.from = from;
        this.to = to;
    }

    public String getText()
    {
        return text;
    }

    public String getFrom()
    {
        return from.getAlias();
    }


    public Peer getTo()
    {
        return to;
    }
}
