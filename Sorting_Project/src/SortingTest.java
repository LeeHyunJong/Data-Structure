import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		int temp;
		if(value.length>1){
			for(int i=value.length-2; i>=0; i--){
				for(int j=0; j<=i; j++){
					if(value[j] > value[j+1]){
						temp = value[j];
						value[j] = value[j+1];
						value[j+1] = temp;
					}
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		int i, j;
		int copy;
		
		for(i=1; i<value.length; i++){
			copy = value[i];
			
			for(j=0; j<i; j++){
				if(value[j] > value[i])
					break;
			}
			
			//shift operation
			for(int k=i-1; k>=j; k--)
				value[k+1] = value[k];
			
			value[j] = copy;
		}
		
		// TODO : Insertion Sort 를 구현하라.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		value = heapsort(value, value.length);
		return (value);
	}
	
	//copy from lecture ppt Chapter12
	private static void percolateDown(int[] key, int i, int n){
		int child = 2*i + 1;
		int rightChild = 2*i + 2;
		int temp;
		
		if(child<n){
			if(rightChild<n && key[child] < key[rightChild]){
				child = rightChild; //index of larger child
			}
			if(key[i] < key[child]){
				temp = key[i];
				key[i] = key[child];
				key[child] = temp;
				percolateDown(key, child, n);
			}
		}
		
	}
	
	private static int[] heapsort(int[] value, int n){
		
		int temp;
		
		//build initial heap : value[0 ... (n-1)]
		for(int i=(n-1)/2; i>=0; i--){
			percolateDown(value, i, n);
		}
		//delete one by one
		for(int i=n-2; i>=0; i--){
			temp = value[0];
			value[0] = value[i+1];
			value[i+1] = temp;
			percolateDown(value, 0, i);
		}
		
		return value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		
		if(value.length>1){
			int[] value1 = new int[value.length/2];
			int[] value2 = new int[value.length - value.length/2];
			
			System.arraycopy(value, 0, value1, 0, value.length/2);
			System.arraycopy(value, value.length/2, value2, 0, value.length - value.length/2);
			value1 = DoMergeSort(value1);
			value2 = DoMergeSort(value2);
			value = merge(value1, value2);
		}
		
		return (value);
	}
	
	private static int[] merge(int[] a, int[] b){
		int[] c = new int[a.length + b.length];
		int i=0, j=0, index=0;
		
		while(i<a.length && j<b.length){
			if(a[i] <= b[j]){
				c[index] = a[i];
				i++;
			}else{
				c[index] = b[j];
				j++;
			}
			index++;
		}
		if(j==b.length){
			for(; i<a.length;i++, index++)
				c[index] = a[i];
		}else{ //i==a.length
			for(; j<b.length; j++, index++)
				c[index] = b[j];
		}
		
		return c;
	}
	   
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		QuickSort(value, 0, value.length-1);
		return (value);
	}
	
	private static void QuickSort(int[] value, int first, int last){
		
		if(last-first>=1){
			int pivot = value[first];
			int i = first+1, j = last, temp;
			
			if(i==j && pivot > value[i]){
				value[first] = value[i];
				value[i] = pivot;
			}
			else{
				while(i<j){
					if(value[i] <= pivot && value[j] >= pivot){
						i++;
						j--;
					}
					else if(value[i] <= pivot && value[j] < pivot){
						i++;
					}
					else if(value[i] > pivot && value[j] > pivot){
						j--;
					}
					else{ //value[i] > pivot && value[j] <= pivot
						temp = value[i];
						value[i] = value[j];
						value[j] = temp;
					}
				}
				value[first] = value[j];
				value[j] = pivot;
				
				QuickSort(value, first, j-1);
				QuickSort(value, j+1, last);
			}
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		
		int digitMax = 0, min = 0, temp, i;
		
		//find maximum digit
		for(i=0; i<value.length; i++){
			temp = digit(value[i]);
			digitMax = digitMax < temp? temp : digitMax;
			min = value[i] < min? value[i] : min;
		}
		
		//Make list of queue
		List<Queue<Integer>> list = new ArrayList<Queue<Integer>>();
		
		for(i=0; i<10; i++){
			Queue<Integer> queue = new LinkedList<Integer>();
			list.add(queue);
		}
		
		int index=0;
		int[] sort = new int[value.length];
		
		//digitMax can be increase after subtracting min to array
		for(int j=digitMax+1; j>0; j--){
			
			//add index to list of queue
			for(i=0; i<value.length; i++){
				//subtracting minimum to make all elements positive
				temp = (value[i]-min)/(int)Math.pow(10, digitMax+1-j) % 10;
				list.get(temp).offer(i);
			}
			
			//pop elements from each queue
			for(i=0, index=0; i<10; i++){
				while(!list.get(i).isEmpty()){
					sort[index] = value[list.get(i).poll()]-min;
					index++;
				}
			}
			
			for(i=0; i<value.length; i++){
				value[i] = sort[i] + min;
			}
		}
		
		return (value);
	}
	
	
	private static int digit(int n){
		if(n<0) n *= -1; //if n is negative, n = (-n)
		int i;
		for(i=0; n>0; i++){
			n = n/10;
		}
		return i;
	}
}