import java.util.ArrayList;

public class Path {
    private ArrayList<Node> path;

    
    public Path(){
        this.path = new ArrayList<>();
    }

    public Path(ArrayList<Node> path){
        this.path = path;
    }

    public double getX(){
        return this.x;
    }

    public ArrayList<Node> getPath(){
        return this.path;
    }

    public void addNodeToPath(Node n){
        this.path.add(n);
    }
}