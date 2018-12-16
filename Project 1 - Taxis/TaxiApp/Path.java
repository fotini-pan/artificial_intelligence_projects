import java.util.ArrayList;
import java.util.HashMap;

public class Path {
    private ArrayList<ArrayList<Node>> path;
    private int length;

    
    public Path(){
        this.path = new ArrayList<>();
    }

    public Path(ArrayList<ArrayList<Node>> path){
        this.path = path;
    }

    public ArrayList<ArrayList<Node>> getPath(){
        return this.path;
    }

    public void makePath(MapNode goal) {
        
    }
}