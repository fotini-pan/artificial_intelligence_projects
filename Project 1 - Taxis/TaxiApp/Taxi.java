import java.util.ArrayList;
import java.util.HashMap;

public class Taxi {
    private double x;
    private double y;
    private int id;
    private Node taxiNode;
    private ArrayList<ArrayList<MapNode>> pathToClient;

    public Taxi(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.pathToClient = null;
    }

    public Taxi(ArrayList<String> arr) {
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
        this.id = Integer.parseInt(arr.get(2));
        this.pathToClient = null;
    }

    public ArrayList<ArrayList<MapNode>> getPathToClient(){
        return this.pathToClient;
    }

    public void setPathToClient(ArrayList<ArrayList<MapNode>> pathToClient){
        this.pathToClient = pathToClient;
    }

    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }

    public int getId(){
        return this.id;
    }

    public Node getTaxiNode(){
        return this.taxiNode;
    }

    public void setTaxiNode(Node n){
        this.taxiNode = n;
    }

    public MapNode getMapNodeOfTaxi(HashMap<String, MapNode> hm){
        String key = Double.toString(this.taxiNode.getX())+ Double.toString(this.taxiNode.getY());
        return hm.get(key);
    }
}