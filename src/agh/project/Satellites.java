package agh.project;

public class Satellites {
	private String coordinates;
    private String names;
    private String last_update;
 
    public String getCoordinates() {
        return coordinates;
    }
    public void setCoordinate(String coordinate) {
        this.coordinates = coordinate;
    }
    public String getName() {
        return names;
    }
    public void setTytul(String name) {
        this.names = name;
    }
    public String getAutor() {
        return last_update;
    }
    public void setAutor(String last_updt) {
        this.last_update = last_updt;
    }
 
    public Satellites() {}
    public Satellites(String coordinate, String name, String last_updt) {
        this.coordinates = coordinate;
        this.names = name;
        this.last_update = last_updt;
    }
 
    @Override
    public String toString() {
        return "["+coordinates+"] - "+names+" - "+last_update;
    }
}
