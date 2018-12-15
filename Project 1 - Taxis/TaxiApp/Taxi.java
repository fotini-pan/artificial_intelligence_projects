import java.util.ArrayList;

public class Taxi {
    private double x;
    private double y;
    private int id;
    private Node taxiNode;

    public Taxi(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Taxi(ArrayList<String> arr) {
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
        this.id = Integer.parseInt(arr.get(2));
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
}