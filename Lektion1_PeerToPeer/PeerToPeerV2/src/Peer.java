import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Peer implements PeerInterface {
	private String alias;
	private PeerInterface otherPeer;
	private Registry localRegistry, remoteRegistry;
	private int port;
	private PeerList peerList;
	private Scanner scan = new Scanner(System.in);


	public Peer(String alias, int port) throws RemoteException, AlreadyBoundException, NotBoundException {
		this.alias = alias;
		this.port = port;
		// Create Local Registry
		localRegistry = LocateRegistry.createRegistry(this.port);
		localRegistry.bind(alias, this);
		UnicastRemoteObject.exportObject(this, 0);

		// Register Peer to the Remote Server (This is where we cheat :D )
		remoteRegistry = LocateRegistry.getRegistry(1099);
		((AddressServerInterface) remoteRegistry.lookup("addressServer")).registerPeer(alias, port);

		peerList = new PeerList();

	}

	public String getAlias() {
		return alias;
	}

	public PeerInterface getOtherPeer() {

		return otherPeer;
	}


	@Override
	public void deliverMessage(Message message) throws RemoteException {
		System.out.println(message.getFrom() + ": " + message.getText());
	}

	@Override
	public void deliverMessage(String message) throws RemoteException {
		System.out.println(message);
	}

	public void startLoop() throws RemoteException, NotBoundException {
		System.out.println("Peer has started up as \"" + alias + "\" on port: " + port);
		String msg;

		while (true) {
			System.out.println("Who do you want to connect to?");
			msg = scan.nextLine();

			if (peerList.exists(msg)) {
				int otherPort = peerList.find(msg);
				otherPeer = (PeerInterface) LocateRegistry.getRegistry(otherPort).lookup(msg);
			} else {
				int peerPort = ((AddressServerInterface) remoteRegistry.lookup("addressServer")).findPeer(msg);
				if (peerPort != -1) {
					peerList.add(msg, peerPort);
					otherPeer = (PeerInterface) LocateRegistry.getRegistry(peerPort).lookup(msg);
					chatLoop();
				}
				System.out.println("peer not found");
			}
		}
	}


	private void chatLoop() {
		String input = "";

		System.out.println("> You can now chat. You're texts are green: ");
		while (!input.equals("disconnect")) {
			input = scan.nextLine();
			try {
				otherPeer.deliverMessage(new Message(input, alias, "you"));
			} catch (RemoteException e) {
				e.printStackTrace();
			}


		}


	}

}
