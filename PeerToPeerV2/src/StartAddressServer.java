import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class StartAddressServer
{
    public static void main(String[] args)
    {
        AddressServerInterface addressServer = new AddressServerImpl();
        try
        {
            addressServer.startAddressServer();
        }
        catch (RemoteException | AlreadyBoundException e)
        {
            e.printStackTrace();
        }
    }
}
