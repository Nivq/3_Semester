package peer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Peer extends Remote
{
    void startPeer() throws RemoteException;

    void sendMessage(String message, String alias)
        throws RemoteException;

    void receiveMessage(String targetAlias) throws RemoteException;

}
