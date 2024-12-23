import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class table_4 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_4
{
        static final int ZODIAC_COMPLEMENT = 11;
        static final int DEGREES_COMPLEMENT = 30;
        private static final int CONNECT_MIN_INDEX = 12;
    	private static final int CONNECT_HOUR_INDEX = 13;
        static List<List<List<Double>>> table_4 = new ArrayList<>();
        static final double table_4_org[][] = {
            { 0, 0, 5, 2,38, 3,14, 4,42, 3,10, 2},
            { 4, 0, 9, 2,40, 3,14, 4,40, 3, 6, 2},
            { 9, 0,12, 2,42, 3,14, 4,38, 3, 2, 2},
            {13, 0,16, 2,44, 3,14, 4,36, 3,58, 1},
            {17, 0,20, 2,46, 3,14, 4,33, 3,54, 1},
            {22, 0,23, 2,48, 3,14, 4,31, 3,50, 1},
            {26, 0,27, 2,50, 3,13, 4,28, 3,45, 1},
            {30, 0,30, 2,52, 3,13, 4,26, 3,41, 1},
            {35, 0,34, 2,54, 3,12, 4,23, 3,37, 1},
            {39, 0,37, 2,56, 3,12, 4,20, 3,33, 1},
            {43, 0,41, 2,57, 3,11, 4,17, 3,29, 1},
            {48, 0,44, 2,59, 3,11, 4,15, 3,24, 1},
            {52, 0,48, 2, 0, 4,10, 4,12, 3,20, 1},
            {56, 0,51, 2, 2, 4, 9, 4, 9, 3,16, 1},
            { 0, 1,54, 2, 3, 4, 8, 4, 6, 3,12, 1},
            { 5, 1,57, 2, 4, 4, 7, 4, 2, 3, 7, 1},
            { 9, 1, 0, 3, 5, 4, 6, 4,59, 2, 3, 1},
            {13, 1, 3, 3, 7, 4, 5, 4,56, 2,58, 0},
            {17, 1, 6, 3, 8, 4, 3, 4,53, 2,54, 0},
            {21, 1, 9, 3, 9, 4, 2, 4,49, 2,50, 0},
            {25, 1,12, 3, 9, 4, 1, 4,46, 2,45, 0},
            {29, 1,15, 3,10, 4,59, 3,43, 2,41, 0},
            {33, 1,18, 3,11, 4,58, 3,39, 2,36, 0},
            {37, 1,20, 3,12, 4,56, 3,36, 2,32, 0},
            {41, 1,23, 3,12, 4,54, 3,32, 2,27, 0},
            {45, 1,26, 3,13, 4,52, 3,28, 2,23, 0},
            {49, 1,28, 3,13, 4,51, 3,25, 2,18, 0},
            {53, 1,31, 3,13, 4,49, 3,21, 2,14, 0},
            {57, 1,33, 3,14, 4,47, 3,17, 2, 9, 0},
            { 1, 2,36, 3,14, 4,45, 3,13, 2, 5, 0},
            { 5, 2,38, 3,14, 4,42, 3,10, 2, 0, 0}};
		private static final int SUN_LAW_DEG_INDEX = 7;
		private static final int SUN_LAW_ZOD_INDEX = 8;
		private static final int MIN_ZODIAC_COUNT = 0;
		private static final int LAST_ZODIAC_INDEX = 11;
		private static final int MAX_ZODIAC_COUNT = LAST_ZODIAC_INDEX + 1;
		private static final int MIN_STRAIGHT_ZOD_COUNT = 0;
		private static final int MAX_STRAIGHT_ZOD_COUNT = 5;
		private static final int MAX_DEG_COUNT = 30;
		private static final double POSITIVE_SIGN = 1;
		private static final double NEGATIVE_SIGN = -1;
		
        
        static {
            for (double[] row : table_4_org) {
                List<List<Double>> rowList = new ArrayList<>();
                for (int i = 0; i < row.length; i += 2) {
                    List<Double> pair = new ArrayList<>();
                    pair.add(row[i]);
                    pair.add(row[i + 1]);
                    rowList.add(pair);
                }
                table_4.add(rowList);
            }
        }
        
        //coordinates lines contains the coordinates in indexes 7,8
        public static Line getSunDiffLine(Line coordinatesLine) {
        	int degNumber = (int)(double)coordinatesLine.get(SUN_LAW_DEG_INDEX);
        	int zodiacNumber = (int)(double)coordinatesLine.get(SUN_LAW_ZOD_INDEX); 
        	Line result = new Line();
        	if(!isValidZodNumber(zodiacNumber))
        		Calculations.errorAlert("Error" , "invalid Zodiac value" , "");
        	else if(isStraightDirectionZodNumber(zodiacNumber)) {
        		result.set(CONNECT_MIN_INDEX , table_4.get(degNumber).get(zodiacNumber).get(0));//afik
        		result.set(CONNECT_HOUR_INDEX , table_4.get(degNumber).get(zodiacNumber).get(1));
        		result.setSign(NEGATIVE_SIGN);
        	}
        	else {
        		int oppositeDegNumberIndex = MAX_DEG_COUNT - degNumber;
        		int oppositeZodNumberIndex = LAST_ZODIAC_INDEX - zodiacNumber;
        		result.set(CONNECT_MIN_INDEX , table_4.get(oppositeDegNumberIndex).get(oppositeZodNumberIndex).get(0));//afik
        		result.set(CONNECT_HOUR_INDEX , table_4.get(oppositeDegNumberIndex).get(oppositeZodNumberIndex).get(1));
        		result.setSign(POSITIVE_SIGN);
        	}
        	return result;
        }
        
        private static boolean isValidZodNumber(int zodNumber) {
        	return (MIN_ZODIAC_COUNT <= zodNumber && zodNumber < MAX_ZODIAC_COUNT); //check max
        }
        
        private static boolean isStraightDirectionZodNumber(int zodNumber) {
        	return (MIN_STRAIGHT_ZOD_COUNT <= zodNumber && zodNumber <= MAX_STRAIGHT_ZOD_COUNT);
        }

}

