package palindrome;
import java.util.LinkedList;
import java.util.List;

public class Node {

    // Methodes spreken voor zich
    private int index;
    private char symbol;
    private List<Integer> isConnectedTo = new LinkedList<>();
    private List<Integer> canBeReachedFrom = new LinkedList<>();

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void addNeighbors(int neighbor) {
        isConnectedTo.add(neighbor);
    }

    public void addIncomingEdge(int fromNode){
        canBeReachedFrom.add(fromNode);
    }

    public int getIndex() {
        return index;
    }

    public char getSymbol() {
        return symbol;
    }

    public List<Integer> getNeighbors() {
        return isConnectedTo;
    }

    public List<Integer> getNodesThatReachThisNode(){
        return canBeReachedFrom;
    }

    public boolean canBeReached(){
        return !canBeReachedFrom.isEmpty();
    }

    public boolean canBeReachedFrom(int fromNode){
        return canBeReachedFrom.contains(fromNode);
    }

    public boolean hasNeighbors(){
        return !isConnectedTo.isEmpty();
    }

    public boolean hasNeighbor(int neighbor){
        return isConnectedTo.contains(neighbor);
    }

    @Override
    public String toString() {
        return symbol + " " + index;
    }

}
