import java.util.*;
import java.io.*;

public class RobotMap {

    private static RobotMap theMap = new RobotMap();
    private HashMap<String,MapNode> nodes;

    private RobotMap() {
        try {
            Scanner input = new Scanner(new File("../../corobot_map/map/waypoints.csv"));
            String nodeline = input.nextLine(); // toss the headers of the csv
            nodes = new HashMap<String,MapNode>();
            while (input.hasNextLine()) {
                nodeline = input.nextLine();
                String[] parts = nodeline.split(",");
                // name xpix ypix xm ym type nbrs...
                MapNode newnode = new MapNode();
                newnode.name = parts[0].toUpperCase();
                newnode.x = Double.parseDouble(parts[3]);
                newnode.y = Double.parseDouble(parts[4]);
                newnode.nbrs = new LinkedList<String>();
                for (int i = 6; i < parts.length; i++)
                    if (!(parts[i].equals("")))
                        newnode.nbrs.add(parts[i].toUpperCase());
                nodes.put(newnode.name,newnode);
            }
        } catch (IOException e) {
            System.err.println("Map cannot be built!");
            System.err.println(e);
        }
    }

    public static String getClosestNode(double x, double y) {
        double minsqdist = 99999;
        String closest = "";
        for (MapNode mn : theMap.nodes.values()) {
            double sqdist = (x - mn.x) * (x - mn.x) + (y - mn.y) * (y - mn.y);
            if (sqdist < minsqdist) {
                minsqdist = sqdist;
                closest = mn.name;
            }
        }
        return closest;
    }
    
    public static HashMap<String,MapNode> getNodes() {
        return theMap.nodes;
    }

    public static Set<String> getNodeNames() {
        return theMap.nodes.keySet();
    }

    public static MapNode getNode(String name) {
        name = name.toUpperCase();
        return theMap.nodes.get(name);
    }

    public static boolean isNode(String name) {
        name = name.toUpperCase();
        return theMap.nodes.containsKey(name);
    }
}
class MapNode {
    String name;
    double x, y; // in meters
    List<String> nbrs;
}