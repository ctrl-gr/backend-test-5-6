import java.io.*;


public class HTMLdiffGenerator {
    public static void main(String[] args) throws Exception {
        String inputFilePath = "src/main/resources/input4.html";
        String outputFilePath = "src/main/resources/output.html";
        String rulesFilePath = "output.txt";

        try {

            BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
            StringBuilder inputBuilder = new StringBuilder();
            String line;
            while ((line = inputReader.readLine()) != null) {
                inputBuilder.append(line);
            }
            inputReader.close();

            String input = inputBuilder.toString();


            BufferedReader rulesReader = new BufferedReader(new FileReader(rulesFilePath));
            String rule;
            while ((rule = rulesReader.readLine()) != null) {
                String[] tokens = rule.split("\\[|\\]");


                String command = tokens[0];
                String[] positionAndSize = tokens[1].split(", ");
                int position = Integer.parseInt(positionAndSize[1].split(": ")[1]);
                int size = Integer.parseInt(positionAndSize[0].split(": ")[1]);

                String[] lines = tokens[2].split(": ")[0].split(", ");
                String replacement = String.join("", lines);

                if (command.equals("INSERT ")) {
                    input = input.substring(0, position) + replacement + input.substring(position);
                } else if (command.equals("REMOVE ")) {
                    int endPosition = position + size;
                    input = input.substring(0, position) + input.substring(endPosition);
                } else if (command.equals("CHANGE")) {
                    int endPosition = position + size;
                    input = input.substring(0, position) + replacement + input.substring(endPosition);
                }
            }
            rulesReader.close();


            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath));
            outputWriter.write(input);
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}