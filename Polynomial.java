package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	private Polynomial line;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Node p1 = this.poly;
		Node p2= p.poly;
		Node end= null; //pointer
		Node p3 = null;//pointer
		while(p1 != null && p2 != null) {
			Node n = new Node(p2.term.coeff,p2.term.degree,null);
			if(p1.term.degree == p2.term.degree){
				n.term.degree = p2.term.degree;
				n.term.coeff = p1.term.coeff + p2.term.coeff;
				n.next = null;
				p1 = p1.next;
				p2 = p2.next;
				if(n.term.coeff == 0){
					continue;
				}
				
			}
			else if(p1.term.degree < p2.term.degree) {
				n.term.degree = p1.term.degree;
				n.term.coeff = p1.term.coeff;
				n.next = null;
				p1 = p1.next;
				
				}
				
			
			else if (p2.term.degree < p1.term.degree) {
				n.term.degree = p2.term.degree;
				n.term.coeff= p2.term.coeff;
				n.next = null;
				p2 = p2.next;
			
				
			}
				if( p3 == null) {
					p3=n;
					end = n;
				}
				else{
				end.next=n;
				end = end.next;
				}
				
		
		}
			while(p1 != null && p2 == null){
				Node n = new Node(p1.term.coeff,p1.term.degree,null);
				n.term.coeff = p1.term.coeff;
				n.term.degree = p1.term.degree;
				n.next = null;
				p1 = p1.next;
				if( p3 == null) {
					p3 = n;
					end = n;
				}
				else{
					
					end.next = n;
					end = end.next;
				}
				
				
			}
			while (p1 == null && p2 != null){
				Node n = new Node(p2.term.coeff,p2.term.degree,null);
				n.term.coeff = p2.term.coeff;
				n.term.degree = p2.term.degree;
				n.next = null;
				p2 = p2.next;
				if( p3 == null){
					p3 =n;
					end = n;
				}
				else{
					end.next = n;
					end = end.next;
				}
			}
			
	    Polynomial p4 = new Polynomial();
	    p4.poly = p3;
	    return p4;
		
			
		
		
		
		
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Node p1 = this.poly;
		Node p2 = p.poly;
		Polynomial p5 = new Polynomial();//the result polynomial
	
			   Polynomial p4 = new Polynomial();
			   /*if(p1 == null || p2 == null){
					 p5 = null;
					 return p5;
			   }*/
			   // I wasn't sure if an empty file was supposed to return 0 or null. If it needs to return null, remove the comment bar from 266-269 above such that the 'if' statement is available.
				for ( p2 = p.poly; p2 != null; p2= p2.next){
					Node p3 = null;//pointer 
					Node end = null;//pointer
					
					
					for(p1 = this.poly; p1 != null; p1 = p1.next) {
						
						Node s = new Node(p1.term.coeff*p2.term.coeff,p1.term.degree+p2.term.degree,null);// creates a new node s for every iteration.
						


						if(s.term.coeff == 0){ //because we don't want 0 coefficient such as 0.0x^5
							continue;
						}
						s.term.degree = p1.term.degree+p2.term.degree;
						s.term.coeff= p1.term.coeff*p2.term.coeff;
						 
						 
						if(p3 == null){
							p3 = s;
							end= s;
							
						}
						else{
							end.next = s;
							end = end.next;
							
						}
						p4.poly =p3;
					
						}
						
					 p5 = p5.add(p4);// the product is the sum of iterations when every term of p2 is multiplied throughout p1. 
					

					
					
					
				}
			 
			 return p5;
				
				
			 
			
				 
		
	
}

	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		/** COMPLETE THIS METHOD **/
		Node p1 = this.poly;
		float sum = 0;
		for(p1=this.poly;p1 != null; p1= p1.next) {
			float c = p1.term.coeff;
		    float f = (float)Math.pow(x,p1.term.degree);
		    sum += (c*f);
		  
		

			
		}
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
