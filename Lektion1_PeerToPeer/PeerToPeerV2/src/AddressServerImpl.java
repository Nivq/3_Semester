import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AddressServerImpl implements AddressServerInterface
{
    @Override public boolean registerPeer(Peer peer) throws RemoteException
    {
        return false;
    }

    @Override public Peer findPeer(String alias) throws RemoteException
    {
        return null;
    }

    @Override public void startAddressServer()
        throws RemoteException, AlreadyBoundException
    {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("addressServer", this);
        UnicastRemoteObject.exportObject(this,0);
    }
}
