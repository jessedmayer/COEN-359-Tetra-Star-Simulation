abstract class TUnitState {
    protected TFace grid;
    protected TUnit unit;

    public TUnitState(TFace grid, TUnit unit) { this.grid = grid; this.unit=unit; }
    abstract public void nextState();
    abstract public void nextTimeStep();
}

class HeroMovingState extends TUnitState {
    private int x;
    private int y;

    public HeroMovingState(TFace grid, TUnit unit) { 
        super(grid, unit);
        this.x = unit.getX();
        this.y = unit.getY();
    }

    public void nextState() {
        if(grid.getLocation(x, y).isMapBase()) {
            if(!grid.getLocation(x, y).hasMap()) {
                if (unit.hasFlyer()) {
                    unit.setCurrentState(new HeroFlytoVBaseState(grid, unit));
                }
                else {
                    unit.setCurrentState(new HeroGetFlyerState(grid, unit));
                }
            }
        }
    }
    public void nextTimeStep() {
        unit.move();
        this.x = unit.getX();
        this.y = unit.getY();
        nextState();
    }
}

class HeroGetFlyerState extends TUnitState {
    public HeroGetFlyerState(TFace grid, TUnit unit) {
        super(grid, unit);
    }

    public void nextState() {
        unit.setCurrentState(new HeroFlytoVBaseState(grid, unit));
    }
    public void nextTimeStep() {
        unit.getFlyer();
        nextState();
    }
}

class HeroFlytoVBaseState extends TUnitState {
    private StarMaps map;

    public HeroFlytoVBaseState(TFace grid, TUnit unit) {
        super(grid, unit);
        this.map = grid.getLocation(unit.getX(), unit.getY()).getMap();
    }

    public void nextState() {
        unit.setCurrentState(new HeroRetrieveMapState(grid, unit, map));
    }
    public void nextTimeStep() {
        unit.fly(grid.getVX(), grid.getVY());
        nextState();
    }
}

class HeroRetrieveMapState extends TUnitState {
    private StarMaps map;
    private Location vBase; 
    private Location hBase;

    public HeroRetrieveMapState(TFace grid, TUnit unit, StarMaps map) {
        super(grid, unit);
        this.map = map;
        this.vBase = grid.getLocation(grid.getVX(), grid.getVY());
        this.hBase = grid.getLocation(unit.getBaseX(), unit.getBaseY());
    }

    public void nextState() {
        unit.setCurrentState(new HeroMovingState(grid, unit));
    }
    public void nextTimeStep() {
        //this state will remove the map from VBase, encrypt it, add the map back to its original mapBase and to HBase
        //fly the hero back to HBase, and make the hero lose his flyer (so he has to call for another one later)
        vBase.removeMap(map);
        map.encrypt(unit, unit.getSymbol());
        map.getBase().addMap(map);
        hBase.addMap(map);
        unit.fly(hBase.getX(), hBase.getY());
        unit.loseFlyer();
        nextState();
    }
}

class VaderMovingState extends TUnitState {
    private int x;
    private int y;

    public VaderMovingState(TFace grid, TUnit unit) {
        super(grid, unit);
        this.x = unit.getX();
        this.y = unit.getY();
    }

    public void nextState() {
        if (grid.getLocation(x, y).isMapBase()) {
            if (grid.getLocation(x, y).hasMap()) {
                StarMaps map = grid.getLocation(x, y).getMap();
                unit.setCurrentState(new VaderStealMapState(grid, unit, map));
            }
        }
    }
    public void nextTimeStep() {
        unit.move();
        this.x = unit.getX();
        this.y = unit.getY();
        nextState();
    }
}

class VaderStealMapState extends TUnitState {
    private StarMaps map;
    private int x;
    private int y;

    public VaderStealMapState(TFace grid, TUnit unit, StarMaps map) {
        super(grid, unit);
        this.map = map;
        this.x = unit.getX();
        this.y = unit.getY();
    }

    public void nextState() {
        unit.setCurrentState(new VaderFlyBackState(grid, unit, x, y));
    }
    public void nextTimeStep() {
        grid.getLocation(x, y).removeMap(map);
        unit.fly(grid.getVX(), grid.getVY());
        grid.getLocation(grid.getVX(), grid.getVY()).addMap(map);
        nextState();
    }
}

class VaderFlyBackState extends TUnitState {
    private int x;
    private int y;

    public VaderFlyBackState(TFace grid, TUnit unit, int x, int y) {
        super(grid, unit);
        this.x = x;
        this.y = y;
    }

    public void nextState() {
        unit.setCurrentState(new VaderMovingState(grid, unit));
    }

    public void nextTimeStep() {
        unit.fly(x, y);
        nextState();
    }
}