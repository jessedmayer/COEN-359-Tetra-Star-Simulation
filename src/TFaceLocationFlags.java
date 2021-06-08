import java.util.List;

public class TFaceLocationFlags {
    LocationFlags[][] TFaceFlags;
    TFace face;
    int rows;
    int columns;
    List<TUnit> TUnits;

    TFaceLocationFlags(TFace face, List<TUnit> TUnits, int columns, int rows){
        this.face = face;
        this.TUnits = TUnits;
        this.columns = columns;
        this.rows = rows;
        TFaceFlags = new LocationFlags[rows][columns];
        for(int m = 0; m < rows; m++) {
            for (int n = 0; n < columns; n++) {
                TFaceFlags[m][n] = new LocationFlags();
            }
        }
    }

    public void setLocationFlags(){
        for(int m = 0; m < rows; m++) {
            for (int n = 0; n < columns; n++) {
                if(face.getLocation(n,m).isEmpty()){
                    TFaceFlags[m][n].locationType = "Empty";
                }
                if(!face.getLocation(n,m).isEmpty()){
                    TFaceFlags[m][n].isEmpty = false;
                }
                if(face.getLocation(n, m).show().equals("THeroBase")){
                    TFaceFlags[m][n].locationType = "THeroBase";
                }
                if(face.getLocation(n, m).show().equals("TVaderBase")){
                    TFaceFlags[m][n].locationType = "TVaderBase";
                }
                if(face.getLocation(n, m).show().equals("MapBase")){
                    TFaceFlags[m][n].locationType = "MapBase";
                }
                if(face.getLocation(n, m).show().equals("River")){
                    TFaceFlags[m][n].locationType = "River";
                }
                if(!face.getLocation(n, m).isVisible()){
                    TFaceFlags[m][n].locationType = "NotVisible";
                }
            }
        }
        int xPos;
        int yPos;
        for (TUnit temp : TUnits) {
            xPos = temp.getX();
            yPos = temp.getY();

            if(temp.show().equals("THero") || temp.show().equals("THero+Flyer")){
                TFaceFlags[yPos][xPos].THeroes++;
                TFaceFlags[yPos][xPos].TUnits++;
            }
            if(temp.show().equals("TVader") || temp.show().equals("TVader+Flyer")){
                TFaceFlags[yPos][xPos].TVaders++;
                TFaceFlags[yPos][xPos].TUnits++;
            }
            if(temp.show().equals("TRover")){
                TFaceFlags[yPos][xPos].TRovers++;
                TFaceFlags[yPos][xPos].TUnits++;
            }
        }
    }


    class LocationFlags{
        String locationType = null;    //NotVisible, Empty, River, THeroBase, TVaderBase, MapBase (River skipped so not needed)
        boolean isEmpty = true;
        int TUnits = 0;
        int THeroes = 0;
        int TVaders = 0;
        int TRovers = 0;

        public LocationFlags() {

        }
    }
}
