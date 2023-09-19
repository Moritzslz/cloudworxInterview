import java.util.List;

public class Firma {

    public Id id;
    public List<GesperrtePLZ> gesperrtePLZ;

    public Firma(Id id, List<GesperrtePLZ> gesperrtePLZ) {
        this.id = id;
        this.gesperrtePLZ = gesperrtePLZ;
    }
}
