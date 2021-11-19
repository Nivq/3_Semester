package address;

import peer.Peer;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddressServer extends Remote
{
    void startAddressServer() throws RemoteException, AlreadyBoundException;
    void register(Peer peer) throws  RemoteException;
    Peer find(Peer peer) throws RemoteException;

}
