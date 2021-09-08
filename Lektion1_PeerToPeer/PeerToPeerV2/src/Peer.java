import java.rmi.RemoteException;

public class Peer implements PeerInterface
{
    private String alias;
    private PeerInterface servant;

    public Peer(String alias, PeerInterface servant)
    {
        this.alias = alias;
        this.servant = servant;
    }

    public String getAlias()
    {
        return alias;
    }

    public PeerInterface getServant()
    {
        return servant;
    }

    @Override public void deliverMessage(Message message) throws RemoteException
    {

    }
}
