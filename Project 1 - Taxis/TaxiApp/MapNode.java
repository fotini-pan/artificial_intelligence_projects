import java.util.ArrayList;

public class MapNode {
    private double x;
    private double y;
    private ArrayList<Node> refNodes;
    private ArrayList<MapNode> canGoNodes;

    public MapNode(double x, double y){
        this.x = x;
        this.y = y;
        this.refNodes = new ArrayList<>();
        this.canGoNodes = new ArrayList<>();

    }

    public MapNode(ArrayList<String> arr){
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
        this.refNodes = new ArrayList<>();
        this.canGoNodes = new ArrayList<>();
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

    public void addRefNode(Node n){
        this.refNodes.add(n);
    }

    public void addCanGoNode(MapNode m){
        this.canGoNodes.add(m);
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
}