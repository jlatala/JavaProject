package agh.project;

public class Satellites {
	private String coordinates;
    private String name;
    private String last_update;
 
    public String getCoordinates() {
        return coordinates;
    }
    public void setCoordinate(String coordinate) {
        this.coordinates = coordinate;
    }
    public String getTytul() {
        return tytul;
    }
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
 
    public Ksiazka() {}
    public Ksiazka(int id, String tytul, String autor) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
    }
 
    @Override
    public String toString() {
        return "["+id+"] - "+tytul+" - "+autor;
}
