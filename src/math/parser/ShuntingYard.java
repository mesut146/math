package math.parser;

import java.util.*;

class ShuntingYard
{
    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    // Supported operators
    private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>();
    static {
        OPERATORS.put("+", new int[] { 1, LEFT_ASSOC });
        OPERATORS.put("-", new int[] { 1, LEFT_ASSOC });
        OPERATORS.put("*", new int[] { 2, LEFT_ASSOC });
        OPERATORS.put("/", new int[] { 2, LEFT_ASSOC });
        OPERATORS.put("^", new int[] { 3, RIGHT_ASSOC });
		OPERATORS.put("#", new int[] { 3, RIGHT_ASSOC });
        OPERATORS.put("!", new int[] { 4, LEFT_ASSOC});
    }

    private static boolean isOperator(Token token)
    {
        return OPERATORS.containsKey(token.name);
    }

    private static boolean isAssociative(Token token, int type)
    {
        return OPERATORS.get(token.name)[1] == type;
    }

    private static final int cmpPrecedence(Token token1, Token token2)
    {
        return OPERATORS.get(token1.name)[0] - OPERATORS.get(token2.name)[0];
    }

    public static List<Token> infixToRPN(List<Token> inputTokens)
    {
        List<Token> out = new LinkedList<>();
        Stack<Token> stack = new Stack<>();
		//System.out.println("tok="+inputTokens);
		boolean m=true;
        for (Token token : inputTokens)
        {
            if (token.type == TokenType.Operator)
            {
                while (!stack.empty() && isOperator(stack.peek()))
                {
                    if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                        || (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) < 0))
                    {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            }
            else if (token.type == TokenType.Open)
            {
                stack.push(token);
            }
            else if (token.type == TokenType.Close)
            {
				m = false;
                while (!stack.empty() && stack.peek().type != TokenType.Open)
                {
                    out.add(stack.pop());
                }
                stack.pop();
            }else if(token.type==TokenType.Comma){
                stack.push(token);
            }
            else
            {
				m = false;
                out.add(token);
            }
        }
        while (!stack.empty())
        {
            out.add(stack.pop());
        }
        return out;
    }
}
