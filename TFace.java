class TFace {
    private List<String> heroIds;
    private boolean vBaseExist;
}

class Location {
    protected boolean occupied;
    protected boolean visible;
    protected boolean walkable;

    public Location() {
        this.occupied = false;
        this.visible = false;
        this.walkable = true;
    }

    public void addMap(StarMap map) { }
    public void removeMap() { }
}

class emptyLocation extends Location {
    public emptyLocation() {
        super();
    }
}

class riverLocation extends Location {
    public riverLocation() {
        super();
        this.walkable = false;
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
        hBase.addMap(map);
    }
}

class MapBase extends Location {
    private boolean hasMap;
    protected StarMap map;

    public MapBase(StarMap map) {
        super();
        this.map = map;
        this.hasMap = true;
    }

    @Override
    public void addMap() { this.hasMap = true; }
    public void removeMap() { this.hasMap = false; }
}

class StarMap {

}