class TFace {
    private List<String> heroIds;
    private boolean vBaseExist;
    private Location[][] grid;
    private int xSize;
    private int ySize;

    public TFace(int x, int y){
        grid = new Location[x][y];
        this.xSize = x;
        this.ySize = y;
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
}

class Location {
    protected boolean occupied;
    protected boolean visible;
    protected boolean walkable;
    protected boolean roverable;
    protected boolean mapBase;
    protected boolean containMap;

    public Location() {
        this.occupied = false;
        this.visible = false;
        this.walkable = true;
        this.roverable = false;
        this.mapBase = false;
        this.containMap = false;
    }

    public boolean hasMap() { return this.containMap; }
    public boolean isMapBase() { return this.mapBase; }
    public boolean isOccupied() { return this.occupied; }
    public boolean isVisible() { return this.visible; }
    public boolean isWalkable() { return this.walkable; }
    public boolean isRoverable() { return this.roverable; }
    public void changeOccupied() { this.occupied = !this.occupied; }
    public void foundVisible() { this.visible = true; }
    public void addMap(StarMap map) { }
    public void removeMap(StarMap map) { }
    public StarMap getMap() { return null; }
    public String show() { return " "; }
}

class emptyLocation extends Location {
    public emptyLocation() {
        super();
        this.roverable = true;
    }
    @Override
    public String show() {
        return ".";
    }
}

class riverLocation extends Location {
    public riverLocation() {
        super();
        this.walkable = false;
    }
    @Override
    public String show() {
        return "~";
    }
}

//adaptee for bases
class Base {
    protected List<StarMap> maps;
    protected String baseId;

    public Base(String id) {
        this.baseId = id;
    }
    public void addMap(StarMap map) { this.maps.add(map); }
    public void removeMap(StarMap map) { this.maps.remove(map); }
    //might need to implement hasMap(StarMap map) to look for map
}

class THeroBase extends Location {
    protected Base hBase;

    public THeroBase(String heroId) {
        super();
        this.hBase = new Base(heroId);
    }

    @Override
    public void addMap(StarMap map) {
        hBase.addMap(map);
        this.containMap = true;
    }
    public String show() {
        return "b";
    }
}

class TVaderBase extends Location {
    protected Base vBase;

    public TVaderBase() {
        super();

        this.hBase = new Base("TVader");
        this.visible = true;
    }

    @Override
    public void addMap(StarMap map) {
        vBase.addMap(map);
        this.containMap = true;
    }
    public String show() {
        return "B";
    }
    public void removeMap(StarMap map) {
        vBase.removeMap(map);
    }
}

class MapBase extends Location {
    protected StarMap map;

    public MapBase(StarMap map) {
        super();
        this.map = map;
        this.roverable = true;
        this.mapBase = true;
        this.containMap = true;
    }

    @Override
    public void addMap(StarMap map) { this.hasMap = true; }
    public void removeMap(StarMap map) { this.hasMap = false; }
    public StarMap getMap() { return this.map; }
    public String show() {
        return "M";
    }
}

class StarMap {
    public void view() { }
    public void encrypt(int heroId) { }
}
