import java.util.*;

interface TUnit {
    public void move();
    public void fly(int x, int y);
    public void getFlyer();
    public String show();
    public String getId();
    public void changeEncryption(char c);
    public void nextTimeStep();
    public int getX();
    public int getY();
    public void setCurrentState (TUnitState s);
    public boolean hasFlyer();
    public int getBaseX();
    public int getBaseY();
    public char getSymbol();
    public void loseFlyer();
}

class TRover implements TUnit {
    private TFace grid;
    private boolean flyer;
    private int xPos;
    private int yPos;
    private Location pos;

    public TRover(TFace grid, int x, int y) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.flyer = false;
    }

    public void move() {
        Location newPos = null;
        int newX = -1;
        int newY = -1;
        Random rand = new Random();
        int directions = 4;
        while (newPos == null || !newPos.isRoverable()) {
            newPos = this.pos;
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        newX = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        newX = xPos - 1;
                    }
                    break;
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        newY = yPos - 1;
                    }
                    break;
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        newY = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
        this.pos.foundVisible();
        this.xPos = newX;
        this.yPos = newY;
    }
    public void fly(int x, int y) { }
    public void getFlyer() { }
    public boolean hasFlyer() { return this.flyer; }
    public String getId() { return null; }
    public void changeEncryption(char c) { }
    public void setCurrentState (TUnitState s) { }
    public String show() {
        //return "R";
        return "COEN-359-Tetra-Star-Simulation/Logos/TRover.jpg";
    }
    public void nextTimeStep() { move(); }
    public int getX() { return this.xPos; }
    public int getY() { return this.yPos; }
    public int getBaseX() { return -1; }
    public int getBaseY() { return -1; }
    public char getSymbol() { return ' '; }
    public void loseFlyer() { this.flyer = false; }
}

class THero implements TUnit {
    private TFace grid;
    private boolean flyer;
    private int xPos;
    private int yPos;
    private Location pos;
    private int bxPos;
    private int byPos;
    private Location base;
    private String id;
    private char encryptMethod;
    private TUnitState currentState;

    public THero(TFace grid, int x, int y, Location hBase, String id, char method) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.flyer = false;
        this.bxPos = hBase.getX();
        this.byPos = hBase.getY();
        this.base = hBase;
        this.id = id;
        this.encryptMethod = method;
        this.currentState = new HeroMovingState(grid, this);
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        int newX = xPos;
        int newY = yPos;
        while (newPos == null || !newPos.isWalkable()) {
            newPos = this.pos;
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        newX = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        newX = xPos - 1;
                    }
                    break;
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        newY = yPos - 1;
                    }
                    break;
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        newY = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
        this.pos.foundVisible();
        this.xPos = newX;
        this.yPos = newY;
        //if mapBase has a map, THero will view map in the same time step
        if (this.pos.isMapBase()) {
            if (this.pos.hasMap()){
                StarMaps map = this.pos.getMap();
                map.view(this);
            }
        }
    }
    public void fly(int x, int y) {
        if (flyer) {
            this.xPos = x;
            this.yPos = y;
            this.pos = grid.getLocation(x, y);
        }
    }
    public void getFlyer() { this.flyer = true; }
    public void loseFlyer() { this.flyer = false; }
    public boolean hasFlyer() { return this.flyer; }
    public void changeEncryption(char c) {
        this.encryptMethod = c;
    }
    public String getId() { return this.id; }
    public String show() {
        if (flyer) {
            //return "HF";
            return "COEN-359-Tetra-Star-Simulation/Logos/THero+Flyer.jpg";
        }
        else {
            //return "H";
            return "COEN-359-Tetra-Star-Simulation/Logos/THero.jpg";
        }
    }
    public void setCurrentState (TUnitState s) { this.currentState = s; }
    public void nextTimeStep() {
        this.currentState.nextTimeStep();
    }
    public int getX() { return this.xPos; }
    public int getY() { return this.yPos; }
    public int getBaseX() { return this.bxPos; }
    public int getBaseY() { return this.byPos; }
    public char getSymbol() { return this.encryptMethod; }
}

class TVader implements TUnit {
    private TFace grid;
    private boolean flyer;
    private int xPos;
    private int yPos;
    private Location pos;
    private Location base;
    private int bxPos;
    private int byPos;
    private TUnitState currentState;

    public TVader(TFace grid, int x, int y, Location vBase) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.bxPos = vBase.getX();
        this.byPos = vBase.getY();
        this.base = vBase;
        this.flyer = true;
        this.currentState = new VaderMovingState(grid, this);
        //implement builder pattern for constructor
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        int newX = -1;
        int newY = -1;
        while (newPos == null || !newPos.isVaderable()) {
            newPos = this.pos;
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        newX = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        newX = xPos - 1;
                    }
                    break;
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        newY = yPos - 1;
                    }
                    break;
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        newY = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
        this.xPos = newX;
        this.yPos = newY;
    }
    public void fly(int x, int y) {
        if (flyer) {
            this.xPos = x;
            this.yPos = y;
            this.pos = grid.getLocation(x, y);
        }
    }
    public void getFlyer() { this.flyer = true; }
    public void loseFlyer() { this.flyer = false; }
    public boolean hasFlyer() { return this.flyer; }
    public void changeEncryption(char c) { }
    public void setCurrentState (TUnitState s) { this.currentState = s; }
    public String getId() { return null; }
    public String show() {
        if (flyer) {
            return "COEN-359-Tetra-Star-Simulation/Logos/TVader+Flyer.jpg";
        }
        else {
            //return "V";
            return "COEN-359-Tetra-Star-Simulation/Logos/TVader.jpg";
        }
    }
    public int getX() { return this.xPos; }
    public int getY() { return this.yPos; }
    public void nextTimeStep() {
        this.currentState.nextTimeStep();
    }
    public int getBaseX() { return this.bxPos; }
    public int getBaseY() { return this.byPos; }
    public char getSymbol() { return ' '; }
}