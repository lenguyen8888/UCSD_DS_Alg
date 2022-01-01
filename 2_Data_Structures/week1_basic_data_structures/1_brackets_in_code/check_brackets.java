import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        int errorPos = -1;
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
                Bracket item = new Bracket(next, position);
                opening_brackets_stack.push(item);
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here
                if (opening_brackets_stack.size() > 0) {
                    Bracket matchItem = opening_brackets_stack.pop();
                    if (!matchItem.Match(next)) {
                        errorPos = position;
                        break;
                    }
                } else {
                    errorPos = position;
                    break;
                }

            }
        }

        if (errorPos < 0 && opening_brackets_stack.size() > 0) {
            Bracket item = opening_brackets_stack.pop();
            errorPos = item.position;
        }

        // Printing answer, write your code here
        if (errorPos < 0)
            System.out.println("Success");
        else
            System.out.println(errorPos + 1);
    }
}
