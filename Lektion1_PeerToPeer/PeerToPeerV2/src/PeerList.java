import java.util.TreeMap;

public class PeerList
{
    private TreeMap<Peer, String> list;
    //Dunno med Peer, String

    public boolean exists(Peer peer)
    {
        return list.containsKey(peer);
    }

    public void add(Peer peer)
    {

    }

    public Peer find(String alias)
    {
        return null;
    }
}
