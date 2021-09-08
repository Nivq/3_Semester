import java.rmi.RemoteException;

public class AddressServer implements AddressServerInterface
{
    @Override public boolean registerPeer(Peer peer) throws RemoteException
    {
        return false;
    }

    @Override public Peer findPeer(String alias) throws RemoteException
    {
        return null;
    }
}
