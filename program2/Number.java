package program2;

public class Number {
	private Node low, high;
	private int digitCount = 0;
	private int decimalPlaces = 0;
	private boolean negative = false;
	
//	//extra variables
//	int leadingZeros = 0;
//	int trailingZeros = 0;
	
	public Number(){
		digitCount = 0;
		decimalPlaces = 0;
		negative = false;
	}
	public Number(String str){
		digitCount = 0;
		decimalPlaces = 0;
		negative = false;
		accept(str);
		
	}
	public void accept(String str){

		int x, index = 0;
		char c = str.charAt(0);
		if(c == '+'){
			negative = false;
			index++;
		}else if( c == '-'){
			negative = true;
			index++;
		}
		
		
		while(index < str.length()){
			c = str.charAt(index);
			if(c == '.'){
				decimalPlaces = (str.length()-1) - index;
			}else{
				x = Character.getNumericValue(c);
				if(x == -1 || x >9){//wrong input
					System.out.println("incorrect value input");
					return;
				}
				insert(x);
				digitCount++;
			}
			

			index++;			
		}
		
		System.out.println("digitCount: "+digitCount+" decimalPlaces: "+decimalPlaces);

		trim();
		
	}
	/*
	 * returns <0 if n < this return >0 if this>0 
	 * returns 0 if both number are the same
	 */
	public int compareToAbsolute(Number n){
		int thisRD = digitCount - decimalPlaces;
		int nRD = n.getDigitCount() - n.getDecimalPlaces();
		if( thisRD > nRD){//if this Number has more Real Digits than n. this.number is bigger
			return 1;
		}
		if(thisRD < nRD){//if n has Real Digits than this.number then n is bigger
			return -1;
		}
		
		if(thisRD == nRD){//if both of them have the same size
			Node thisPtr = high;
			Node nPtr = n.high;
			int comp;
			do{
				comp = thisPtr.getData() - nPtr.getData();
				//
				if(comp > 0)
					return comp;
				if(comp < 0)
					return comp;
				
				thisPtr = thisPtr.next;//traverse this
				nPtr = nPtr.next;//traverse n
				
			}while(thisPtr != null && nPtr != null);
			if(thisPtr == null && nPtr == null)
				return 0;
			if(nPtr == null)
				return 1;
			if(thisPtr == null)
				return -1;
			
				
		}
		
		
		
		return 0;
	}
	public Number add(Number n){
		if(n == null){
			return this;
		}	
		//if this and n have the same sign just add them and set the correct sign
		if(negative == n.getNegative()){
			lineUp(n);
			Number r = addAbsolute(n);
			r.setNegative(negative);//set results sign to this sign (could work with n since they are the same)
			//set result decimal place by 
			if(decimalPlaces > n.decimalPlaces)
				r.setDecimalPlaces(decimalPlaces);
			else 
				r.setDecimalPlaces(n.getDecimalPlaces());
			trim();
			n.trim();
			return r;
		}
		int compare = compareToAbsolute(n);
		if(compare > 0){//this is bigger
			lineUp(n);
			Number r = subtractAbsolute(n);
			r.setNegative(this.negative);
			return r;
		}
		if(compare < 0){//n is bigger
			lineUp(n);
			Number r = n.subtractAbsolute(this);//takes this object and substract n-this
			r.setNegative(n.getNegative());
			return r;
		}
		else
			return null;//compare == 0 so answer is 0

	}
	
	public Number addAbsolute(Number n){
		Number r = new Number();
		int carry = 0;
		int newDigit;
		Node thisPtr = low;
		Node nPtr = n.low;
		while (thisPtr != null){
			newDigit = thisPtr.getData() + nPtr.getData() + carry;//this.data + n.data + carry
			r.insertHigh(newDigit % 10);
			r.increaseDigitCount();
			carry = newDigit / 10;
			thisPtr = thisPtr.previous;
			nPtr = nPtr.previous;
			
//				add the values stored in the pointed to nodes plus the carry to
//			   get int newDigit
//			   store newDigit % 10 in a new node inserted at the head of sum
//			   sum.digitCount++
//			   store newDigit / 10 in carry
//			   thisPtr = thisPtr.getPrev()
//			   nPtr = nPtr.getPrev()
		}
		if (carry != 0){//create a new node with carry
			r.insertHigh(carry);
			r.increaseDigitCount();
		}
		return r;

}
	public Number subtractAbsolute(Number n){
		Number difference = new Number();
		int borrow = 0;
		Node thisPtr = low;
		Node nPtr = n.low;
		int newDigit;
		while( thisPtr != null){
			newDigit = thisPtr.getData() - nPtr.getData() - borrow;
			if(newDigit < 0){
				newDigit += 10;
				borrow = 1;
			}else{
				borrow = 0;
			}
//			Node newNode = new Node(newDigit);
			difference.insertHigh(newDigit);
			difference.increaseDigitCount();
			thisPtr = thisPtr.previous;
			nPtr = nPtr.previous;
		}
		difference.setDecimalPlaces(decimalPlaces);
		return difference;
}
	public Number subtract(Number n){
		return null;
}
	public Number multiply(Number n){
		Number product = new Number();
		Node nPtr = n.high;
		while(nPtr != null) {
			Number partialProduct = new Number();
			int carry = 0;
			Node thisPtr = this.low;
			product.insert(0);//insert 0 to tail as to multiply by 10 INCREASE DIGIT COUNT???
			while(thisPtr != null){
				int newDigit = thisPtr.getData() * nPtr.getData() + carry;
				carry = newDigit / 10;
				newDigit = newDigit % 10;
				partialProduct.insertHigh(newDigit);//insert at the head
				partialProduct.increaseDigitCount();
				thisPtr = thisPtr.previous;
			}
			if(carry != 0){
				partialProduct.insertHigh(carry);//insert at the head
				partialProduct.increaseDigitCount();
			}
			product.increaseDigitCount();
			product.lineUp(partialProduct);
			product = product.addAbsolute(partialProduct);
			nPtr = nPtr.next;
		}
		product.setDecimalPlaces(this.decimalPlaces + n.getDecimalPlaces());
		if(negative == n.negative){
			product.setNegative(negative);			
		}
		else{
			product.setNegative(true);			
		}
		return product;

	}
	public void reverseSign(){
		//. Reverses the value of negative.
		if(negative){
			negative = false;
		}else{
			negative = true;
		}
	}
	public void lineUp(Number n){
		/*
		 * ADD LEADING ZEROS
		 */
		int thisRP = digitCount - decimalPlaces;// this Real integer places 10.0001 = 2 RP
		int nRP = n.getDigitCount() - n.getDecimalPlaces();// n Real places 134.30201 = 3 RP
		int compare = thisRP - nRP;//compare the decimal points of the number
		if(compare > 0){//if greated line up n by the difference (compare)
			n.addLeadingZeros(compare);
		}
		else if(compare <0){
			compare *= -1;//turn it positive
			this.addLeadingZeros(compare);
		}
		
		
		/*
		 * ADD TRAILING ZEROS
		 */
		compare = this.decimalPlaces - n.getDecimalPlaces();
		if(compare > 0){
			n.addTrailingZeros(compare);
		}else if(compare < 0){
			compare *= -1;
			this.addTrailingZeros(compare);
		}
		
		
	}
	
	
	public void addLeadingZeros(int leadingZeros){
		//trailingZeros
		for(int i=0; i<leadingZeros; i++){
			insertHigh(0);//add at the highest node 1.01 = 00001.01
			digitCount++;
		}
	}
	public void addTrailingZeros(int trailingZeros){
		//trailingZeros
		for(int i=0; i<trailingZeros; i++){
			insert(0);//inserts at the low node 1.01 = 1.010000
			decimalPlaces++;
			digitCount++;
		}
	}
	//uses insert and insert first
	/*
	 * insert at low node
	 */
	public void insert(int data){
		//insert first node
		if(low == null && high == null){
			insertFirst(data);
			return;
		}
		//insert after firstNode
		Node p = new Node(data);
		p.previous = low;
		low.next = p;
		low = p;
	}
	
	public void insertFirst(int data){
		Node p = new Node(data);
		high = p;
		low = p;
	}
	
	public void insertHigh(int data){
		//insert first node
		if(low == null && high == null){
			insertFirst(data);
			return;
		}
		//insert after firstNode
		Node p = new Node(data);
		p.next = high;
		high.previous = p;
		high = p;
	}
	
	public void trim(){
//		//delete leading zeros
//		for(int i=0; i<leadingZeros; i++){
////			if(high.data != 0)
////				return;//if is not leading 0 return
//			high = high.next;
//			
//			
//		}
//		//delete trailing zeros with out affecting the decimal point
//		for( int  i = 0; i < trailingZeros; i++){
//			low = low.previous;
//		}
//		
		
		/*
		 * delete leading zeros as long as is less than decimal places
		 * 
		 */

		while(digitCount > decimalPlaces && high.getData() ==0){
			high = high.next;
			digitCount--;
		}
		while(0 < decimalPlaces && low.getData() == 0){
			low = low.previous;
			digitCount--;
			decimalPlaces--;
		}
		
	}
	
	public void increaseDigitCount(){
		digitCount++;
	}
	public int getDigitCount(){
		return digitCount;
	}
	public int getDecimalPlaces(){
		return decimalPlaces;
	}
	public boolean getNegative(){
		return negative;
	}
	public void setNegative(boolean negative){
		this.negative = negative;
	}
	public void setDecimalPlaces(int decimalPlaces){
		this.decimalPlaces = decimalPlaces;
	}
							/*
	 * need to add positive negative on the beggining 
	 * if positive don't add if negative yes
	 */
	public String toString(){
		//. Returns a String representation of the Number (so it can be displayed by
		StringBuilder sb = new StringBuilder();
		if(negative){
			System.out.println("NEGATIVE");
			sb.append("-");
		}
		Node pointer = high;
		for(int i=0;i < digitCount; i++){
			if(i == (digitCount - decimalPlaces)){
	
				sb.append(".");
			}
				sb.append(pointer);
				pointer = pointer.next;
	
		}
		
		return sb.toString();
	
	}
							private class Node{
								private Node next;
								private Node previous;
								int data;
								//constructor where	next = null and previous = null;
								//and data is equals to data
								private Node(int data){
									this.data = data;
								}
								public String toString(){
									return " "+data+ " ";
								}
								public int getData(){
									return data;
								}
								
							}

}
