import javax.swing.*;
import java.util.*;

class TFace {
    private List<String> heroIds = new ArrayList<>();
    private Location[][] grid;
    private int xSize;
    private int ySize;
    private int vBaseX;
    private int vBaseY;
    private int currentTimeStep = 0;

    public TFace(int x, int y){
        grid = new Location[x][y];
        this.xSize = x;
        this.ySize = y;
    }

    public void incrementTimeStep(){ this.currentTimeStep++; }

    public int getCurrentTimeStep(){ return this.currentTimeStep;}

    public void setLocation(Location location) {
        this.grid[location.getX()][location.getY()] = location;
    }
    public Location getLocation(int x, int y) {
        return this.grid[x][y];
    }
    public int getXDim() {
        return this.xSize;
    }
    public int getYDim() {
        return this.ySize;
    }
    public void setVX(int x) {
        this.vBaseX = x;
    }
    public void setVY(int y) {
        this.vBaseY = y;
    }
    public int getVX() {
        return this.vBaseX;
    }
    public int getVY() {
        return this.vBaseY;
    }
}

class Location {
    protected boolean occupied;
    protected boolean visible;
    protected boolean walkable;
    protected boolean roverable;
    protected boolean vaderable;
    protected boolean mapBase;
    protected boolean containMap;
    protected boolean empty;
    protected int xPos;
    protected int yPos;

    public Location(int x, int y) {
        this.occupied = false;
        this.visible = false;
        this.walkable = true;
        this.roverable = false;
        this.vaderable = true;
        this.mapBase = false;
        this.containMap = false;
        this.empty = false;
        this.xPos = x;
        this.yPos = y;
    }

    public boolean isEmpty() { return this.empty; }
    public boolean hasMap() { return this.containMap; }
    public boolean isMapBase() { return this.mapBase; }
    public boolean isOccupied() { return this.occupied; }
    public boolean isVisible() { return this.visible; }
    public boolean isWalkable() { return this.walkable; }
    public boolean isRoverable() { return this.roverable; }
    public boolean isVaderable() { return this.vaderable; }
    public void changeOccupied() { this.occupied = !this.occupied; }
    public void foundVisible() { this.visible = true; }
    public void addMap(StarMaps map) { }
    public void removeMap(StarMaps map) { }
    public StarMaps getMap() { return null; }
    public String show() { return " "; }
    public int getX() { return this.xPos; }
    public int getY() { return this.yPos; }
}

class emptyLocation extends Location {
    public emptyLocation(int x, int y) {
        super(x, y);
        this.roverable = true;
        this.empty = true;
    }
    @Override
    public String show() {
        return ".";
    }
}

class riverLocation extends Location {
    public riverLocation(int x, int y) {
        super(x, y);
        this.walkable = false;
        this.visible = true;
        this.vaderable = false;
    }
    @Override
    public String show() {
        //return "~";
        return "River";
    }
}

//adaptee for bases
class Base {
    protected List<StarMaps> maps = new ArrayList<>();
    protected String baseId;

    public Base(String id) {
        this.baseId = id;
    }
    public void addMap(StarMaps map) { this.maps.add(map); }
    public void removeMap(StarMaps map) { this.maps.remove(map); }
    public List<StarMaps> getMaps() { return this.maps; }
}

class THeroBase extends Location {
    protected Base hBase;

    public THeroBase(int x, int y, String heroId) {
        super(x, y);
        this.hBase = new Base(heroId);
        this.visible = true;
        this.vaderable = false;
    }

    @Override
    public void addMap(StarMaps map) {
        hBase.addMap(map);
        this.containMap = true;
    }
    public String show() {
        //return "b";
        return "THeroBase";
    }
}

class TVaderBase extends Location {
    protected Base vBase;

    public TVaderBase(int x, int y) {
        super(x, y);

        this.vBase = new Base("TVader");
        this.visible = true;
    }

    @Override
    public void addMap(StarMaps map) {
        vBase.addMap(map);
        this.containMap = true;
    }
    public String show() {
        //return "B";
        return "TVaderBase";
    }
    public void removeMap(StarMaps map) {
        vBase.removeMap(map);
        if (vBase.getMaps().isEmpty()) {
            this.containMap = false;
        }
    }
}

class MapBase extends Location {
    protected StarMaps map;

    public MapBase(int x, int y, StarMaps map) {
        super(x, y);
        this.map = map;
        this.roverable = true;
        this.mapBase = true;
        this.containMap = true;
    }

    @Override
    public void addMap(StarMaps map) { this.containMap = true; }
    public void removeMap(StarMaps map) {
        this.containMap = false;

        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, "TVader has stolen a StarMap!");

    }
    public StarMaps getMap() { return this.map; }
    public String show() {
        //return "M";
        return "MapBase";
    }
}