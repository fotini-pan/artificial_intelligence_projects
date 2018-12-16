import java.util.ArrayList;
import java.util.Comparator;

public class MapNode {
    private double x;
    private double y;
    private double h;
    private double g;
    private ArrayList<Node> refNodes;
    private ArrayList<MapNode> canGoNodes;
    private ArrayList<MapNode> parents;

    public MapNode(double x, double y){
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.refNodes = new ArrayList<>();
        this.canGoNodes = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    public MapNode(ArrayList<String> arr){
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
        this.g = 0;
        this.h = 0;
        this.refNodes = new ArrayList<>();
        this.canGoNodes = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }

    public ArrayList<Node> getRefNodes(){
        return this.refNodes;
    }

    public ArrayList<MapNode> getCanGoNodes(){
        return this.canGoNodes;
    }

    public ArrayList<MapNode> getParents(){
        return this.parents;
    }

    public void addRefNode(Node n){
        this.refNodes.add(n);
    }

    public void addCanGoNode(MapNode m){
        this.canGoNodes.add(m);
    }

    public void addParent(MapNode m){
        this.parents.add(m);
    }

    public void clearAndAddParent(MapNode m){
        this.parents.clear();
        this.parents.add(m);
    }

    public Node getRefNodeById(int id){
        Node n = null;
        for(Node node : this.refNodes){
            if(node.getId()==id){
                n = node;
            }
        }
        return n;
    }

    public double getH(){
        return this.h;
    }

    public void setH(double hvalue){
        this.h = hvalue;
    }

    public double getG(){
        return this.g;
    }

    public void setG(double g){
        this.g = g;
    }

    public double getF(){
        return (this.g + this.h);
    }
}

/*class MapNodeComparator implements Comparator<MapNode>{ 
    public int compare(MapNode mn1, MapNode mn2) { 
        if (mn1.getF() < mn2.getF()) {
            return 1;
        } 
        else if (mn1.getF() > mn2.getF()) {
            return -1;
        } 
        return 0; 
        } */ 
} 
