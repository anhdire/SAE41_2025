import java.util.ArrayList;
import java.util.List;

public class Serie {

    private List<Position> positions;

    public Serie() {
        this.positions = new ArrayList<>();
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public int longueur() {
        return positions.size();
    }
}