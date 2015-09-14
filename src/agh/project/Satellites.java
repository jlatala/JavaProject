package agh.project;

//ta klasa poki co nie jest wykorzystywana
public class Satellites {
	public String Name;
    public String EW;
    public Double Coordinates;
    public String Last_Update;
 
    public Double getCoordinates() {
        return Coordinates;
    }
    public void setCoordinate(Double _Coordinates) {
        this.Coordinates = _Coordinates;
    }
    public String getName() {
        return Name;
    }
    public void setName(String _Name) {
        this.Name = _Name;
    }
    public String getLastUpdate() {
        return Last_Update;
    }
    public void setLastUpdate(String _Last_Updt) {
        this.Last_Update = _Last_Updt;
    }
 
    public Satellites() {}
    public Satellites(String _Name, Double _Coordinates, String _EW, String _Last_Updt) {
    	this.Name = _Name;
        this.Coordinates = _Coordinates;
        this.EW = _EW;
        this.Last_Update = _Last_Updt;
    }
 
    @Override
    public String toString() {
        return "["+Coordinates+"] - "+EW+" "+Name+" - "+Last_Update;
    }
}
