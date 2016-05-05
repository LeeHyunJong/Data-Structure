
public class BigInteger_Test {
	public static void main(String[] args) {
		int [] x = {0};
		int [] y = {7, 6, 2, 8, 7};
		int i;
		
		System.out.println(y[0] + "," + y[1] + "," + y[2] + "," + y[3] + "," + y[4]);
		
		BigInteger input = new BigInteger("-1");
		
		BigInteger result = new BigInteger("-1");
		
		result = result.add(input);
		
		System.out.println(result.toString());
		
		
	}
}
