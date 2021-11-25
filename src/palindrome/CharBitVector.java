package palindrome;

public class CharBitVector {


    public static long add(long set, char ch){
        return set | 1L << charToInt(ch);
    }

    public static long addAll(long set, char... chars){
        for(char c : chars){
            set = add(set, c);
        }
        return set;
    }

    /**
     * Deze methode veronderstelt dat enkel karakter in 0-9A-Za-z voorkomen
     * @param ch het karakter dat omgezet moet worden
     * @return de positie die het inneemt in de bitvector
     */
    private static int charToInt(char ch){
        if(ch <= 57){ // 0-9 heeft ascii-waarden 48-57, en deze chars nemen posities 0-9 in de bitvector in
            return ch - 48;
        } else if(ch <= 90){ // A-Z heeft ascii-waarden 65-90, en deze chars nemen posities 10-35 in
            return ch - 55;
        } else { // a-z heeft ascii-waarden 97-122, deze chars nemen posities 36-61 in
            return ch - 61;
        }
    }

    public static String toString(long bitvector){
        StringBuilder nodesString = new StringBuilder();
        int i = Long.numberOfTrailingZeros(bitvector);
        while(i < 62){
            nodesString.append(posToChar(i));
            bitvector = bitvector & ~(1L << i);
            i = Long.numberOfTrailingZeros(bitvector);
        }
        return nodesString.toString();
    }

    private static char posToChar(int pos){
        if(pos <= 9){
            return (char)(pos+48);
        } else if(pos <= 35){
            return (char)(pos + 55);
        } else {
            return (char)(pos + 61);
        }
    }
}
