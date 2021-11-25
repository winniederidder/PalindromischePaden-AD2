package palindrome;

public class Path {

    private int first;
    private int last;
    private Path next;

    public Path(int node){
        this(node, node);
    }

    public Path(int first, int last){
        this.first = first;
        this.last = last;
        this.next = null;
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    public void setNext(Path next) {
        this.next = next;
    }

    public Path getNext() {
        return next;
    }

    public int getLength(){
        int length = (first == last) ? 1 : 2;
        if(next != null){
            length += next.getLength();
        }
        return length;
    }

    public static final Path NO_PATH = new Path(-1, -1);
}