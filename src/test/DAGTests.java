package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import palindrome.CharBitVector;
import palindrome.PalindromicPathFinder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DAGTests {

    private static PalindromicPathFinder dag;
    private static final Random RG = new Random();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    @BeforeClass
    public static void init(){
        dag = new PalindromicPathFinder();
    }

    @Test
    public void testGraph1(){
        String graph = "5\n" +
                "A 2\n" +
                "2 3\n" +
                "B 0\n" +
                "\n" +
                "B 1\n" +
                "3\n" +
                "A 2\n" +
                "1 4\n" +
                "C 0\n" +
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes()); // System.in vervangen om input te kunnen testen
        System.setIn(input);
        dag.main(); // Leest van System.in
        System.setIn(System.in); // niet vergeten terugzetten
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "3 AB 0 2 3", dag.pathsIntersection()); // BAB, ABA
    }

    @Test
    public void testGraph2(){
        String graph = "5\n" +
                "A 2\n" +
                "1 2\n" +
                "C 2\n" +
                "2 3\n" +
                "B 2\n" +
                "3 4\n" +
                "A 0\n" +
                "\n" +
                "B 1\n" +
                "3\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "4 / 0 2 4 3", dag.pathsIntersection()); // ABBA
    }

    @Test
    public void testGraph3(){
        String graph = "10\n" +
                "R 3\n" +
                "1 2 3\n" +
                "A 1\n" +
                "4\n" +
                "B 3\n" +
                "3 4 9\n" +
                "C 0\n" +
                "\n" +
                "C 2\n" +
                "3 5\n" +
                "E 1\n" +
                "6\n" +
                "C 1\n" +
                "7\n" +
                "A 1\n"+
                "8\n" +
                "R 0\n"+
                "\n" +
                "D 0\n" +
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "7 / 0 1 4 5 6 7 8", dag.pathsIntersection()); // RACECAR
    }

    @Test
    public void testDoublePath(){ // Er zijn 2 palindromische paden van maximale lengte tussen eenzelfde toppenpaar
        String graph = "6\n" +
                "A 2\n" +
                "1 2\n" +
                "B 1\n" +
                "3\n" +
                "C 1\n" +
                "4\n" +
                "B 1\n" +
                "5\n" +
                "C 1\n" +
                "5\n" +
                "A 0\n"+
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "4 A 0 1 3 5", dag.pathsIntersection());
    }

    @Test
    public void testGraph4L(){
        String graph = "17\n" +
                "A 2\n" +
                "1 2\n" +
                "B 1\n" +
                "3\n" +
                "B 1\n" +
                "7\n" +
                "C 2\n" +
                "4 5\n" +
                "D 2\n" +
                "5 6\n" +
                "E 0\n" +
                "\n" +
                "R 1\n" +
                "8\n" +
                "A 1\n"+
                "5\n" +
                "O 1\n"+
                "9\n" +
                "O 3\n" +
                "5 10 15\n"+
                "M 2\n" +
                "7 11\n" +
                "O 2\n"+
                "12 14\n" +
                "O 2\n"+
                "13 14\n" +
                "R 1\n" +
                "14\n" +
                "D 0\n"+
                "\n" +
                "P 1\n"+
                "16\n" +
                "O 1\n" +
                "10";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "9 / 4 6 8 9 10 11 12 13 14", dag.pathsIntersection()); // DROOMOORD
    }

    @Test
    public void testNoPaths(){
        String graph = "5\n" +
                "A 1\n" +
                "1\n" +
                "B 2\n" +
                "2 4\n" +
                "C 2\n" +
                "3 4\n" +
                "A 0\n" +
                "\n" +
                "D 0\n" +
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "0 /", dag.pathsIntersection());
    }

    @Test
    public void testMediumGraph(){
        String graph = "25\n" +
                "E 2\n" +
                "5 9\n" +
                "G 1\n" +
                "6\n" +
                "H 1\n" +
                "7\n" +
                "M 0\n" +
                "\n" +
                "D 0\n" +
                "\n" +
                "J 0\n" +
                "\n" +
                "M 2\n" +
                "13 10\n" +
                "E 1\n"+
                "19\n" +
                "X 1\n"+
                "3\n" +
                "A 0\n" +
                "\n" +
                "H 0\n" +
                "\n" +
                "O 1\n" +
                "8\n" +
                "H 2\n" +
                "4 20\n" +
                "R 1\n" +
                "17\n" +
                "E 1\n" +
                "10\n" +
                "K 2\n" +
                "21 11\n" +
                "T 1\n" +
                "11\n" +
                "K 1\n"+
                "15\n" +
                "A 0\n"+
                "\n" +
                "M 1\n" +
                "22\n" +
                "Q 1\n"+
                "13\n" +
                "R 1\n"+
                "19\n" +
                "L 0\n" +
                "\n" +
                "R 2\n" +
                "16 24\n"+
                "H 0\n"+
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertEquals("Incorrecte hoeveelheid of lengte", "6 / 6 13 17 15 21 19", dag.pathsIntersection());
    }

    @Test
    public void testLarge(){
        String graph = "49\n" +
                "H 4\n" +
                "1 7 8 23\n" +
                "R 1\n" +
                "2\n" +
                "Z 1\n" +
                "9\n" +
                "Z 1\n" +
                "11\n" +
                "T 1\n" +
                "12\n" +
                "T 1\n" +
                "13\n" +
                "Z 1\n" +
                "13\n" +
                "R 0\n"+
                "\n" +
                "G 0\n"+
                "\n" +
                "J 1\n" +
                "10\n" +
                "Z 1\n" +
                "11\n" +
                "T 0\n" +
                "\n" +
                "M 0\n" +
                "\n" +
                "M 0\n" +
                "\n" +
                "U 1\n" +
                "8\n" +
                "B 2\n" +
                "9 16\n" +
                "R 1\n" +
                "23\n" +
                "R 2\n"+
                "18 25\n" +
                "J 1\n"+
                "26\n" +
                "G 0\n" +
                "\n" +
                "B 1\n"+
                "19\n" +
                "Z 3\n"+
                "22 28 29\n" +
                "U 0\n" +
                "\n" +
                "H 0\n" +
                "\n"+
                "U 0\n"+
                "\n" +
                "T 0\n" +
                "\n" +
                "G 0\n" +
                "\n" +
                "U 0\n" +
                "\n" +
                "U 0\n" +
                "\n" +
                "J 1\n" +
                "36\n" +
                "P 1\n" +
                "24\n" +
                "M 1\n" +
                "39\n" +
                "Z 1\n"+
                "25\n" +
                "R 2\n"+
                "32 26\n" +
                "U 1\n" +
                "27\n" +
                "J 2\n" +
                "36 24\n" +
                "Z 0\n" +
                "\n" +
                "T 2\n" +
                "31 38\n" +
                "B 1\n" +
                "46\n" +
                "P 1\n" +
                "40\n" +
                "H 1\n" +
                "48\n" +
                "T 1\n" +
                "34\n" +
                "G 1\n"+
                "43\n" +
                "Z 1\n"+
                "44\n" +
                "G 0\n" +
                "\n" +
                "T 1\n"+
                "44\n" +
                "B 1\n"+
                "47\n" +
                "J 0\n" +
                "\n" +
                "Z 0\n" +
                "\n";
        ByteArrayInputStream input = new ByteArrayInputStream(graph.getBytes());
        System.setIn(input);
        dag.main();
        System.setIn(System.in);
        Assert.assertTrue("Incorrecte hoeveelheid of lengte", dag.pathsIntersection().startsWith("3 Z "));
    }

    @Test
    public void testRandomGraphs(){
        ByteArrayInputStream input;
        for(int i= 0; i < 5; i++){
            String graph = getRandomGraph(50, RG.nextInt(5)+ 5);
            System.out.println(graph);
            input = new ByteArrayInputStream(graph.getBytes());
            System.setIn(input);
            dag.main();
        }
        System.setIn(System.in);
    }

    @Test
    public void testInefficientComputing(){
        ByteArrayInputStream input;
        for(int i= 0; i < 1; i++){
            String graph = getRandomGraph(25, RG.nextInt(5)+ 5);
            System.out.println(graph);
            input = new ByteArrayInputStream(graph.getBytes());
            System.setIn(input);
            dag.main();
        }
        System.setIn(System.in);
    }

    private String getRandomGraph(int amountOfNodes, int amountOfNodeNames){
        int beginIndex = RG.nextInt(CHARACTERS.length()-amountOfNodeNames);
        String choices = CHARACTERS.substring(beginIndex, beginIndex+amountOfNodeNames);
        System.out.println(choices);
        StringBuilder graphBuilder = new StringBuilder();
        graphBuilder.append(amountOfNodes).append("\n");
        for(int i = 0; i < amountOfNodes-1; i++){
            int amountOfNeighbors = RG.nextInt(amountOfNodes - i-1);
            graphBuilder.append(choices.charAt(RG.nextInt(choices.length()))).append(" ");
            if(amountOfNeighbors != 0){
                Set<Integer> neighbors = new HashSet<>();
                neighbors.add(RG.nextInt(amountOfNodes-i-1) + i+1);
                for(int j = 1; j < amountOfNeighbors; j++){
                    neighbors.add(RG.nextInt(amountOfNodes -i-1) + i+1);
                }
                graphBuilder.append(neighbors.size()).append("\n");
                List<Integer> elements = new ArrayList<>(neighbors);
                graphBuilder.append(elements.get(0));
                for(int k = 1; k < elements.size(); k++){
                    graphBuilder.append(" ").append(elements.get(k));
                }
                graphBuilder.append("\n");
            } else {
                graphBuilder.append("0\n\n");
            }

        }
        graphBuilder.append(CHARACTERS.charAt(RG.nextInt(choices.length()))).append(" 0\n\n");
        return graphBuilder.toString();
    }

    @Test
    public void testFile() throws Exception{
        InputStream input = Files.newInputStream(Paths.get("C:/Users/Winni/Documents/graafje2.txt"));
        System.setIn(input);
        dag.main();
        System.out.println(dag.pathsIntersection());
    }

}
