import javax.swing.*;

public interface IconStrategy {
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags);
}

class NotVisibleLocation implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/NotVisible.jpg");
    }
}

class EmptyLocationOneUnit implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        if(currentLocationFlags.THeroes == 1){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero.jpg");
        }
        if(currentLocationFlags.TVaders == 1){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader.jpg");
        }
        if(currentLocationFlags.TRovers == 1){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TRover.jpg");
        }
        return null;
    }
}
class EmptyLocationTwoUnits implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        if(currentLocationFlags.THeroes == 2){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+THero.jpg");
        }
        if(currentLocationFlags.TRovers == 2){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TRover+TRover.jpg");
        }
        if((currentLocationFlags.THeroes == 1) && (currentLocationFlags.TRovers == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+TRover.jpg");
        }
        if((currentLocationFlags.THeroes == 1) && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+THero.jpg");
        }
        if((currentLocationFlags.TRovers == 1) && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+TRover.jpg");
        }
        return null;
    }
}
class EmptyLocationThreeUnits implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        return null;
    }
}
class NonEmptyLocationZeroUnits implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        if(currentLocationFlags.locationType.equals("River")){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/River.jpg");
        }
        if(currentLocationFlags.locationType.equals("THeroBase")){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THeroBase.jpg");
        }
        if(currentLocationFlags.locationType.equals("TVaderBase")){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVaderBase.jpg");
        }
        if(currentLocationFlags.locationType.equals("MapBase")){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/MapBase.jpg");
        }
        return null;
    }
}
class NonEmptyLocationOneUnit implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        if((currentLocationFlags.locationType.equals("THeroBase")) && (currentLocationFlags.THeroes == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+THeroBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("TVaderBase")) && (currentLocationFlags.THeroes == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+TVaderBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("TVaderBase")) && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+TVaderBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.THeroes == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.TRovers == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TRover+MapBase.jpg");
        }
        return null;
    }
}
class NonEmptyLocationTwoUnits implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        if((currentLocationFlags.locationType.equals("THeroBase")) && (currentLocationFlags.THeroes == 2)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+THero+THeroBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("TVaderBase")) && (currentLocationFlags.THeroes == 1)
                && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+THero+TVaderBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("TVaderBase")) && (currentLocationFlags.THeroes == 2)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+THero+TVaderBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.THeroes == 2)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+THero+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.TRovers == 2)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TRover+TRover+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.THeroes == 1)
                && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+THero+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.TRovers == 1)
                && (currentLocationFlags.TVaders == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/TVader+TRover+MapBase.jpg");
        }
        if((currentLocationFlags.locationType.equals("MapBase")) && (currentLocationFlags.TRovers == 1)
                && (currentLocationFlags.THeroes == 1)){
            return new ImageIcon("COEN-359-Tetra-Star-Simulation/Logos/THero+TRover+MapBase.jpg");
        }
        return null;
    }
}
class NonEmptyLocationThreeUnits implements IconStrategy{

    @Override
    public ImageIcon assignIcon(TFaceLocationFlags.LocationFlags currentLocationFlags) {
        return null;
    }
}