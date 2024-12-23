/**
 * Table_1 class provides methods and constants for calculating 
 * the cycle movement in a specific location (Jerusalem).
 * 
 * @version (04.06.2024)
 */
public class Table_1 
{    
	private static final int START_CYCLE = 304;
	private static final double[] BASE304_JERUSALEM_ARRAY = {56,26,4,31,7,2,21,2,10,38,3,1,5,16,5,0,0,0};
	private static final double[] ONE_CYCLE_ARRAY = {34,7,0,58,6,10,45,29,11,5,0,0,31,16,2,0,0,0};
	private static final Line BASE304_JERUSALEM = new Line(BASE304_JERUSALEM_ARRAY);
	private static final Line ONE_CYCLE = new Line(ONE_CYCLE_ARRAY);
    
	/**
     * Calculates the cycle placement of the moon and the sun for a given cycle number.
     * 
     * @param cycleNumber the cycle number to calculate.
     * @return Line object representing the calculated cycle placements.
     */
    public static Line halichotCycleCalc(int cycleNumber) {
    	int cyclesAdd = cycleNumber - START_CYCLE;
    	Line addition = ONE_CYCLE.mult(cyclesAdd);
    	Line result = BASE304_JERUSALEM.add(addition);
    	return result;
    }
        
}
