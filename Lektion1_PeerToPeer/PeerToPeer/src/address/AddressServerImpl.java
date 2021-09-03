package address;

import peer.Peer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AddressServerImpl implements AddressServer
{




    public AddressServerImpl()
    {

    }

    public void startAddressServer()
        throws RemoteException, AlreadyBoundException
    {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("addressServer", this);
        UnicastRemoteObject.exportObject(this,0);

    }

    @Override public void register(Peer peer)
    {

    }

    @Override public Peer find(Peer peer)
    {
        return null;
    }
}
