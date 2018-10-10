import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Matrix {
	//Member variable to store matrix.
	private double[][] m;
	//Static (constant) member variable to store matrix size.
	private static int size;
	//Member variable to store the Determinant Multiplier.
	public static double detMultiplier = 1;
	//Member variable to store the Determinant.
	public static double determinant;
	//Member variable to store if array if ref or not.
	public static boolean isRef;
	
	//Constructor to initialize values for matrix. Size must be given.
	public Matrix(int n) {
		//Set the size.
		size=n; 
		//Instantiate the matrix array.
		m = new double[size][size]; 
		//Get User Values For Array.
		Scanner s = new Scanner(System.in);
		for(int i = 0; i<size;i++){
			for(int j =0; j<size;j++){
				System.out.println("Enter value for A" + (i+1) + (j+1));
				m[i][j] = s.nextDouble();
			}
		}
		isRef = checkIfRef(m);
		determinant = getDet(m);
		
	}
	
	//Constructor that takes in another matrix and duplicates it.
	public Matrix(Matrix n) {
		size = n.getSize();
		m = n.getMat();
	}

	//Returns the Determinant if Matrix is already in row echelon. (Simpler)
	public double getDR() {
		double det = 1;
		for(int i = 0; i<size; i++) det*=m[i][i];
		return det;
	}
	//Getter for Size.
	public int getSize() {return size;}
	
	//Getter for Matrix.
	public double[][] getMat() {return m;}
	
	//Getter for isRef.
	public boolean getRef() {return isRef;}
	
	//Returns the Stored Value of the Determinant times the Determinant Multiplier.
	public double getD() { return detMultiplier*getDet(m);}
	
	//Returns the stored value of the detMultiplier.
	public double getdetMultiplier() {return detMultiplier;}
	
	//Converts Object's Matrix To Row Echelon Form
	public void convertToREF() {	
		draw(m);
		//Base Case For Size 1 and 0
		if(size == 1 || size == 0) System.out.println("Can't REF This"); 
		//Else if size greater than 1 but m is already REF.
		else if(checkIfRef(m)==true) {
			System.out.println("Already in REF");
			draw(m);
		}
		//Else if Size greater than 1, and m is not in Row Echelon Form yet.
		else if(checkIfRef(m)==false){			
			//Do the process of row elimination on each column up to size-2 because 
			//last column not needed.
			for(int i = 0; i<(size-1); i++) {
				//Make a list of row intervals that have a number, are not 0.		
				List<Integer> rows = new ArrayList<Integer>();
				//Loops through given column including diagonal.
				for(int j = i; j< size; j++) {
					if(m[j][i]!=0) {	
						//Add value j to list of non-zero row entries.
						rows.add(j);					
					}
				}
				//If m[i][i] is 0 then permutate ith row with (list.1)th row
				if(m[i][i]==0) {
					P(i,rows.get(0));
					draw();
				}
				/*Now that m[i][i] is not 0,
				Loop divide, For every j and i pair
				do Add row0 to rowj 
				k= -m[j][i]/m[i][i]
				*/
				for(int j=1; j<rows.size(); j++) {
						A(i,rows.get(j),(-1*m[rows.get(j)][i]/m[i][i]));
						drawr(m);
				}	
				//Check if in row echelon and if not then do the same thing with
				//the next column until size-2, but move row up one to match column diagonal.
				if (checkIfRef(m)==true) {
					System.out.println("END OF ROW ECHELON");
					//We want to end the loop.
					break;
				}
			}
		}
	}
	
	//If all values below diagonal are 0, return true, if non-zero found, return false. 
	private boolean checkIfRef(double[][] b) {
		for(int i = 0; i < b.length-1; i++) {
			for(int j = i+1; j<b.length;j++) {
				if(m[j][i]!=0) {
					return false;
				}
			}
		}
		return true;
	}
	
	//Draws Object's Main Matrix.
	public void draw() {
		for(int i = 0; i<size;i++){
			System.out.print("|");
			for(int j =0; j<size;j++){
				System.out.print(m[i][j] + " ");		
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println();
	}
	
	//Overloaded function, specific draw.
	public void draw(double[][] x) {
		for(int i = 0; i<size;i++){
			System.out.print("|");
			for(int j =0; j<size;j++){
				System.out.print(x[i][j] + " ");		
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println();
	}
	
	//Draws with rational numbers.
	public void drawr(double[][] x) {
		for(int i = 0; i<size;i++){
			System.out.print("|");
			for(int j =0; j<size;j++){
				if((x[i][j]-Math.floor(x[i][j]))!=0) System.out.print(new Rational(x[i][j]) + " ");	
				else System.out.print(x[i][j] + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println();
	}
	
	//Returns the Determinant of the matrix.
	public double getDet()
	{
		if(m.length == 1) return (m[0][0]);
		if(m.length == 2) return ((m[0][0]*m[1][1])-(m[0][1]*m[1][0]));
		
		double sum=0;
		for(int k=0; k<m.length;k++) {
			sum += (Math.pow(-1, k))*(m[0][k]*getDet(shrink(m,0,k)));
		}
		return sum;
	}
	
	
	//Returns the Determinant of a given matrix.
	public double getDet(double [][] m)
	{
		if(m.length == 1) return (m[0][0]);
		if(m.length == 2) return ((m[0][0]*m[1][1])-(m[0][1]*m[1][0]));
		
		double sum=0;
		for(int k=0; k<m.length;k++) {
			sum += (Math.pow(-1, k))*(m[0][k]*getDet(shrink(m,0,k)));
		}	
		return sum;	
	}
	
	//Returns a shrunken matrix from given matrix with given row and column.
	public double[][] shrink(double[][] p, int r, int c){
		int tmpx = -1;
		int tmpy = -1;
		double [][] newmat = new double[p.length-1][p.length-1];
		
		for(int i = 0; i < p.length; i++) {
			tmpx++;
			if(i==r) tmpx--;
			tmpy = -1;
			for(int j = 0; j < p.length; j++) {
				tmpy++;
				if(j==c) tmpy--;
				if(i!=r && j!=c) {
					newmat[tmpx][tmpy] = p[i][j];
				}
			}
		}
		return newmat;
	}
	
	//Returns a permutated matrix r1 with r2.
	public void P(int r1, int r2)
	{
		detMultiplier*=-1;
		double[][] newMat = new double[m.length][m.length];
		
		for(int i = 0; i<m.length; i++) {
			for(int j = 0; j<m.length; j++) {
				if(i!=r1 && i!= r2) newMat[i][j] = m[i][j];
				else {
					newMat[r2][j]=m[r1][j];
					newMat[r1][j]=m[r2][j];
				}
			}
		}
		System.out.println("---- P" + (r1+1) + "" + (r2+1) + " ---->");
		m=newMat;
	}
	
	//Returns the matrix row r multiplied by k.
	public void M(int r, double k)
	{
		detMultiplier/=k;
		double[][] newMat = new double[m.length][m.length];
		
		for(int i =0; i<m.length;i++) {
			for(int w = 0; w<m.length;w++) {
				if(i!=r) newMat[i][w] = m[i][w];
				else newMat[i][w]=k*m[i][w];
			}
		}
		//If k is whole, print normally, otherwise convert to fraction.
		if(k-Math.floor(k)==0) System.out.println("---- M" + r + "(" + k + ")" + " ---->");
		else System.out.println("---- M" + r + "(" + new Rational(k) + ")" + " ---->");
		m=newMat;
	}
	
	//Returns the matrix added r1 multiplied by k to r2.
	public void A(int r1, int r2, double k)
	{
		double[][] newMat = new double[m.length][m.length];
		for(int i = 0; i<m.length; i++) {
			for(int j = 0; j<m.length; j++) {
				if(i != r2) newMat[i][j] = m[i][j];
				else {
					newMat[i][j]=(m[r1][j]*k)+m[i][j];
				}
			}
		}
		//If k is whole, print normally, otherwise convert to fraction.
		if(k-Math.floor(k)==0) System.out.println("---- A" + (r1+1) +""+ (r2+1) + "(" + k + ")" + " ---->");
		else System.out.println("---- A" + (r1+1) +""+ (r2+1) + "(" + new Rational(k) + ")" + " ---->");
		m=newMat;
	}	
}
