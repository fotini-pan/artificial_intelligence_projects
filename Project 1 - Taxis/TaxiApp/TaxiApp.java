import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class TaxiApp {
    private static ArrayList<Taxi> taxis = new ArrayList<>();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Node> nodes = new ArrayList<>();
    private static HashMap<String, MapNode> map = new HashMap<>();

    /*
        The 3 below methods are used to read the input data.
        readNotes() also produces the graph of the nodes
    */

    public static void readTaxis() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("taxis.csv"), "ISO-8859-7");
        scanner.useDelimiter(",");
        scanner.nextLine(); 
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            ArrayList<String> values = new ArrayList<String>(Arrays.asList(line.split(",")));
            Taxi t = new Taxi(values);
            taxis.add(t);
        }
        scanner.close();
        return;
    }

    public static void readNodes() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("nodes.csv"), "ISO-8859-7");
        scanner.useDelimiter(",");
        scanner.nextLine();
        MapNode prevMapNode = null;
        MapNode currMapNode = null;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            ArrayList<String> values = new ArrayList<String>(Arrays.asList(line.split(",")));
            Node n = new Node(values);
            nodes.add(n);
            String key = values.get(0) + values.get(1);
            currMapNode = map.get(key);
            if(currMapNode == null){
                MapNode newMapNode = new MapNode(values); 
                map.put(key, newMapNode);
                newMapNode.addRefNode(n);
                if(prevMapNode != null){
                    Node tempNode = prevMapNode.getRefNodeById(n.getId());
                    if(tempNode != null){
                        newMapNode.addCanGoNode(prevMapNode);
                        prevMapNode.addCanGoNode(newMapNode);    
                    }
                }
            }
            else{
                currMapNode.addRefNode(n);
                if(prevMapNode != null){
                    Node tempNode = prevMapNode.getRefNodeById(n.getId());
                    if(tempNode != null){
                        currMapNode.addCanGoNode(prevMapNode);
                        prevMapNode.addCanGoNode(currMapNode);    
                    }
                }
            }
            prevMapNode = currMapNode;
        }      
        scanner.close();
        return;
    }

    public static void readClients() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("client.csv"), "ISO-8859-7");
        scanner.useDelimiter(",");
        scanner.nextLine();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            ArrayList<String> values = new ArrayList<String>(Arrays.asList(line.split(",")));
            Client c = new Client(values);
            clients.add(c);
        }
        scanner.close();
        return;
    }

    /*
        This method is used to find the node of our map that has the less distance, spherical law of cosines against used,
        from our client. 
    */

    public static void findNodeOfClient(){
        double x1 = clients.get(0).getX();
        double y1 = clients.get(0).getY();
        double x2 = nodes.get(0).getX();
        double y2 = nodes.get(0).getY();
        double min = calculateDistance(x1, x2, y1, y2);
        int posMin = 0;
        for(int i = 1; i < nodes.size(); i++){
            Node n1 = nodes.get(i);
            x2 = n1.getX();
            y2 = n1.getY();
            double temp = calculateDistance(x1, x2, y1, y2);
            if(temp < min){
                min = temp;
                posMin = i;
            }
        }
        clients.get(0).setClientNode(nodes.get(posMin));
    }

    /*
        This method is used to find the node of our map that has the less distance, spherical law of cosines against used,
        from all the taxis given. 
    */

    public static void findNodeOfTaxi(){
        for(Taxi taxi : taxis){
            double x1 = taxi.getX();
            double y1 = taxi.getY();
            double x2 = nodes.get(0).getX();
            double y2 = nodes.get(0).getY();
            double min = calculateDistance(x1, x2, y1, y2);
            int posMin = 0;
            for(int i = 1; i < nodes.size(); i++){
                Node n1 = nodes.get(i);
                x2 = n1.getX();
                y2 = n1.getY();
                double temp = calculateDistance(x1, x2, y1, y2);
                if(temp < min){
                    min = temp;
                    posMin = i;
                }
            }
            taxi.setTaxiNode(nodes.get(posMin));
        }
    }
        
    /*
        This method is used to compute the heuristic value of each node from our goal, which is the coordinates of clientNode field 
        from our client.
    */

    public static void makeHeuristicValues(){
        double x1 = clients.get(0).getClientNode().getX();
        double y1 = clients.get(0).getClientNode().getY();
        for(Node node : nodes){
            double x2 = node.getX();
            double y2 = node.getY();
            double distance = calculateDistance(x1, x2, y1, y2);
            node.setHValue(distance);
        }
    }

    /*
        This method is using the simple spherical law of cosines formula (cos c = cos a cos b + sin a sin b cos C)
        which gives well-condi­tioned results down to distances as small as a few metres on the earth’s surface.
    */

    public static double calculateDistance(double lon1, double lon2, double lat1, double lat2) {
        final int R = 6371000;

        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δλ = Math.toRadians(lon2 - lon1);

        double distance = Math.acos(Math.sin(φ1)*Math.sin(φ2) + Math.cos(φ1)*Math.cos(φ2)*Math.cos(Δλ))*R;
       
        return distance;
    }


    public static void main(String[] args) throws FileNotFoundException {
        readTaxis();
        readNodes();
        readClients();
        findNodeOfClient();
        findNodeOfTaxi();
        makeHeuristicValues();
        for(String key : map.keySet()){
            MapNode m = map.get(key);
            ArrayList<Node> refNodes = m.getRefNodes();
            ArrayList<MapNode> canGoNodes = m.getCanGoNodes();
            System.out.println(m.getX() + ", " + m.getY());
            for(Node node : refNodes){
                System.out.println("    " + node.getX() + ", " + node.getY() + ", " + node.getName());
            }
            System.out.println("-----------------------");
            System.out.println();
            for(MapNode mapNode : canGoNodes){
                System.out.println("    " + mapNode.getX() + ", " + mapNode.getY());
            }
            System.out.println("===========================================================");
            System.out.println();
        }
    }
}