package test;

import org.junit.Assert;
import org.junit.Test;
import palindrome.CharBitVector;

public class CharBitVectorTests {

    @Test
    public void testNumbers(){
        long set = 0;
        for(char ch = 48; ch <= 57; ch++){
            set = CharBitVector.add(set, ch);
        }
        Assert.assertEquals("Foutieve verwerking van nummer-karakters", "0123456789", CharBitVector.toString(set));
    }

    @Test
    public void testHoofdletters(){
        long set = 0;
        for(char ch = 65; ch <= 90; ch++){
            set = CharBitVector.add(set, ch);
        }
        Assert.assertEquals("Foutieve verwerking van hoofdletters", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", CharBitVector.toString(set));
    }

    @Test
    public void testKleineLetters(){
        long set = 0;
        for(char ch = 97; ch <= 122; ch++){
            set = CharBitVector.add(set, ch);
        }
        Assert.assertEquals("Foutieve verwerking van kleine letters", "abcdefghijklmnopqrstuvwxyz", CharBitVector.toString(set));
    }

    @Test
    public void testDoorElkaar(){
        long set = 0;
        set =CharBitVector.add(set,'q');
        set = CharBitVector.add(set,'7');
        set =CharBitVector.add(set,'O');
        set = CharBitVector.add(set,'N');
        set =CharBitVector.add(set,'I');
        set =CharBitVector.add(set,'0');
        set =CharBitVector.add(set,'q'); // expres dubbel
        set =CharBitVector.add(set,'a');
        Assert.assertEquals("Foutieve verwerking", "07INOaq", CharBitVector.toString(set));
    }

}
