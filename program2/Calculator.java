package program2;

import java.util.Scanner;
public class Calculator {

	public static void main(String args[]){

		Scanner keyboard = new Scanner (System.in);
		String str;
		boolean option = true;//true to start the program
		String c;
		Number number = null;
		Number number2;
		
		do{
			if(number == null){
				System.out.println("number: 0");
			}else
			System.out.println("number: "+number);
			menu();			
			c = keyboard.next();
			switch(c){			
			case "e":
				System.out.println("please enter a new value");
				str = keyboard.next();
				number = new Number(str);
				break;
			case "a":
				System.out.println("please enter a number to be added");
				str = keyboard.next();
				number2 = new Number(str);
				if(number == null){
					number = number2;
					break;
				}
				if(number2 == null)
					break;
				number = number.add(number2);
				break;
			case "s":
				System.out.println("please enter a number to be substracted");
				str = keyboard.next();
				number2 = new Number(str);
				if(number == null){
					number = number2;
					break;
				}
				if(number2 == null)
					break;
				number = number.subtract(number2);
				break;
			case "m":
				System.out.println("please enter a number to be multiply");
				str = keyboard.next();
				number2 = new Number(str);
				if(number == null || number2 == null ){//if ether number or number2 is 0 result is 0 zero 
					number = null;
					break;
				}
				number = number.multiply(number2);
				break;
			case "c":
				number = null;
				break;
			case "q":
				option = false;
				break;
			case "r":
				number.reverseSign();
				break;
			case "p":
				System.out.println("number: "+ number);
				System.out.println("decimal: "+ number.getDecimalPlaces() + " digits: "+ number.getDigitCount() + " negative " + number.getNegative());
			
			default:
				System.out.println("not an option \n please select from the menu:");
			}
			
		}while(option);
		
	}
	private static void menu(){
		System.out.println("enter a value: e\t\t\t add: a");
		System.out.println("subtract: s \t\t\t\t multiply: m");
		System.out.println("reverse sign: r \t\t\t clear: c");
		System.out.println("quit: q");
		
	}
	public static void add(){
		Scanner keyboard = new Scanner (System.in);
		System.out.println("please enter new number");
		String str = keyboard.nextLine();
		Number num = new Number(str);
		System.out.println(num);
	}

}
