/**
 * Efficient implementation of IDnaStrand.
 * This implementation uses Linked Lists to represent genomic/DNA data.
 * 
 * @author Anshu Dwibhashi
 * @date 26 October 2018, created.
 */
public class LinkStrand implements IDnaStrand{
	private int myAppends;

	// Our linked list's node
	private class Node {
	   	String info;
	   	Node next;
	   	public Node(String s) {
	      	info = s;
	      	next = null;
	   	}
	}
	private Node myFirst,myLast;
	private long mySize = 0;
	
	/**
	 * Create a strand representing s.
	 * 
	 * @param s: the source of cgat data for this strand
	 */
	public LinkStrand(String s) {
		initialize(s);
	}
	
	/**
	 * Create a strand representing an empty string.
	 * represents valid genomic/DNA data.
	 * 
	 * @param s: the source of cgat data for this strand
	 */
	public LinkStrand() {
		initialize("");
	}

	/**
	 * Get the size (# of base pairs) of this strand
	 * @return the number of base pairs in this strand
	 */
	@Override
	public long size() {
		return mySize;
	}

	private int myIndex;
	private int myLocalIndex;
	private Node myCurrent;
	
	/**
	 * Initialize this strand so that it represents the value of source.
	 * 
	 * @param source: the source of this enzyme
	 */
	@Override
	public void initialize(String source) {
		myFirst = myLast = myCurrent = new Node(source);
		myAppends = 0;
		myIndex = myLocalIndex = 0;
		mySize = source.length();
	}

	/**
	 * Get an instance of a LinkStrand for the given source 
	 * 
	 * @param source: source to make a LinkStrand from
	 */
	@Override
	public IDnaStrand getInstance(String source) {
		return new LinkStrand(source);
	}

	/**
	 * Simply append a strand of dna data to this strand.
	 * 
	 * @param dna: the String appended to this strand
	 */
	@Override
	public IDnaStrand append(String dna) {
		Node newNode = new Node(dna);
		myLast.next = newNode;
		myLast = newNode;
		myAppends++;
		mySize+=dna.length();
		return this;
	}

	/**
	 * Reverse the linked list containing this strand.
	 */
	@Override
	public IDnaStrand reverse() {
		Node newList = null;
		Node iter = myFirst;
		while(iter != null) {
			Node temp = iter;
			iter = iter.next;
			temp.info = new StringStrand(temp.info).reverse().toString();
			temp.next = newList;
			if (newList == null) {
				myLast = temp;
			}
			newList = temp;
		}
		// Now we have a reversed list
		myFirst = newList;
		return this;
	}

	/**
	 * Get the number of appends that were made
	 * @return number of appends
	 */
	@Override
	public int getAppendCount() {
		return myAppends;
	}

	/**
	 * Get the character at the required index
	 * @return required character
	 */
	@Override
	public char charAt(int index) throws IndexOutOfBoundsException {
		if (myIndex == 0 && myCurrent == null) {
			myCurrent = myFirst;
		}
		while (myIndex != index) {
			myIndex++; myLocalIndex++;
			if (myCurrent == null) {
				throw(new IndexOutOfBoundsException());
			}
			if (myLocalIndex >= myCurrent.info.length()) {
				myLocalIndex = 0;
				myCurrent = myCurrent.next;
			}
		}
		myIndex = index;
		try {
			return myCurrent.info.charAt(myLocalIndex);
		} catch (Exception e) {
			throw(new IndexOutOfBoundsException());
		}
	}
	
	/**
	 * Get a string representation of this string
	 * @return string representation
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Node iter = myFirst;
		while(iter != null) {
			builder.append(iter.info);
			iter = iter.next;
		}
		return builder.toString();
	}
	
}
