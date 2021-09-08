import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AddressServerImpl implements AddressServerInterface {
	PeerList peerList = new PeerList();

	@Override
	public void registerPeer(String alias, int port) throws RemoteException {
		peerList.add(alias, port);
	}

	@Override
	public int findPeer(String alias) throws RemoteException {
		if (peerList.exists(alias)) {
			return peerList.find(alias);
		} else {
			return -1;
		}
	}

	@Override
	public void startAddressServer()
			throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("addressServer", this);
		UnicastRemoteObject.exportObject(this, 0);
		new Thread(() -> {
			while (true) {
				System.out.println("Online users: " + peerList.size());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
