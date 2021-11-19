import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StartPeer {
	public static void main(String[] args) throws NotBoundException, RemoteException, AlreadyBoundException {
		Scanner s = new Scanner(System.in);
		System.out.println("Input Alias");
		String alias = s.nextLine();
		System.out.println("Input desired Port");
		int port = s.nextInt();
		s.nextLine();
		Peer peer = new Peer(alias, port);
		peer.startLoop();
	}
}
