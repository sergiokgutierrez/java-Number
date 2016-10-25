package program2;

public class test {
	public static void main(String[] args) {
		Number number= new Number("000034.010000000");
		System.out.println("number: "+number);
		number.trim();
		System.out.println("number: "+number+" digitcount "+number.getDigitCount() +" decimalplaces: "+number.getDecimalPlaces());
		

		
	}

}
