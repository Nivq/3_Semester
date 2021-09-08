import java.util.TreeMap;

public class PeerList
{
    private TreeMap<String, Integer> list = new TreeMap<>();

    public boolean exists(String alias)
    {
        return list.containsKey(alias);
    }

    public void add(String alias, int port)
    {
        list.put(alias, port);
    }

    public int find(String alias)
    {
        return list.get(alias);
    }

    public int size(){
        return list.size();
    }
}
