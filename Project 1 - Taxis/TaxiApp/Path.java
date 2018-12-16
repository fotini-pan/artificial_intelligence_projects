import java.util.ArrayList;
import java.util.HashMap;

public class Path {
    private ArrayList<String> path;
    private double totalCost;
    private Taxi taxi;
    
    public Path(Taxi taxi){
        this.taxi = taxi;
        this.totalCost = 0;
        this.path = new ArrayList<>();
    }

    public ArrayList<String> getPath(){
        return this.path;
    }

    public void addNodeToPath(double x, double y){
        String coordinates = Double.toString(x) + ", " + Double.toString(y);
        this.path.add(coordinates);
    }

    public void setTotalCost(double totalCost){
        this.totalCost = totalCost;
    }

    public double getTotalCost(){
        return this.totalCost;
    }

    public Taxi getTaxi(){
        return taxi;
    }
}