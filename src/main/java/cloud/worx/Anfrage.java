package cloud.worx;

public class Anfrage {

    public Id Id;
    public Firma Firma;
    public Person Person;
    public boolean blocked;
    private String state;

    public Anfrage(Id Id, Firma firma, Person person) {
        this.Id = Id;
        this.Firma = firma;
        this.Person = person;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}