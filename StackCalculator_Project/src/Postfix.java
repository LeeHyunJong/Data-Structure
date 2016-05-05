public class Postfix {
	
	String[] post; //array for postfix splitted by space
	
	StackReferenceBased stack = new StackReferenceBased(); //stack for evaluating
	
	private boolean error = false; //If error is false, then arithmetic error exists
	
	//constructor
	public Postfix(String input){
		post = input.split(" ");
	}
	
	//calculate result of postfix
	public long evaluate(){
		long operand1, operand2, result = 0;
		
		
		try{
			for(int i=0; i<post.length; i++){
				if(post[i].matches("[0-9]+")){
					stack.push(post[i]);
					if(post.length==1){ //postfix has only a number
						result = Long.parseLong(stack.pop());
					}
				}else{
					if(post[i].equals("~")){ //unary operator
						result = -1 * Long.parseLong(stack.pop());
					}else{ //binary operator
						operand2 = Long.parseLong(stack.pop());
						operand1 = Long.parseLong(stack.pop());
						if(post[i].equals("+"))
							result = operand1 + operand2;
						else if(post[i].equals("-"))
							result = operand1 - operand2;
						else if(post[i].equals("*"))
							result = operand1 * operand2;
						else if(post[i].equals("/")){
							try{
								result = operand1 / operand2;
							}catch(ArithmeticException e){
								//case : operand2==0
								error = true;
							}
						}else if(post[i].equals("^")){
							if(operand2 >= 0){
								result = (long)Math.pow(operand1, operand2);
							}else{ //0^y (y<0) is error
								error = true;
							}
						}else{ //post[i].equals("%")
							try{
								result = operand1 % operand2;
							}catch(ArithmeticException e){
								//case : operand2==0
								error = true;
							}
						}
					}
					stack.push(new Long(result).toString());
				}
			}
			
		}catch(NullPointerException e){ 
			//arithmetic error occur (operator is too many)
			error = true;
		}catch(NumberFormatException e){
			//operand is bigger than LONG_MAX
			error = true;
		}
		return result;
	}
	
	public boolean isPostfix(){
		int lastChar = endPost(0,post.length-1);
		if(lastChar == 0) return true;
		else return false;
	}
	
	public int endPost(int first, int last){
		if(first > last) return -1;
		
		//postfix = <postfix1> <postfix2> <operator>
		if(post[last].matches("([0-9]+)")) return last; //base case (post[last] is identifier)
		else if(post[last].matches("[-+*/%~]") || post[last].equals("^")){
			int lastEnd = endPost(first, last-1); //check <postfix2>
			if(lastEnd == 0) return 0;
			if(lastEnd == -1) return -1;
			else return endPost(first, lastEnd-1); //check <postfix1>
		}else{
			return -1;
		}
	}
	
	//return whether error exist or not
	public boolean getArithmeticError(){
		return error;
	}
	
}
