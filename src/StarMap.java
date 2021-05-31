import java.util.*;

class StarMaps {
    protected TFace grid;
    protected String mapId;
    protected int[] locId;
    protected int items;
    protected HashMap<String, Integer> hId_resCounter = new HashMap<>();
    protected List<Map> atlas = new ArrayList<Map>();
    protected int[] body = new int[2];

    public void view() { }
    public void encrypt(int heroId) { }
    public void decrypt(int heroId) { }
}

class StarMap extends StarMaps {

}