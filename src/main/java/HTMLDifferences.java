import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class HTMLDifferences {
    public static void main(String[] args) throws Exception {

        List<String> input1 = readFile("src/main/resources/input3.html");
        List<String> input2 = readFile("src/main/resources/input4.html");


        Patch patch = DiffUtils.diff(input1, input2);
        List<Delta> deltas = patch.getDeltas();


        StringBuilder rulesBuilder = new StringBuilder();
        for (Delta delta : deltas) {
            switch (delta.getType()) {
                case INSERT:
                    rulesBuilder.append("INSERT " + delta.getRevised() + "\n");
                    break;
                case DELETE:
                    rulesBuilder.append("REMOVE " + delta.getOriginal() + "\n");
                    break;
                case CHANGE:
                    rulesBuilder.append("INSERT " + delta.getRevised() + "\n");
                    rulesBuilder.append("REMOVE " + delta.getOriginal() + "\n");
                    break;

            }
        }


        FileWriter writer = new FileWriter("output.txt");
        writer.write(rulesBuilder.toString());
        writer.close();
    }

    private static List<String> readFile(String path) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}