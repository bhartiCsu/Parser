import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class predictiveParser {
    private Stack<String> stack = new Stack<>();

    // The parsing table using nested maps
    private Map<String, Map<String, String>> parsingTable = new HashMap<>();
    {
        Map<String, String> eRow = new HashMap<>();
        eRow.put("a", "TQ");
        eRow.put("(", "TQ");
        parsingTable.put("E", eRow);

        Map<String, String> qRow = new HashMap<>();
        qRow.put("+", "+TQ");
        qRow.put("-", "-TQ");
        qRow.put("$", "ɛ");
        qRow.put(")", "ɛ");
        parsingTable.put("Q", qRow);

        Map<String, String> tRow = new HashMap<>();
        tRow.put("(", "FR");
        tRow.put("a", "FR");
        parsingTable.put("T", tRow);

        Map<String, String> rRow = new HashMap<>();
        rRow.put("*", "*FR");
        rRow.put("/", "/FR");
        rRow.put("+", "ɛ");
        rRow.put("-", "ɛ");
        rRow.put(")", "ɛ");
        rRow.put("$", "ɛ");
        parsingTable.put("R", rRow);

        Map<String, String> fRow = new HashMap<>();
        fRow.put("(", "(E)");
        fRow.put("a", "a");
        parsingTable.put("F", fRow);
    }

    public void parseInput(String input) {
        stack.push("$");
        stack.push("E");
        int i = 0;
        //System.out.println("Input: "+ input);
        while (!stack.isEmpty()) {
            String top = stack.pop();

            String nextChar = input.substring(i, i+1);
            if(top.equals("$") && nextChar.equals("$")){
                System.out.println("String is accepted/ valid.");
                break;
            }

            if(top.equals("$") && !nextChar.equals("$")){
                System.out.println("String is not accepted/ In valid");
                break;
            }
            if (isNonTerminal(top)) {
                Map<String, String> row = parsingTable.get(top);
                String production = row.get(nextChar);
                if (production == null) {
                    continue;
                }
                if (!production.equals("ɛ")) {
                    String[] symbols = production.split("");
                    for (int j = symbols.length - 1; j >= 0; j--) {
                        stack.push(symbols[j]);
                    }
                }
            } else if (!top.equals(nextChar)) {
                continue;
            } else {
                i++;
            }
            System.out.println("Stack: " + stack);
        }

    }

    private boolean isNonTerminal(String symbol) {
        return symbol.equals("E") || symbol.equals("Q") || symbol.equals("T") || symbol.equals("R") || symbol.equals("F");
    }
}

