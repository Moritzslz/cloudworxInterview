public class Anfrage {

    public Id Id;
    public Firma Firma;
    public Person Person;
    public boolean blocked;

    public Anfrage(Id Id, Firma firma, Person person) {
        this.Id = Id;
        this.Firma = firma;
        this.Person = person;
    }
}