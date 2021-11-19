package peer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Scanner;

public class PeerImpl implements Peer
{
    private String alias;
    private Map<String, Peer> peers;
    private Registry serverRegistry;
    private Registry localRegistry;


    public PeerImpl(String alias){
        this.alias = alias;

    }



    public void startPeer()
        throws RemoteException
    {
        // set up rmi
        serverRegistry = LocateRegistry.getRegistry("localhost", 1099);

        UnicastRemoteObject.exportObject(this,0);

        try
        {
            serverRegistry.bind(alias, this);
        }
        catch (AlreadyBoundException e)
        {
            e.printStackTrace();
        }

        System.out.println(serverRegistry.list().length);

        handleInput();
    }


    private void handleInput(){
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("to who?");
            String target = scanner.nextLine();
            System.out.println("WHat you sain?");
            String message = scanner.nextLine();
            try
            {
                sendMessage(message,target);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override public void sendMessage(String message, String alias)
        throws RemoteException
    {
        try
        {
            Peer target = (Peer) serverRegistry.lookup(alias);
            target.receiveMessage(message);

        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override public void receiveMessage(String message)
        throws RemoteException
    {
        System.out.println(message);
    }
}
