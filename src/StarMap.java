import javax.swing.*;
import java.util.*;

class StarMaps {
    protected TFace grid;
    protected Location mapBase;
    protected String id;
    protected int[] locId = new int[2];
    protected int items;
    protected boolean encrypted;
    protected boolean group;
    protected HashMap<String, Integer> hId_resCounter = new HashMap<>();
    protected List<StarMaps> atlas = new ArrayList<StarMaps>();
    protected int[] body = new int[2];
    protected int[] encryptBody = new int[2];
    protected String viewing = "";
    protected String encryptView = "";
    protected String border = "";
    protected char eSymbol;
    protected TUnit eHero;
    protected String heroId = "";
    protected int encryptDate = 0;

    public StarMaps(TFace grid, int x, int y, String id) {
        Random rand = new Random();
        this.grid = grid;
        this.id = id;
        this.locId[0] = x;
        this.locId[1] = y;
        this.body[0] = rand.nextInt(grid.getXDim());
        this.body[1] = rand.nextInt(grid.getYDim());
        this.items = 1;
        this.encrypted = false;
        this.group = false;
    }

    public void setBase(Location mapBase){
        this.mapBase = mapBase;
    }

    public void view(TUnit hero) {
        //if map isn't encrypted, simply show coordinates from body
        if(!encrypted) {
            //System.out.println(body);
            grid.getLocation(body[0], body[1]).foundVisible();

            //Pop up message
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "StarMap Coordinates: (" + body[0] + ", " + body[1] + ")");
        }
        //if map is encrypted, try to decrypt and show results. Then, set view back to encrypted version
        else {
            decrypt(hero);
            //System.out.println(viewing);
            //Pop up message
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,hero.getId() + " is viewing StarMap: \n" + viewing);
            viewing = encryptView;



        }
    }
    public void encrypt(TUnit hero, char symbol) {
        //if the map is currently not encrypted, encrypt the map
        //and add heroId to header (part of the restore_counter)
        JFrame f = new JFrame();
        if (!encrypted) {
            switch(symbol) {
                case '*':
                    this.encryptBody[0] = body[1];
                    this.encryptBody[1] = body[0];
                    this.eSymbol=symbol;
                    break;
                case '^':
                    this.encryptBody[0] = (body[0]+5)%10;
                    this.encryptBody[1] = (body[1]+3)%10;
                    this.eSymbol=symbol;
                    break;
                default:
                    break;
            }
            for (int i = 0; i < 20; i++){
                this.border += eSymbol;
                //System.out.println("New Border: " + this.border);
            }
            this.heroId = hero.getId();
            this.encryptDate = grid.getCurrentTimeStep();
            //need to implement adding time step
            this.viewing = "ID: " + heroId + " Tetra Date: " + this.encryptDate + "\nCoordinates: (" +
                    encryptBody[0] + ", " + encryptBody[1] + ")";
            this.encryptView = viewing;
            this.encrypted = true;
            this.eHero = hero;
            hId_resCounter.put(heroId, 1);

            //learn the info from the map in case it wasn't learned before
            grid.getLocation(body[0], body[1]).foundVisible();

            JOptionPane.showMessageDialog(f, heroId + " has encrypted the Star Map");
        }
        //if the map is already encrypted, increment res_counter if original encryptor encrypts
        //otherwise, add new hero into id, which is part of the restore_counter. restore_counter stays at 0
        else {
            if (hero == this.eHero) {
                hId_resCounter.put(heroId, hId_resCounter.get(heroId) + 1);

                JOptionPane.showMessageDialog(f, heroId + " has restored the map " + hId_resCounter.get(heroId) + " times");
            }
            else {
                hId_resCounter.put(hero.getId(), 1);
                JOptionPane.showMessageDialog(f, hero.getId() + " has restored the map encrypted by " + heroId);
            }
        }
    }
    public void decrypt(TUnit hero) {
        if (this.eHero == hero) {
            this.viewing = this.border + "\nID: " + hero.getId() + " Tetra Date: " + this.encryptDate
                    + "\nCoordinates: (" + body[0] + ", " + body[1] + ")" + "\n" + border;
        }
    }
    public void addMap(StarMaps map) {
        if (group) {
            atlas.add(map);
        }
    }
    public boolean showSignal(String mapId) {
        if (this.id == mapId){
            return true;
        }
        return false;
    }
    public Location getBase() {
        return this.mapBase;
    }
}

class StarMap extends StarMaps {
    public StarMap(TFace grid, int x, int y, String id) {
        super(grid, x, y, id);
    }
}

class StarAtlas extends StarMaps {
    public StarAtlas(TFace grid, int x, int y, String id) {
        super(grid, x, y, id);
        this.group = true;
    }

    @Override
    public void setBase(Location mapBase){
        for(StarMaps map : atlas){
            map.setBase(mapBase);
        }
        this.mapBase = mapBase;
    }
    public void view(TUnit hero) {
        for (StarMaps map : atlas) {
            map.view(hero);
        }
    }
    public void encrypt(TUnit hero, char symbol) {
        for (StarMaps map : atlas) {
            map.encrypt(hero, symbol);
        }
    }
    public boolean showSignal(String mapId) {
        for (StarMaps map : atlas) {
            if (map.showSignal(mapId)){
                return true;
            }
        }
        return false;
    }
}