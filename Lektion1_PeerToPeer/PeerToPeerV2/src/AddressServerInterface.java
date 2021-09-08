import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddressServerInterface extends Remote
{
    boolean registerPeer(Peer peer) throws RemoteException;
    Peer findPeer(String alias) throws RemoteException;
    void startAddressServer() throws RemoteException, AlreadyBoundException;

}
