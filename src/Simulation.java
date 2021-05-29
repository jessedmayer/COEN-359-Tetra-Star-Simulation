import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Simulation{
    private JFrame window;
    private JPanel topPanel, groupPanel, TFace;
    private int timeStep;

    Simulation(){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1400,800);
        window.setTitle("Tetra-Star Simulation");

        createPanels();
        //groupPanel.add(statsPanel);

        //create menu bar
        window.setContentPane(topPanel);
        //window.setJMenuBar(createMenuBar());
        window.setVisible(true);
    }

    public void createPanels(){
        topPanel = new JPanel(new BorderLayout());
        groupPanel = new JPanel();

        //topPanel.add(lineChartPanel, BorderLayout.CENTER);
        topPanel.add(groupPanel, BorderLayout.EAST);

        //add borders and backgrounds to other panels
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        groupPanel.setBackground(Color.lightGray);
        topPanel.setBackground(Color.lightGray);
    }



    class TFacePositions {
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

        public boolean isOccupied() { return this.occupied; }
        public boolean isVisible() { return this.visible; }
        public boolean isWalkable() { return this.walkable; }
        public void changeOccupied() { this.occupied = !this.occupied; }
        public void foundVisible() { this.visible = true; }
        public void addMap(StarMap map) { }
        public void removeMap() { }
        public String show() { return " "; }
    }

    class emptyLocation extends Location {
        public emptyLocation() {
            super();
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
        public String show() {
            return "b";
        }
    }

    class TVaderBase extends Location {
        protected Base vBase;

        public TVaderBase() {
            super();

            this.vBase = new Base("TVader");
            this.visible = true;
        }

        @Override
        public void addMap(StarMap map) {
            vBase.addMap(map);
        }
        public String show() {
            return "B";
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
        public void addMap(StarMap map) { this.hasMap = true; }
        public void removeMap() { this.hasMap = false; }
        public String show() {
            return "M";
        }
    }

    class StarMap {

    }

    public static void main(String[] args){
        new Simulation();
    }
}



