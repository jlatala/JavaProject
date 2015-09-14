package agh.project;

/**
 * This class provides satelittes structure for SQLite database.
 * @author Jakub Latala
 *
 */
public class Satellites {
	private String Name;
    private String EW;
    private Double Coordinates;
    private String Last_Update;
 
    /**
     * Returns coordinates of satellite.
     * @return coordinates of satellite
     */
    public Double getCoordinates() {
        return Coordinates;
    }
    
    /**
     * Sets coordinates of satellite.
     * @param _Coordinates coordinates of satellite
     */
    public void setCoordinate(Double _Coordinates) {
        this.Coordinates = _Coordinates;
    }
    
    /**
     * Returns name of satellite.
     * @return name of satellite
     */
    public String getName() {
        return Name;
    }
    
    /**
     * Sets name of satellite.
     * @param _Name name of satellite
     */
    public void setName(String _Name) {
        this.Name = _Name;
    }
    
    /**
     * Returns last update of satellite
     * @return last update of satellite
     */
    public String getLastUpdate() {
        return Last_Update;
    }
    
    /**
     * Sets last update of satellite
     * @param _Last_Updt last update of satellite
     */
    public void setLastUpdate(String _Last_Updt) {
        this.Last_Update = _Last_Updt;
    }
 
    /**
     * Default constructor. 
     */
    public Satellites() {}
    
    /**
     * Constructor of satellite
     * @param _Name name
     * @param _Coordinates coordinate
     * @param _EW east/west
     * @param _Last_Updt last update
     */
    public Satellites(String _Name, Double _Coordinates, String _EW, String _Last_Updt) {
    	this.Name = _Name;
        this.Coordinates = _Coordinates;
        this.EW = _EW;
        this.Last_Update = _Last_Updt;
    }
 
    @Override
    /**
     * Returns all parameters of satellite as string.
     */
    public String toString() {
        return "["+Coordinates+"°"+EW+"] - "+Name+" - "+Last_Update;
    }
}
