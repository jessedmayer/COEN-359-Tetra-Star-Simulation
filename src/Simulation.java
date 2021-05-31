import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Simulation{
    private JFrame window;
    private JPanel topPanel, groupPanel, headerPanel;
    private JPanel[][] TFacePanels;
    private JLabel currentTimeStep;
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

        window.setSize(100*columns,100*rows);
        window.setContentPane(topPanel);
        window.setVisible(true);
    }

    public void getUserInput(){
        JFrame f = new JFrame();
        String input;

        input = JOptionPane.showInputDialog(f,"TFace Rows (5-15):");
        rows = Integer.parseInt(input);
        input = JOptionPane.showInputDialog(f,"TFace Columns (5-15):");
        columns= Integer.parseInt(input);
        input = JOptionPane.showInputDialog(f,"THeroes (2-4):");
        THeroes = Integer.parseInt(input);
        input = JOptionPane.showInputDialog(f,"TRovers (2-4):");
        TRovers = Integer.parseInt(input);
    }

    public void createPanels(){
        topPanel = new JPanel(new BorderLayout());
        setHeaderPanel();
        groupPanel = new JPanel();
        TFacePanels = new JPanel[rows][columns];

        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(groupPanel, BorderLayout.CENTER);

        groupPanel.setLayout(new GridLayout(rows,columns));

        //Create Tetra Grid
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                TFacePanels[m][n] = new JPanel();
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
                TFacePanels[m][n].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                groupPanel.add(TFacePanels[m][n]);
            }
        }

        window.setSize(100*columns,100*rows);

        //Refreshes the TFace with its new rows and columns
        groupPanel.revalidate();
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
        currentTimeStep.setText("Current Time Step: " + timeStep);
        //Scan TFace for TRovers, THeroes, and TVaders and have them move
        //look through TUnit list and move them
        //Unit element.move()
        //Revalidate (update) TFace for new positions
        //.show()
    }

    public void initializeTFace(){
        List<TUnit> TUnits = new ArrayList<>();
        Random rand = new Random();

        //Create TFace;
        TFace face = new TFace(columns, rows);

        //Calc Random Location for VBase
        int xMax = columns - 2;
        int xMin = 1;
        int xRandom = (int)Math.floor(Math.random()*(xMax - xMin + 1) + xMin);
        int yMax = rows - 2;
        int yMin = 1;
        int yRandom = (int)Math.floor(Math.random()*(yMax - yMin + 1) + yMin);

        //Create VBase at xRandom, yRandom
        TFace.

        //Create Rivers around VBase


        //Calc Random Locations for THeroBases
        int options = 4;
        int xPos;
        int yPos;
        for(int i = 0; i < THeroes; i++){
            int int_random = rand.nextInt(options);
            switch(int_random) {
                case 0:
                    //THeroBase on top row
                    yPos = 0;
                    
                    break;
                case 1:
                    //THeroBase on bottom row
                    break;
                case 2:
                    //THeroBase on left column
                    break;
                case 3:
                    //THeroBase on right column
                    break;
            }
            //Create THero Bases on edge of map (THero spawns there)


        }



        //Number of StarMaps based on TFace size(MapBases) (can only be in bases)
        //TRovers just span randomly (needs to be in own square)
        TUnits.add(new THero());

    }


    public static void main(String[] args){
        new Simulation();
    }
}



