import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Write a description of class table_6 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_6
{
    static final int ZODIAC_COMPLEMENT = 11;
    static final int DEGREES_COMPLEMENT = 30;
    private static final int ALLIGATOR_LAW_DEG_INDEX = 1;
	private static final int ALLIGATOR_LAW_ZOD_INDEX = 2;
	private static final int MIN_ZODIAC_COUNT = 0;
	private static final int LAST_ZODIAC_INDEX = 11;
	private static final int MAX_ZODIAC_COUNT = LAST_ZODIAC_INDEX + 1;
	private static final int CONNECT_MIN_INDEX = 12;
	private static final ArrayList<Integer> STRAIGHT_DIRECTION_ZOD =
			new ArrayList<Integer>(Arrays.asList(0,1,2,6,7,8)); 
	private static final double POSITIVE_SIGN = 1;
	private static final double NEGATIVE_SIGN = -1;
	private static final int MAX_DEG_COUNT = 30;
	private static final int HALF_MAX_ZOD_COUNT = 6;
    static List<List<Double>> table_6 = new ArrayList<>();
    
    static final double table_6_org[][] = { 
        { 0,13,13},
        { 1,14,13},
        { 1,14,13},
        { 2,14,13},
        { 2,14,12},
        { 3,15,12},
        { 3,15,12},
        { 4,15,11},
        { 4,15,11},
        { 5,15,10},
        { 5,15,10},
        { 6,15,10},
        { 6,15, 9},
        { 7,15, 9},
        { 7,15, 8},
        { 8,15, 8},
        { 8,15, 7},
        { 9,15, 7},
        { 9,15, 6},
        {10,15, 6},
        {10,15, 5},
        {10,15, 5},
        {11,15, 4},
        {11,15, 4},
        {12,15, 3},
        {12,15, 3},
        {12,14, 2},
        {13,14, 2},
        {13,14, 1},
        {13,14, 1},
        {13,13, 0}};
    
    static {
        for (double[] row : table_6_org) {
            List<Double> rowList = new ArrayList<>();
            for (int i = 0; i < row.length; i++) {
            	rowList.add(row[i]);
            }
            table_6.add(rowList);
        }
    }
    
  //coordinates lines contains the coordinates in indexes 1,2
    public static Line getAlligatorDiffLine(Line coordinatesLine) {
    	int degNumber = (int)(double)coordinatesLine.get(ALLIGATOR_LAW_DEG_INDEX);
    	int orgZodiacNumber = (int)(double)coordinatesLine.get(ALLIGATOR_LAW_ZOD_INDEX); 
    	int zodiacCol; //thats how the table works
    	Line result = new Line();
    	if(!isValidZodNumber(orgZodiacNumber))
    		Calculations.errorAlert("Error" , "invalid Zodiac value" , "\"" + orgZodiacNumber + "\"");
    	else if(isStraightDirectionZodNumber(orgZodiacNumber)) {
    		zodiacCol = orgZodiacNumber % HALF_MAX_ZOD_COUNT;
    		result.set(CONNECT_MIN_INDEX , table_6.get(degNumber).get(zodiacCol));//afik
    		result.setSign(POSITIVE_SIGN);
    	}
    	else {
    		int oppositeDegNumberIndex = MAX_DEG_COUNT - degNumber;
    		int oppositeZodNumberIndex = (LAST_ZODIAC_INDEX - orgZodiacNumber)  % HALF_MAX_ZOD_COUNT;
    		result.set(CONNECT_MIN_INDEX , table_6.get(oppositeDegNumberIndex).get(oppositeZodNumberIndex));//afik
    		result.setSign(NEGATIVE_SIGN);
    	}
    	return result;
    }
    
    private static boolean isValidZodNumber(int zodNumber) {
    	return (MIN_ZODIAC_COUNT <= zodNumber && zodNumber < MAX_ZODIAC_COUNT); //check max
    }
    
    private static boolean isStraightDirectionZodNumber(int zodNumber) {
    	return STRAIGHT_DIRECTION_ZOD.contains(zodNumber);
    }
    
}

