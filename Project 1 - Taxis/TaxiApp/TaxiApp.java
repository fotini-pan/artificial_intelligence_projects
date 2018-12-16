import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TaxiApp {
    private static ArrayList<Taxi> taxis = new ArrayList<>();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Node> nodes = new ArrayList<>();
    private static HashMap<String, MapNode> map = new HashMap<>();
    private static ArrayList<MapNode> open = new ArrayList<>();
    //private static PriorityQueue<MapNode> open = new PriorityQueue<>(1, new MapNodeComparator());
    private static ArrayList<MapNode> closed = new ArrayList<>();
    //private static ArrayList<Path> paths = new ArrayList<>();

    /*
        The 3 below methods are used to read the input data.
        readNodes() also produces the graph of the nodes
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
                        if(!(prevMapNode.getCanGoNodes().contains(newMapNode))){
                            prevMapNode.addCanGoNode(newMapNode);
                        }
                        if(!(newMapNode.getCanGoNodes().contains(prevMapNode))){
                            newMapNode.addCanGoNode(prevMapNode);
                        }
                    }
                }
            }
            else{
                currMapNode.addRefNode(n);
                if(prevMapNode != null){
                    Node tempNode1 = prevMapNode.getRefNodeById(n.getId());
                    Node tempNode2 = prevMapNode.getRefNodeByXY(n.getX(), n.getY());
                    if(tempNode1 != null && tempNode2==null){
                        if(!(prevMapNode.getCanGoNodes().contains(currMapNode))){
                            prevMapNode.addCanGoNode(currMapNode);
                        }
                        if(!(currMapNode.getCanGoNodes().contains(prevMapNode))){
                            currMapNode.addCanGoNode(prevMapNode);
                        }    
                    }
                }
            }
            prevMapNode = map.get(key);
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
        for(String key : map.keySet()){
            MapNode m = map.get(key);
            double x2 = m.getX();
            double y2 = m.getY();
            double distance = calculateDistance(x1, x2, y1, y2);
            m.setH(distance);
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

    /*
        This method is used to sort the open list.
    */

    public static void sortOpenList(){
        Collections.sort(open, new Comparator<MapNode>(){

            @Override
            public int compare(MapNode m1, MapNode m2){
                if(m1.getF() < m2.getF()){
                    return -1;
                }
                else if(m1.getF() > m2.getF()){
                    return 1;
                }    
                else{
                    return 0;
                }
            }
        });
        return ;
    }

    /*
        This function is used to perform the A Star algorithm.
    */

    public static double aStarSearch(MapNode S, MapNode G){
        open.add(S);
        while (!open.isEmpty()) {
            sortOpenList();
            MapNode current = open.remove(0);
            if (current == G) {
                return current.getG();
            }
            closed.add(current);
            double x1 = current.getX();
            double y1 = current.getY();
            ArrayList<MapNode> children = current.getCanGoNodes();
            for (MapNode m: children) {
                double x2 = m.getX();
                double y2 = m.getY();
                double oldG = m.getG();
                double distance = calculateDistance(x1, x2, y1, y2);
                double newG = current.getG() + distance;
                if (oldG == 0) {
                    m.setG(newG);
                    m.addParent(current);
                }
                else if (oldG > newG) {
                    m.setG(newG);
                    m.clearAndAddParent(current);
                }
                else if (oldG == newG) {
                    m.addParent(current);
                }
                if (!open.contains(m) && !closed.contains(m)) {
                    open.add(m);
                }
            }
        }
        return -1;
    }

    public static void pathFinder(MapNode stopNode, MapNode curr) {
        System.out.println(curr.getX() + ", " + curr.getY());
        curr.setIsExplored(true);
        if (curr == stopNode) {
            return;
        }
        ArrayList<MapNode> parents = curr.getParents();
        for (MapNode p: parents) {
            if (p.getIsExplored()) {
                continue;
            }
            pathFinder(stopNode, p);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        readTaxis();
        readNodes();
        readClients();
        findNodeOfClient();
        findNodeOfTaxi();
        makeHeuristicValues();
        Client client = clients.get(0);
        MapNode goal = client.getMapNodeOfClient(map);
        MapNode start = taxis.get(0).getMapNodeOfTaxi(map);
        double cost = aStarSearch(start, goal);
        //pathFinder(start, goal);
        System.out.println(cost);
        /*MapNode m = goal; 
        int count = 0;
        while(true){
            count = count + 1;
            System.out.println(m.getX() + ", " + m.getY());
            m = m.getParents().get(0);
            if(m == start){
                System.out.println(m.getX() + ", " + m.getY());
                break;
            }
        }
        System.out.println(count);*/
        for(String key : map.keySet()){
            MapNode m = map.get(key);
            if(m.getParents().size()>0){
                System.out.println(m.getParents().size());
            }
        }
    }
    
}