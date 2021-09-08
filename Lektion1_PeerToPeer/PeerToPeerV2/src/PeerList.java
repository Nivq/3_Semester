import java.util.TreeMap;

public class PeerList
{
    private TreeMap<String, Integer> list = new TreeMap<>();

    public boolean exists(String alias)
    {
        return list.containsKey(peer);
    }

    public void add(String alias, int port)
    {
        list.put(alias, port);
    }

    public Peer find(String alias)
    {
        return null;
    }

    public int size(){
        return list.size();
    }
}
