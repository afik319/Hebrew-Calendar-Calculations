
public class Aderet {
	public static void main(String[] args) {
		double [] aderet_jer_base = {
		         4.0 / 60 + 11.0 , 11 , 8 ,
		         5.0 / 60 + 12.0 , 28 , 8 ,
		        29.0 / 60 + 46.0 ,  3 , 5 ,
	             5.0 / 60 + 33.0 , 14 , 5 ,
	            20.0 / 60 + 11.0 ,  11 , 1 ,
	            0 , 0 , 0
	        }; //wrong
		double [] aderet_month = {
				40.0 + calc(14 ,  5 , 44 , 50 , 16 , 31 , 40) ,  0 , 1 ,
				49.0 + calc( 0 ,  8 , 14 ,  6 , 48 ,  2 , 51) , 25 , 0 ,
	             6.0 + calc(20 , 14 , 56 , 32 , 26 , 31 , 40) , 29 , 0 ,
	             6.0 + calc(24 , 40 , 43 ,  3 , 41 , 31 , 40) , 29 , 0 , 
	            44.0 + calc( 3 , 20 ,  0 ,  0 ,  0 ,  0 ,  0) , 12 , 1 ,
	            0 , 0 , 0
	        };
		double [] aderet_cycle = {
				35.0 + calc(34 , 44 , 26 , 10 , 16 , 19 , 10) ,  7 ,  0 ,
				55.0 + calc(36 ,  3 , 24 , 33 , 11 , 57 , 42) ,  6 , 10 ,
	            49.0 + calc(40 , 45 , 16 , 19 , 25 ,  6 , 50) , 29 , 11 ,
	             7.0 + calc( 1 , 42 , 28 , 43 , 11 , 19 , 10) , 0  ,  0 , 
	            33.0 + calc( 3 , 20 ,  0 ,  0 ,  0 ,  0 ,  0) , 16 ,  2 ,
	            0 , 0 , 0
	        };
		double [] jer_add = {
	            0 ,  0 , 0 ,
	            0 ,  0 , 0 ,
	            0 ,  0 , 0 ,
	            0 ,  0 , 0 , 
	            16 , 1 , 0 ,
	            0 , 0 , 0
	        };
		Line ader_jer_base = new Line(aderet_jer_base);
		Line ader_month = new Line(aderet_month);
		Line ader_cycle = new Line(aderet_cycle);
		Line result_cycle = (ader_cycle.mult(290));

		for(int i = 0 ; i < 15 ; i++)
			System.out.print((int)(double)result_cycle .get(i) + " , ");		
	}
	
	public static double calc(int n2 , int n3 , int n4 , int n5 , int n6 , int n7 ,int n8) {
		double result = 0.0;
		int[] content = {n2 , n3 , n4 , n5 , n6 , n7 , n8};
		for(int i = 0 ; i < 7 ; i++)
			result += (content[i] / Math.pow(60.0 , i + 1));
		return result;
	}
}
