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
        Location newPos = this.pos;
        int newX = xPos;
        int newY = yPos;
        Random rand = new Random();
        int directions = 4;
        while (newPos == this.pos || !newPos.isRoverable()) {
            newX = xPos;
            newY = yPos;
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
        return "TRover";
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

    public static class HeroBuilder {
        private TFace grid = null;
        private String id;
        private Location hBase = null;
        private int x = -1;
        private int y = -1;
        private char method = ' ';

        public HeroBuilder(String id) {
            this.id = id;
        }
        public HeroBuilder setGrid(TFace grid) {
            this.grid = grid;
            return this;
        }
        public HeroBuilder setLoc(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }
        public HeroBuilder setBase(Location hBase) {
            this.hBase = hBase;
            return this;
        }
        public HeroBuilder setChar(char method) {
            this.method = method;
            return this;
        }
        public THero build() {
            return new THero(this);
        }
    }

    private THero(HeroBuilder builder) {
        this.grid = builder.grid;
        this.xPos = builder.x;
        this.yPos = builder.y;
        this.pos = grid.getLocation(xPos, yPos);
        this.flyer = false;
        this.bxPos = builder.hBase.getX();
        this.byPos = builder.hBase.getY();
        this.base = builder.hBase;
        this.id = builder.id;
        this.encryptMethod = builder.method;
        this.currentState = new HeroMovingState(grid, this);
    }
    public void move() {
        Location newPos = this.pos;
        Random rand = new Random();
        int directions = 4;
        int newX = xPos;
        int newY = yPos;
        while (newPos == this.pos || !newPos.isWalkable()) {
            newX = xPos;
            newY = yPos;
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
            return "THero+Flyer";
        }
        else {
            //return "H";
            return "THero";
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

    public static class VaderBuilder {
        private TFace grid = null;
        private Location vBase = null;
        private int x = -1;
        private int y = -1;

        public VaderBuilder() {
        }
        public VaderBuilder setGrid(TFace grid) {
            this.grid = grid;
            return this;
        }
        public VaderBuilder setLoc(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }
        public VaderBuilder setBase(Location vBase) {
            this.vBase = vBase;
            return this;
        }
        public TVader build() {
            return new TVader(this);
        }
    }
    private TVader(VaderBuilder builder) {
        this.grid = builder.grid;
        this.xPos = builder.x;
        this.yPos = builder.y;
        this.pos = builder.grid.getLocation(xPos, yPos);
        this.bxPos = builder.vBase.getX();
        this.byPos = builder.vBase.getY();
        this.base = builder.vBase;
        this.flyer = true;
        this.currentState = new VaderMovingState(grid, this);
    }
    public void move() {
        Location newPos = this.pos;
        Random rand = new Random();
        int directions = 4;
        int newX = xPos;
        int newY = yPos;
        while (newPos == this.pos || !newPos.isVaderable()) {
            newX = xPos;
            newY = yPos;
            //System.out.println("inside while loop");
            int int_random = rand.nextInt(directions);
            //System.out.println(int_random);
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
            return "TVader+Flyer";
        }
        else {
            //return "V";
            return "TVader";
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
