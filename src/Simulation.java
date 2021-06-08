import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.awt.image.*;



public class Simulation{
    private JFrame window;
    private JPanel topPanel, groupPanel, headerPanel;
    private JPanel[][] TFacePanels;
    private JLabel[][] TFaceLabels;
    private JLabel currentTimeStep;
    private TFace face;
    private List<TUnit> TUnits;
    private int timeStep = 0;
    private int rows;
    private int columns;
    private int THeroes;
    private int TRovers;


    Simulation(){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Tetra-Star Simulation");

        getUserInput();
        createPanels();
        initializeTFace();

        window.setSize(110*columns,120*rows);
        //window.pack();
        window.setContentPane(topPanel);
        window.setVisible(true);
    }

    public void getUserInput(){
        JFrame f = new JFrame();
        String input;
        boolean validInput = false;
        while(!validInput){
            input = JOptionPane.showInputDialog(f,"TFace Rows (5-15):");
            rows = Integer.parseInt(input);
            if(rows >=5 && rows <= 15){
                validInput = true;
            }
            else{
                JOptionPane.showMessageDialog(f,"Please enter valid input");
            }
        }
        validInput = false;
        while(!validInput){
            input = JOptionPane.showInputDialog(f,"TFace Columns (5-15):");
            columns= Integer.parseInt(input);
            if(columns >=5 && columns <= 15){
                validInput = true;
            }
            else{
                JOptionPane.showMessageDialog(f,"Please enter valid input");
            }
        }
        validInput = false;
        while(!validInput){
            input = JOptionPane.showInputDialog(f,"THeroes (2-4):");
            THeroes = Integer.parseInt(input);
            if(THeroes >=2 && THeroes <= 4){
                validInput = true;
            }
            else{
                JOptionPane.showMessageDialog(f,"Please enter valid input");
            }
        }
        validInput = false;
        while(!validInput){
            input = JOptionPane.showInputDialog(f,"TRovers (2-4):");
            TRovers = Integer.parseInt(input);
            if(TRovers >=2 && TRovers <= 4){
                validInput = true;
            }
            else{
                JOptionPane.showMessageDialog(f,"Please enter valid input");
            }
        }
    }

    public void createPanels(){
        topPanel = new JPanel(new BorderLayout());
        setHeaderPanel();
        groupPanel = new JPanel();
        TFacePanels = new JPanel[rows][columns];
        TFaceLabels = new JLabel[rows][columns];
        face = new TFace(columns, rows);

        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(groupPanel, BorderLayout.CENTER);

        groupPanel.setLayout(new GridLayout(rows,columns));

        //Create Tetra Grid
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                TFacePanels[m][n] = new JPanel();
                TFaceLabels[m][n] = new JLabel();
                TFacePanels[m][n].add(TFaceLabels[m][n]);
                TFacePanels[m][n].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                groupPanel.add(TFacePanels[m][n]);
            }
        }

        //add borders and backgrounds to other panels
        groupPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        groupPanel.setBackground(Color.lightGray);
        topPanel.setBackground(Color.lightGray);
    }

    public void newSimulation(){
        timeStep = 0;
        currentTimeStep.setText("Current Time Step: " + timeStep);

        //Remove prior simulation's TFace
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                groupPanel.remove(TFacePanels[m][n]);
            }
        }

        getUserInput();

        //groupPanel = new JPanel();
        TFacePanels = new JPanel[rows][columns];

        groupPanel.setLayout(new GridLayout(rows,columns));

        //Create Tetra Grid
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                TFacePanels[m][n] = new JPanel();
                TFaceLabels[m][n] = new JLabel();
                TFacePanels[m][n].add(TFaceLabels[m][n]);
                TFacePanels[m][n].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                groupPanel.add(TFacePanels[m][n]);
            }
        }

        window.setSize(110*columns,120*rows);

        //Refreshes the TFace with its new rows and columns
        groupPanel.revalidate();

        //Re-initialized TFace with its new Locations and TUnits
        initializeTFace();

        //groupPanel.revalidate();
    }

    public void setHeaderPanel(){
        headerPanel = new JPanel(new BorderLayout());
        JButton newSimulation = new JButton("New Simulation");
        JButton nextTimeStep = new JButton("Next Time Step");
        currentTimeStep = new JLabel();
        currentTimeStep.setText("Current Time Step: " + timeStep);

        headerPanel.add(newSimulation, BorderLayout.WEST);
        headerPanel.add(nextTimeStep, BorderLayout.EAST);
        headerPanel.add(currentTimeStep, BorderLayout.CENTER);

        newSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newSimulation();
            }
        });

        nextTimeStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTimeStep();
            }
        });
    }

    public void nextTimeStep(){
        timeStep++;
        face.incrementTimeStep();
        currentTimeStep.setText("Current Time Step: " + timeStep);
        //Move all TUnits
        for (TUnit temp : TUnits){
            //temp.move();
            temp.nextTimeStep();
        }
        updateTFaceElements();
    }

    public void initializeTFace(){
        TUnits = new ArrayList<>();
        Random rand = new Random();
        int xPos = 0;
        int yPos = 0;
        int int_random;

        //Calc Random Location for VBase
        int xMax = columns - 3;
        int xMin = 2;
        int xRandom = (int)Math.floor(Math.random()*(xMax - xMin + 1) + xMin);
        int yMax = rows - 3;
        int yMin = 2;
        int yRandom = (int)Math.floor(Math.random()*(yMax - yMin + 1) + yMin);

        //Create VBase at xRandom, yRandom
        Location vBase = new TVaderBase(xRandom, yRandom);
        face.setLocation(vBase);
        face.setVX(xRandom);
        face.setVY(yRandom);

        //Create Rivers around VBase
        Location river1 = new riverLocation(xRandom-1, yRandom);
        Location river2 = new riverLocation(xRandom, yRandom-1);
        Location river3 = new riverLocation(xRandom+1, yRandom);
        Location river4 = new riverLocation(xRandom, yRandom+1);
        face.setLocation(river1);
        face.setLocation(river2);
        face.setLocation(river3);
        face.setLocation(river4);

        //Calc Random Locations for THeroBases, not on river or VaderBase
        //Select initial position that isn't empty
        xPos = xRandom;
        yPos = yRandom;
        int options = 4;
        for(int i = 0; i < THeroes; i++){
            while(face.getLocation(xPos,yPos) != null){
                int_random = rand.nextInt(options);
                switch(int_random) {
                    case 0:
                        //THeroBase on top row
                        xPos = rand.nextInt(columns);
                        yPos = 0;
                        break;
                    case 1:
                        //THeroBase on bottom row
                        xPos = rand.nextInt(columns);
                        yPos = rows - 1;
                        break;
                    case 2:
                        //THeroBase on left column
                        xPos = 0;
                        yPos = rand.nextInt(rows);
                        break;
                    case 3:
                        //THeroBase on right column
                        xPos = columns-1;
                        yPos = rand.nextInt(rows);
                        break;
                }
            }

            //Create THero Bases on edge of map (THero spawns there)
            String HeroId = "Hero" + (i+1);
            Location HBase = new THeroBase(xPos, yPos, HeroId);
            face.setLocation(HBase);

            //Randomly select encryption method for hero
            char method;
            int_random = rand.nextInt(2);
            switch(int_random) {
                case 0:
                    method = '^';
                    break;
                default:
                    method = '*';
                    break;
            }
            THero hb = new THero.HeroBuilder(HeroId).setGrid(face).setLoc(xPos, yPos).setBase(HBase).setChar(method).build();
            TUnit hero = hb;
            TUnits.add(hero);
        }

        //Create StarBases and place randomly in empty squares
        while(face.getLocation(xPos,yPos) != null){
            xPos = rand.nextInt(columns);
            yPos = rand.nextInt(rows);
        }
        StarMaps starAtlas = new StarAtlas(face, xPos, yPos, "Atlas");
        StarMaps starMap1 = new StarMap(face, xPos, yPos, "map1");
        StarMaps starMap2 = new StarMap(face, xPos, yPos, "map2");
        starAtlas.addMap(starMap1);
        starAtlas.addMap(starMap2);

        Location mapBase1 = new MapBase(xPos, yPos, starAtlas);
        starAtlas.setBase(mapBase1);
        face.setLocation(mapBase1);

        while(face.getLocation(xPos,yPos) != null){
            xPos = rand.nextInt(columns);
            yPos = rand.nextInt(rows);
        }
        StarMaps starMap3 = new StarMap(face, xPos, yPos, "map3");
        Location mapBase2 = new MapBase(xPos, yPos, starMap3);
        starMap3.setBase(mapBase2);
        face.setLocation(mapBase2);

        //Set Empty Locations
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                if(face.getLocation(n,m) == null){
                    Location newLocation = new emptyLocation(n,m);
                    face.setLocation(newLocation);
                }
            }
        }

        //Create TVader and place randomly in empty square
        //while(face.getLocation(xPos,yPos) != null){
        xPos = rand.nextInt(columns);
        yPos = rand.nextInt(rows);
        while(!face.getLocation(xPos,yPos).isEmpty()){
            xPos = rand.nextInt(columns);
            yPos = rand.nextInt(rows);
        }
        TVader vb = new TVader.VaderBuilder().setGrid(face).setLoc(xPos, yPos).setBase(vBase).build();
        TUnit vader = vb;
        TUnits.add(vader);

        //Create TRovers and place randomly in empty squares
        for(int i = 0; i < TRovers; i++){
            xPos = rand.nextInt(columns);
            yPos = rand.nextInt(rows);
            while(!face.getLocation(xPos,yPos).isEmpty()){
                xPos = rand.nextInt(columns);
                yPos = rand.nextInt(rows);
            }
            TUnits.add(new TRover(face, xPos, yPos));
        }

        //Update UI with all added elements
        updateTFaceElements();
    }

    public void updateTFaceElements(){
        //New Implementation using TFaceLocationFlags and IconStrategy
        TFaceLocationFlags flags = new TFaceLocationFlags(face, TUnits, columns, rows);
        flags.setLocationFlags();

        TFaceLocationFlags.LocationFlags currentLocationFlags;
        for(int m = 0; m < rows; m++) {
            for (int n = 0; n < columns; n++) {
                currentLocationFlags = flags.TFaceFlags[m][n];

                TFaceLabels[m][n].setIcon(null);
                if((flags.TFaceFlags[m][n].locationType.equals("Empty")) && (flags.TFaceFlags[m][n].TUnits == 1)){
                    TFaceLabels[m][n].setIcon((new EmptyLocationOneUnit()).assignIcon(currentLocationFlags));
                }
                if((flags.TFaceFlags[m][n].locationType.equals("Empty")) && (flags.TFaceFlags[m][n].TUnits == 2)){
                    TFaceLabels[m][n].setIcon((new EmptyLocationTwoUnits()).assignIcon(currentLocationFlags));
                }
                if((flags.TFaceFlags[m][n].locationType.equals("Empty")) && (flags.TFaceFlags[m][n].TUnits == 3)){
                    TFaceLabels[m][n].setIcon((new EmptyLocationThreeUnits()).assignIcon(currentLocationFlags));
                }
                if((!flags.TFaceFlags[m][n].isEmpty) && (flags.TFaceFlags[m][n].TUnits == 0)){
                    TFaceLabels[m][n].setIcon((new NonEmptyLocationZeroUnits()).assignIcon(currentLocationFlags));
                }
                if((!flags.TFaceFlags[m][n].isEmpty) && (flags.TFaceFlags[m][n].TUnits == 1)){
                    TFaceLabels[m][n].setIcon((new NonEmptyLocationOneUnit()).assignIcon(currentLocationFlags));
                }
                if((!flags.TFaceFlags[m][n].isEmpty) && (flags.TFaceFlags[m][n].TUnits == 2)){
                    TFaceLabels[m][n].setIcon((new NonEmptyLocationTwoUnits()).assignIcon(currentLocationFlags));
                }
                if((!flags.TFaceFlags[m][n].isEmpty) && (flags.TFaceFlags[m][n].TUnits == 3)){
                    TFaceLabels[m][n].setIcon((new NonEmptyLocationThreeUnits()).assignIcon(currentLocationFlags));
                }
                if(flags.TFaceFlags[m][n].locationType.equals("NotVisible")){
                    TFaceLabels[m][n].setIcon((new NotVisibleLocation()).assignIcon(currentLocationFlags));
                }
            }
        }
        //checkIndexing();
        groupPanel.revalidate();
    }

    public void checkIndexing(){
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                TFaceLabels[m][n].setText("(" + n + ", " + m + ")");
                //n is x/column and m is y/row
            }
        }
    }

    public static void main(String[] args){
        new Simulation();
    }
}



