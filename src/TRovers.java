import java.util.*;

interface TUnit {
    public void move();
    public void fly(int x, int y);
    public void getFlyer();
    public String show(); 
    public String getId();
    public void changeEncryption(char c); 
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
    public String getId() { return null; }
    public void changeEncryption(char c) { }
    public String show() { 
        return "R";
    }
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
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        int newX = -1;
        int newY = -1;
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
        //might need to move to separate method to split to different time steps
        if (this.pos.isMapBase()) {
            if (this.pos.hasMap()){
                StarMap map = this.pos.getMap();
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
    public void getFlyer() {
        this.flyer = true;
    }
    public void changeEncryption(char c) {
        this.encryptMethod = c;
    }
    public String getId() { return this.id; }
    public String show() {
        if (flyer) {
            return "HF";
        }
        else {
            return "H";
        }
    }
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

    public TVader(TFace grid, int x, int y, Location vBase) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.bxPos = vBase.getX();
        this.byPos = vBase.getY();
        this.base = vBase;
        this.flyer = true;
        //implement builder pattern for constructor
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        int newX = -1;
        int newY = -1;
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
        this.xPos = newX;
        this.yPos = newY;
        if (this.pos.isMapBase()){
            StarMap map = this.pos.getMap();
            this.pos.removeMap(map);
            fly(bx, by);
            this.base.addMap(map);
            //implement memento pattern to retrace steps
        }

    }
    public void fly(int x, int y) {
        if (flyer) {
            this.xPos = x;
            this.yPos = y;
            this.pos = grid.getLocation(x, y);
        }
    }
    public void getFlyer() {
        this.flyer = true;
    }
    public void changeEncryption(char c) { }
    public String getId() { return null; }
    public String show() {
        if (flyer) {
            return "VF";
        }
        else {
            return "V";
        }
    }
}
