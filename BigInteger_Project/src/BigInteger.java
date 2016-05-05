import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "�Է��� �߸��Ǿ����ϴ�.";
    public static final int MAX = 200;
    
    int [] num = new int[MAX]; //number
    char sign; 
    
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");
    
    //constructor
    //Suppose 0<= i < 10
    public BigInteger(int i)
    {
    	sign = (i>=0? '+' : '-');
    	num[MAX-1] = i;
    }
 
    //Suppose each element is one-digit number & first element has sign
    public BigInteger(int[] num1)
    {
    	int i, j = (num1.length - 1);
    	sign = (num1[j]>0? '+' : '-');
    	for(i=MAX-1; j>=0; i--, j--){
    		num[i] = num1[j];
    	}
    	
    }
    
    public BigInteger(String s)
    {
    	if(s.charAt(0)=='+' || s.charAt(0)=='-'){
    		sign = s.charAt(0);
    		s = s.substring(1);
    	}else{
    		sign = '+';
    	}
    	for(int i=0 ; i<s.length(); i++){
    		num[MAX-s.length()+i] = Character.getNumericValue(s.charAt(i));
    	}
    }

    private int index(int [] arr){
    	int i=0;
    	while(arr[i]==0){
    		i++;
    		if(i==MAX) { return (i-1); }
    	}
    	return i;
    }
    
    public BigInteger add(BigInteger big)
    {
    	int i, temp1, temp2;
    	BigInteger result = new BigInteger(0);
    	
    	
    	if(sign == big.sign){
    		temp2=0;
        	if(index(big.num)-index(num)>=0){
        		for(i=MAX-1; i>=index(num)-1; i--){ //digit of result can be bigger than operands -> i>=index(num)-1
        			temp1 = num[i] + big.num[i] + temp2;
        			temp2 = temp1 / 10; //carry
        			result.num[i] = temp1 % 10;
        		}
        	}
        	else{
        		for(i=MAX-1; i>=index(big.num); i--){
        			temp1 = num[i] + big.num[i] + temp2;
        			temp2 = temp1 / 10; //carry
        			result.num[i] = temp1 % 10;
        			if(i==index(big.num) && temp2 > 0){ //consider digit gets bigger than digit of operands
        				result.num[i-1] = temp2;
        			}
        		}
        	}
        	result.sign = sign;
    	}
    	else{
    		if(sign == '+'){
    			big.sign = '+';
    			result = this.subtract(big);
    		}else{
    			this.sign = '+';
    			result = big.subtract(this);
    		}
    	}
    	
    	return result;
    }
 
    public BigInteger subtract(BigInteger big)
    {
    	int i, temp1, temp2;
    	BigInteger result = new BigInteger(0);
    	String max; //return which number is bigger? (big or this)
    	
    	if(index(big.num) - index(num) > 0){ //compare digits
    		max = "this";
    	}else if(index(big.num) - index(num) < 0){
    		max = "big";
    	}else{ //if two number has same digits, compare each digit
    		for(i=index(num); i<MAX; i++){
    			if(num[i] != big.num[i]) { break; }
    		}
    		if(i==MAX) { max = "this"; } // two number is same
    		else { max = (num[i] - big.num[i] > 0)? "this" : "big"; }
    	}
    	
    	
    	if(sign == big.sign){
    		temp2 = 0;
    		if(max.equals("this")){
        		for(i=MAX-1; i>=index(num); i--){
        			temp1 = num[i] - big.num[i] + temp2;
        			if(temp1 < 0){
        				temp1 += 10;
        				temp2 = -1; // carry
        			}else {
        				temp2 = 0;
        			}
        			result.num[i] = temp1 % 10;
        		}
        		result.sign = sign;
        	}
        	else{
        		for(i=MAX-1; i>=index(big.num); i--){
        			temp1 = big.num[i] - num[i] + temp2;
        			if(temp1 < 0){
        				temp1 += 10;
        				temp2 = -1; //carry
        			}else{
        				temp2 = 0;
        			}
        			result.num[i] = temp1 % 10;
        		}
        		result.sign = (big.sign == '+'? '-' : '+');
        	}
    	}
    	else{
    		if(sign=='+') {
    			big.sign = '+';
    			result = this.add(big);
    		}else{
    			big.sign = '-';
    			result = this.add(big);
    		}
    	}
    	
    	return result;
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	
    	int i, j, temp1, temp2;
    	BigInteger result = new BigInteger(0);
    	BigInteger temp = new BigInteger(0);
    	
    	
    	for(i=MAX-1; i>=index(big.num); i--){
    		temp2 = 0;
    		for(j=MAX-1; j>=index(num); j--){
    			temp1 = num[j] * big.num[i] + temp2;
    			temp2 = temp1 / 10; //carry
    			temp.num[j-(MAX-i-1)] = temp1 % 10;
    			if(j==index(num) && temp2 > 0){
    				temp.num[j-(MAX-i)] = temp2;
    			}
    		}
    		for(j=0; j<(MAX-1-i); j++) { temp.num[(MAX-1)-j]=0; }
    		
    		result = result.add(temp);
		}
    	
    	result.sign = (sign==big.sign? '+' : '-');
    	
    	return result;
    	
    }
 
    @Override
    public String toString()
    {
    	if(index(num)==MAX-1 && num[MAX-1]==0){ //consider num = 0
    		return "0";
    	}
    	int i;
    	String s = "";
    	if(sign == '-'){
    		s += '-';
    	}
    	for(i=index(num); i<MAX; i++){
    		s += String.valueOf(num[i]);
    	}
    	return s;
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
    	
    	input = input.replaceAll("\\s+", "");
    	
        char op;
        String s="";
        BigInteger result = new BigInteger(0);
        
        String patternStr = "([[+][-]]?)([0-9]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        
        //find 1st number
        while(matcher.find()){
        	s = input.substring(matcher.start(), matcher.end());
        	input = input.substring(matcher.end()); //delete 1st number from input
        	break;
        }
        
        BigInteger num1 = new BigInteger(s); //operand
        
        //input starts with operator
        op = input.charAt(0);
        input = input.substring(1);
        
        matcher = pattern.matcher(input);
        
        //find 2nd number
        while(matcher.find()){
        	s = input.substring(matcher.start(), matcher.end());
        }
        
        BigInteger num2 = new BigInteger(s); //operand
        
        
        switch(op){
        case '+' : result = num1.add(num2); break;
        case '-' : result = num1.subtract(num2); break;
        case '*' : result = num1.multiply(num2); break;
        }
        
        return result;
    }
    
    
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
    
}