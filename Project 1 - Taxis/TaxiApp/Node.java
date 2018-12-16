import java.util.ArrayList;

public class Node {
    private double x;
    private double y;
    private int id;
    private String name;

    public Node(double x, double y, int id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }

    public Node(ArrayList<String> arr) {
        this.x = Double.parseDouble(arr.get(0));
        this.y = Double.parseDouble(arr.get(1));
        this.id = Integer.parseInt(arr.get(2));
        try {
            this.name = arr.get(3);
        }
        catch (IndexOutOfBoundsException e){
            this.name = "-";
        }
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

    public String getName(){
        return this.name;
    }
}