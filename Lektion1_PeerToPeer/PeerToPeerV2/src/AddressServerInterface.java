import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddressServerInterface extends Remote
{
    void registerPeer(String alias, int port) throws RemoteException;
    int findPeer(String alias) throws RemoteException;
    void startAddressServer() throws RemoteException, AlreadyBoundException;

}
