public class InfixToPostfix {

	String infix;
	private String postfixBuffer="";
	private StackReferenceBased operatorStack = new StackReferenceBased();
	int result=0;
	
	//constructor
	public InfixToPostfix(String input){
		infix = input;
	}
	
	
	//modification from http://www.tutorialspoint.com/javaexamples/data_intopost.htm
	public void writeOperator(String s, int priority){
		
		int priority2 = 1;
		String op;
		
		//push operators to stack
		//pop until priority of input operator is less than operator existed in stack
		while(!operatorStack.isEmpty()){
			op = operatorStack.peek();
			if(op.equals("("))
				priority2 = 0;
			else if(op.equals("+") || op.equals("-"))
				priority2 = 1;
			else if(op.equals("*") || op.equals("/") || op.equals("~") || op.equals("%"))
				priority2 = 2;
			else //op=="^"
				priority2 = 3;
			
			if(priority2 < priority)
				break;
			else{
				if(priority2 == priority){
					if(s.equals("~"))
						break;
					else if(s.equals("^"))
						break;
				}
				postfixBuffer += (" " + operatorStack.pop());
			}
		}
		operatorStack.push(s);
		
		/*
		if(operatorStack.isEmpty())
			operatorStack.push(s);
		else{
			op = operatorStack.peek();
			if(op.equals("("))
				priority2 = 0;
			else if(op.equals("+") || op.equals("-") || op.equals("%"))
				priority2 = 1;
			else if(op.equals("*") || op.equals("/") || op.equals("~"))
				priority2 = 2;
			else //op=="^"
				priority2 = 3;
			
			if(priority2 < priority){
				operatorStack.push(s);
			}else{
				if(op.equals("~") && s.equals("~")){
					operatorStack.push(s);
				}else if(op.equals("^") && s.equals("^")){
					operatorStack.push(s);
				}else{
					postfixBuffer += (" " + operatorStack.pop());
					operatorStack.push(s);
				}
			}
		}
		*/
		
	}
	
	//copy from http://www.tutorialspoint.com/javaexamples/data_intopost.htm
	//pop stack until "(" is popped
	public void Parenthesis(){
		String op;
		while(!operatorStack.isEmpty()){
			op = operatorStack.pop();
			if(op.equals("(")){
				break;
			}else{
				postfixBuffer += (" " + op);
			}
		}
	}
	
	//modification from http://www.tutorialspoint.com/javaexamples/data_intopost.htm
	public void convert(){
		String temp;
		
		//value for distinguish operand
		//If isOperator == false(read operand), keep read numbers
		//If isOperator == true(read operator), print " " to distinguish numbers
		boolean isOperator = false;
		
		int start=0; // first index or index after "("(left parenthesis) or "-"(unary) 
		int left=0; // # of left parenthesis
		int right=0; // # of right parenthesis
		
		for(int i=0; i<infix.length(); i++){
			
			temp = infix.substring(i, i+1);
			switch(temp){
			  case "+":
				writeOperator(temp, 1);
				isOperator = true;
				break;
			  case "-":
				if(i==start){
					//if "-" is appear when i==start or number is not read before
					//think "-" as "~"
					writeOperator("~", 2);
					start++;
				}else{
					if(!isOperator){
						writeOperator(temp,1);
						isOperator = true;
					}else{
						writeOperator("~", 2);
					}
				}
				break;
			  case "%":
			  case "*":
			  case "/":
				writeOperator(temp, 2);
				isOperator = true;
				break;
			  case "^":
				writeOperator(temp, 3);
				isOperator = true;
				break;
			  case "(":
				operatorStack.push(temp);
				start = i+1;
				left++;
				break;
			  case ")":
				if(isOperator){ 
					//equation between parenthesis is not valid
					postfixBuffer += "@";
				}
				Parenthesis();
				right++;
				break;
			  default:
				if(isOperator) 
					//right after read operator -> next would be number
					postfixBuffer += (" " + temp);
				else
					postfixBuffer += temp;
				isOperator = false;
				break;
			}
		}
		
		while(!operatorStack.isEmpty()){
			postfixBuffer += (" " + operatorStack.pop());
		}
		
		//parenthesis does not meet pair -> error
		if(left!=right){
			postfixBuffer += "@"; //error would be detected in isPostfix() 
		}
		
	}
	
	public String getPostfix(){
		return postfixBuffer;
	}
	
	
	public void print(){
		System.out.println(postfixBuffer);
	}
}