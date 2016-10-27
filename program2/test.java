package program2;

public class test {
	public static void main(String[] args) {
		Number number= new Number("2607");
		System.out.println("number: "+number);
//		System.out.println("number: "+number+" digitcount "+number.getDigitCount() +" decimalplaces: "+number.getDecimalPlaces());

		Number number2 = new Number("53");
		System.out.println("number2: "+number2);
		number = number.multiply(number2);		
		System.out.println("number: "+number+" digitcount "+number.getDigitCount() +" decimalplaces: "+number.getDecimalPlaces());
		

		
	}

}
