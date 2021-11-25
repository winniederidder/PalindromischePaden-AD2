package palindrome;

/**
 * Uitbreiding van PalindromicPathFinder die ook omkan met cyclische directionele grafen
 */
public class DG extends PalindromicPathFinder{

    private boolean[][] visitedPairs;

    public static void main(String[] args) {
        PalindromicPathFinder dg = new DG();
        dg.main();
    }

    @Override
    protected void computeLongestPalindromicPaths(){
        try {
            super.computeLongestPalindromicPaths();
        } catch (UnboundPalindromicPathException ex){
            bestPath = Path.NO_PATH;
            bestCharBitVector = 0;
            System.out.println(ex.getMessage());
            System.out.println("0 /");
        }
    }

    @Override
    protected void findPath(int start, int end) {
        visitedPairs = new boolean[nodes.length][nodes.length];
        super.findPath(start, end);
    }

    @Override
    protected Path extendPath(int start, int end) throws UnboundPalindromicPathException{
        if(visitedPairs[start][end]){
            throw new UnboundPalindromicPathException("Cycle detected at " + start + " " + end);
        }
        visitedPairs[start][end] = true;
        return super.extendPath(start, end);
    }

}
