package palindrome;

import java.util.LinkedList;
import java.util.Scanner;

public class PalindromicPathFinder {

    protected Node[] nodes;
    protected Path bestPath;
    protected long bestCharBitVector;
    protected Path[][] paths;
    protected long[][] charBitvectors;

    /**
     * Leest de input in via de Scanner en bouwt de interne graafvoorstelling op
     * @param graphReader de Scanner die de input bevat
     */
    protected void parseInput(Scanner graphReader){
        int amountOfNodes = Integer.parseInt(graphReader.nextLine()); // eerste lijn is steeds het aanal toppen in de graaf
        paths = new Path[amountOfNodes][amountOfNodes];
        nodes = new Node[amountOfNodes];
        charBitvectors = new long[amountOfNodes][amountOfNodes];
        bestCharBitVector = 0;
        bestPath = null;
        char singleSymbol = '0';
        for(int i = 0; i < amountOfNodes; i++){
            nodes[i] = new Node(); // alle Nodes aanmaken om later eenvoudig te kunnen invullen met data
        }
        for (int i = 0; i < amountOfNodes; i++){
            String[] nodeDescription = graphReader.nextLine().split(" "); // Eerste lijn bevat symbool en aantal buren
            Node node = nodes[i]; // ophalen voor eenvoud
            node.setSymbol(nodeDescription[0].charAt(0)); // is steeds 1 Character
            if(bestPath == null){
                bestPath = new Path(i);
                singleSymbol = node.getSymbol();
                bestCharBitVector = CharBitVector.add(0, singleSymbol);
            } else if(singleSymbol != node.getSymbol()){
                bestCharBitVector = 0;
            }
            // aangezien de grafen acyclisch zijn vermijdt dit if-statements en overtollige berekeningen
            charBitvectors[i][i] = CharBitVector.add(0, nodes[i].getSymbol()); // bitvector is symbool zelf
            node.setIndex(i);
            if(Integer.parseInt(nodeDescription[1]) != 0){
                String[] neighbors = graphReader.nextLine().split(" ");
                for(String s : neighbors){ // Strings omzetten naar getallen voor eenvoud van gebruik
                    int neighbor = Integer.parseInt(s);
                    nodes[i].addNeighbors(neighbor);
                    nodes[neighbor].addIncomingEdge(i); // Elke top zijn buren en inkomende boogrelaties laten bijhouden voor later efficiënter te kunnen zoeken
                }
            } else {
                graphReader.nextLine(); // Geen buren -> lege lijn weggooien
            }
        }
    }

    /**
     * Voor de eenvoud heet deze methode ook main, ze bundelt het inlezen van de invoer en de verwerking van de opgebouwe graaf
     * Hulpmethode om via Junit-tests eenvoudig het resultaat te kunnen controleren
     */
    public void main(){
        Scanner graphReader = new Scanner(System.in); // Maakt lezen en verwerken van invoer zeer eenvoudig
        while(graphReader.hasNextLine()){ // Opdat meerdere grafen na elkaar verwerkt kunnen worden
            parseInput(graphReader);
            computeLongestPalindromicPaths(); // Maximale paden bepalen, als ze bestaan, met deze info
        }
    }

    protected void computeLongestPalindromicPaths(){
        for(int i = 0;  i < nodes.length; i++){
            findPath(i, i);
            for(int neighbor: nodes[i].getNeighbors()){
                if(nodes[neighbor].getSymbol() == nodes[i].getSymbol()) {
                    findPath(i, neighbor);
                }
            }
        }
        System.out.println(pathsIntersection());
    }

    protected void findPath(int start, int end){
        extendPath(start, end);
        cacheIfBetter(start, end);
    }

    protected Path extendPath(int start, int end){
        if(paths[start][end] == null){
            Path path = new Path(start, end);
            long pathVector = 0;
            for(int previous : nodes[start].getNodesThatReachThisNode()){
                for(int next: nodes[end].getNeighbors()){
                    if(nodes[previous].getSymbol() == nodes[next].getSymbol()){
                        Path extension = extendPath(previous, next);
                        if(path.getNext() == null || path.getNext().getLength() < extension.getLength()){
                            path.setNext(extension);
                            pathVector = charBitvectors[previous][next];
                        } else if(path.getNext().getLength() == extension.getLength()){
                            pathVector &= charBitvectors[previous][next];
                        }
                    }
                }
            }
            charBitvectors[start][end] = CharBitVector.addAll(pathVector, nodes[start].getSymbol(), nodes[end].getSymbol());
            paths[start][end] = path;
        }
        return paths[start][end];
    }

    private String toString(Path path){
        if(path == Path.NO_PATH){
            return "";
        }
        LinkedList<Integer> indices = new LinkedList<>();
        Path current = path;
        if(current.getFirst() == current.getLast()){
            indices.add(current.getFirst());
        } else {
            indices.addFirst(current.getFirst());
            indices.addLast(current.getLast());
        }
        while(current.getNext() != null){
            current = current.getNext();
            indices.addFirst(current.getFirst());
            indices.addLast(current.getLast());
        }
        return indices.stream().map(Object::toString).reduce("", (x, y) -> (x + " " + y));
    }

    /**
     * Voorziet de omzetting van de berekende waarden naar de gewenste outputvorm; hulpmethode om Junit-tests te vergemakkelijken
     * @return de gewenste String-voorstelling van het resultaat
     */
    public String pathsIntersection(){
        return bestPath.getLength() + " " + CharBitVector.toString(bestCharBitVector) + toString(bestPath);
    }

    protected void cacheIfBetter(int start, int end){
        if(paths[start][end].getLength() > bestPath.getLength()){
            bestPath = paths[start][end];
            bestCharBitVector = charBitvectors[start][end];
        } else if(paths[start][end].getLength() == bestPath.getLength()){
            bestCharBitVector &= charBitvectors[start][end];
        }
    }
}
