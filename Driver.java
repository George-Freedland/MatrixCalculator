import java.util.Scanner;

public class Driver {
	//Variable to hold size of array.
	private static int L;

	//Main method, scans values in, creates matrix objects,
	//Draws, and Converts to Row Echelon Form
	public static void main(String[] args)
	{	
		//Scan User Value for size of square matrix.
		System.out.println("Enter n in nxn matrix: ");
		Scanner s = new Scanner(System.in);
		//Declare and Instantiate a Matrix object m with size.
		L = s.nextInt();
		Matrix m = new Matrix(L); 
		//Matrix o = new Matrix(m);

		System.out.println("Matrix A = ");
		m.draw();
		//If entered matrix happens to be REF, uses unique getDR determinant method.
		if(m.getRef() && L != 0) System.out.println("The RE Determinant is " + m.getDR());
		//Otherwise get it the old-fashioned way.
		else { 
			System.out.println("The Matrix is not REF yet.");
			System.out.println("The Determinant is " + m.getD());
			System.out.println("The conversion to Row Echelon is as follows: ");
			m.convertToREF();
			System.out.println("The OG Determinant is " + m.getdetMultiplier()*m.getDR());
			System.out.println("Determinant Multiplier is " + m.getdetMultiplier());
			System.out.println("The converted matrix's Determinant is " + m.getDR());
			//System.out.println("This is the copy of a Matrix A, Matrix o = ");
			//o.draw();
		}
	}
}
//Note Proof: (E1 through EN)*A = I, so (E1 through EN) = A Inverse
//(E1 through EN) Inverse = A
