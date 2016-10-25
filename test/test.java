package test;

public class test {
	public static void main(String[] args) {
		int [] array = new int[5];
		for(int i=0; i<5; i++){
			array[i]=i;
		}
		reverseOrder(array);
		for(int i=0; i<5; i++){
			System.out.println(array[i]);
		}
	}
	
	public static void reverseOrder(int [] a){
		for(int i=0; i<5; i++){
			a[i]=8;
		}
	}

}
