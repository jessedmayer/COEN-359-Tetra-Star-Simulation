import java.util.*;

interface TUnit {
    public void move();
    public void fly(int x, int y);
    public void getFlyer();
    public String show(); 

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
        Random rand = new Random();
        int directions = 4;
        while (newPos == null || !newPos.isRoverable()) {
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        this.xPos = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        this.xPos = xPos - 1;
                    }
                    break; 
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        this.yPos = yPos - 1;
                    }
                    break; 
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        this.yPos = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
    }
    public void fly(int x, int y) { }
    public void getFlyer() { }
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
    private Location basePos;

    public THero(TFace grid, int x, int y, int bx, int by) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.flyer = false;
        this.bxPos = bx;
        this.byPos = by;
        this.basePos = grid.getLocation(bx, by);
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        while (newPos == null || !newPos.isWalkable()) {
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        this.xPos = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        this.xPos = xPos - 1;
                    }
                    break; 
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        this.yPos = yPos - 1;
                    }
                    break; 
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        this.yPos = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
        //might need to move to separate method to split to different time steps
        if (this.pos.isMapBase()) {
            if (this.pos.hasMap()){
                StarMap map = this.pos.getMap();
                map.view();
                map.encrypt(heroId);
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
    private Location basePos;
    private int bxPos;
    private int byPos;

    public TVader(TFace grid, int x, int y, int bx, int by) {
        this.grid = grid;
        this.xPos = x;
        this.yPos = y;
        this.pos = grid.getLocation(x, y);
        this.bxPos = bx;
        this.byPos = by;
        this.basePos = grid.getLocation(bx, by);
        this.flyer = true;
        //implement builder pattern for constructor
    }
    public void move() {
        Location newPos = null;
        Random rand = new Random();
        int directions = 4;
        while (newPos == null || !newPos.isWalkable()) {
            int int_random = rand.nextInt(directions);
            switch(int_random) {
                case 0:
                    if ((xPos + 1) < grid.getXDim()) {
                        newPos = grid.getLocation(xPos+1, yPos);
                        this.xPos = xPos + 1;
                    }
                    break;
                case 1:
                    if ((xPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos-1, yPos);
                        this.xPos = xPos - 1;
                    }
                    break; 
                case 2:
                    if ((yPos - 1) >= 0) {
                        newPos = grid.getLocation(xPos, yPos-1);
                        this.yPos = yPos - 1;
                    }
                    break; 
                case 3:
                    if ((yPos + 1) < grid.getYDim()) {
                        newPos = grid.getLocation(xPos, yPos+1);
                        this.yPos = yPos + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        this.pos = newPos;
        if (this.pos.isMapBase()){
            StarMap map = this.pos.getMap();
            this.pos.removeMap();
            fly(bx, by);
            this.basePos.addMap(map);
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
    public String show() {
        if (flyer) {
            return "VF";
        }
        else {
            return "V";
        }
    }
}