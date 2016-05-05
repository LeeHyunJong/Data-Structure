import java.io.*;

public class CalculatorTest
{
	public static void main(String args[])
	{
		//String s = "CONTRABAND";
		//System.out.println(s.compareTo("GONE WITH THE WIND"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}
	
	private static void command(String input)
	{
		long result; //result of input
		
		if(input.matches("(.*)([0-9])(\\s+)([0-9])(.*)")){
			// if there is no operator between two numbers => error
			// For example, 22 - 1 2, 2 3, etc
			System.out.println("ERROR");
		}else if(input.matches("(.*)(\\(+)(\\s*)(\\)+)(.*)")){
			// if there is nothing between two parenthesis
			System.out.println("ERROR");
		}else{
			
			InfixToPostfix trans = new InfixToPostfix(input.replaceAll("\\s+", ""));
			
			//Infix to Postfix
			trans.convert();
			
			Postfix p = new Postfix(trans.getPostfix());
			
			result = p.evaluate(); //check arithmetic error
			
			if(p.isPostfix() && !p.getArithmeticError()){
				//input is postfix and has no error
				trans.print();
				System.out.println(result);
			}else{
				System.out.println("ERROR");
			}
		}
		//trans.print();
		
		//System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
	}
	
}