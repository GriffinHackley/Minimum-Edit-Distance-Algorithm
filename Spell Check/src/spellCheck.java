import java.io.*;
import java.util.*;

public class spellCheck {
    public static void main(String[] args) throws IOException {
        int maxEdit = 0;
        int[] numbers = new int[25];
        Vector maximalEdit = new Vector();

        Scanner line = new Scanner(new File("Misspelled Words.txt"));
        String fullLine;

        while(line.hasNext()){
            fullLine = line.nextLine();
            Scanner words = new Scanner(fullLine);
            words.useDelimiter("[->,]");
            Vector variables = new Vector();

            while(words.hasNext()){
                variables.add(words.next());
            }
            variables.remove("");
            for(int j = 1; j < variables.size(); j++){
                int number = MED(variables.get(0).toString(), variables.get(j).toString());
                numbers[number]++;
                if(number > maxEdit){
                    maxEdit = number;
                    maximalEdit.clear();
                    maximalEdit.add(variables.get(j));
                }
                else if(number == maxEdit){
                        maximalEdit.add(variables.get(j));
                }
            }
        }
                printFile(maxEdit, numbers, maximalEdit);
    }

    private static int MED(String notRight, String right){
        char[] incorrect = notRight.toCharArray();
        char[] correct = right.toCharArray();
        int table[][] = new int[incorrect.length + 1][correct.length + 1];

        for (int i = 0; i <= incorrect.length; i++) {
            for (int j = 0; j <= correct.length; j++) {
                if (i == 0)
                    table[i][j] = j;

                else if (j == 0)
                    table[i][j] = i;

                else if (incorrect[i - 1] == correct[j - 1])
                    table[i][j] = table[i - 1][j - 1];

                else
                    table[i][j] = 1 + Math.min(table[i][j - 1], Math.min(table[i - 1][j], table[i - 1][j - 1]));
            }
        }
        return table[incorrect.length][correct.length];
    }

    private static void printFile(int maxEdit, int[] numbers, Vector maximalEdit) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        for(int i = 1; i<=maxEdit; i++){
            writer.println(i + ": " + numbers[i]);
        }
        writer.println(maximalEdit);
        writer.close();
    }
}