import java.util.ArrayList;

public class Client {
    private double x;
    private double y;
    private Node clientNode;

    public Client(double x, double y) {
        this.x = x;
        this.y = y;
        this.clientNode = null;
    }

    public Client(ArrayList<String> arr) {
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
    }

    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }

    public Node getClientNode(){
        return this.clientNode;
    }

    public void setClientNode(Node n){
        this.clientNode = n;
    }
}