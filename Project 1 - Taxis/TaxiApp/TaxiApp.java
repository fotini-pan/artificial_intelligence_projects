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

    /*
        The 3 below methods are used to read the input data
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
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            ArrayList<String> values = new ArrayList<String>(Arrays.asList(line.split(",")));
            Node n = new Node(values);
            nodes.add(n);
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
        This method is used to compute the heuristic values of each node (assuming that we have one client of course)
    */

    public static void makeHeuristicValues(){
        double x1 = clients.get(0).getX();
        double y1 = clients.get(0).getY();
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
        makeHeuristicValues();
    }
}