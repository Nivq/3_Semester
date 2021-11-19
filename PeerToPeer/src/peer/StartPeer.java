package peer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StartPeer
{
    public static void main(String[] args)
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("PLiz my duwd gib name");
        String input = scanner.nextLine();

        Peer peer = new PeerImpl(input);
        try
        {
            peer.startPeer();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }
}
