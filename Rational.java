
public class Rational {

	private int num, denom;

	public Rational(double d) {
		String s = String.valueOf(d);
		int digitsDec = s.length() - 1 - s.indexOf('.');        

		int denom = 1;
		for(int i = 0; i < digitsDec; i++){
			d *= 10;
			denom *= 10;
		}
		int num = (int) Math.round(d);
		this.num = num; 
		this.denom = denom;
		
	}
	/** @return the greatest common denominator */
	public static long gcm(long a, long b) {
	    return b == 0 ? a : gcm(b, a % b);
	}

	public static String asFraction(long a, long b) {
	    long gcm = gcm(a, b);
	    if(gcm<0) gcm*=-1;
	    
	    return (a / gcm) + "/" + (b / gcm);
	}

	public Rational(int num, int denom) {
		this.num = num; this.denom = denom;
	}

	public String toString() {
		return asFraction(num,denom);
	}
}