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
		// Set fields
		this.alias = alias;
		this.port = port;
		peerList = new PeerList();

		// Create Local Registry
		localRegistry = LocateRegistry.createRegistry(this.port);
		localRegistry.bind(alias, this);
		UnicastRemoteObject.exportObject(this, 0);

		// Register Peer to the Remote Server (This is where we cheat :D )
		remoteRegistry = LocateRegistry.getRegistry(1099);
		((AddressServerInterface) remoteRegistry.lookup("addressServer")).registerPeer(alias, port);
	}

	@Override
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


	public void startLoop() throws RemoteException, NotBoundException {
		// Tells User, their Peer has loaded as their desired Alias on the specified port
		System.out.println("Peer has started up as \"" + alias + "\" on port: " + port);

		// Asks User whom they'd want to connect to
		System.out.println("Who do you want to connect to?");
		String msg = scan.nextLine();

		// Checks if the Peer can be found either in local list or on the registry server, and set as the servant
		if (setPeer(msg)) {
			// If Peer could be set, will start the Chat Loop
			chatLoop();
		} else {
			// Informs User, the Peer couldn't be found
			System.out.println("peer not found");
		}
	}

	private boolean setPeer(String alias) throws RemoteException, NotBoundException {
		// Checks if the Alias is listed in the Local PeerList
		if (peerList.exists(alias)) {
			// Gets the Port associated with the Alias
			int otherPort = peerList.find(alias);
			// Checks the Registry for the port and finds the Alias
			otherPeer = (PeerInterface) LocateRegistry.getRegistry(otherPort).lookup(alias);
			return true;
		} else {
			// Gets the Port associated with the Alias from the Remote Server
			int peerPort = ((AddressServerInterface) remoteRegistry.lookup("addressServer")).findPeer(alias);

			// Checks if an appropriate port was found
			if (peerPort != -1) {
				// Saves the Peer to the Local PeerList
				peerList.add(alias, peerPort);
				// Sets the Peer as the connected one
				otherPeer = (PeerInterface) LocateRegistry.getRegistry(peerPort).lookup(alias);
				return true;
			}
			// If Alias couldn't be found in either the Local or Remote PeerList
			return false;
		}
	}

	private void chatLoop() throws NotBoundException, RemoteException {
		String input = "";
		System.out.println("> You can now chat. You're texts are green: ");

		// Loop until User writes "disconnect"
		while (!input.equals("disconnect")) {
			input = scan.nextLine();

			// Check if a Command has been executed
			if (input.toCharArray()[0] == '!') {
				commandHandler(input.substring(1).split(" "));
			} else {
				// Attempts to send message to other Peer
				try {
					otherPeer.deliverMessage(new Message(input, alias, "you"));
				} catch (RemoteException e) {
					System.out.println("Connection to " + otherPeer.getAlias() + " has been lost");
				}
			}
		}
	}

	public void commandHandler(String[] cmdAndArg) throws NotBoundException, RemoteException {
		String cmd = cmdAndArg[0];
		String arg = "";
		if (cmdAndArg.length > 1) {
			arg = cmdAndArg[1];
		}

		if (cmd.equalsIgnoreCase("chat")) {
			setPeer(arg);
		} else if (cmd.equalsIgnoreCase("help")) {
			System.out.println("Use [!chat <Alias>] to chat to another user");
		}
	}
}
