import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class table_5 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_5 
{
		private static final double POSITIVE_SIGN = 1;
		private static final double NEGATIVE_SIGN = -1;
		private static final int MIN_ZODIAC_COUNT = 0;
		private static final int LAST_ZODIAC_INDEX = 11;
		private static final int MAX_ZODIAC_COUNT = LAST_ZODIAC_INDEX + 1;
		private static final int MIN_STRAIGHT_ZOD_COUNT = 0;
		private static final int MAX_STRAIGHT_ZOD_COUNT = 5;
		private static final int MOON_LAW_DEG_INDEX = 4;
		private static final int MOON_LAW_ZOD_INDEX = 5;
		private static final int CONNECT_MIN_INDEX = 12;
    	private static final int CONNECT_HOUR_INDEX = 13;
        static final int ZODIAC_COMPLEMENT = 11;
        static final int DEGREES_COMPLEMENT = 30;
        private static final int MAX_DEG_COUNT = 30;
        static List<List<List<Double>>> table_5 = new ArrayList<>();
        static final double table_5_org[][] = {
            { 0, 0,16, 5,51, 8,48, 9, 7, 8,32, 4},
            {11, 0,25, 5,56, 8,47, 9, 2, 8,23, 4},
            {22, 0,34, 5, 0, 9,46, 9,56, 7,15, 4},
            {33, 0,43, 5, 4, 9,44, 9,50, 7, 6, 4},
            {44, 0,52, 5, 8, 9,43, 9,44, 7,58, 3},
            {56, 0, 1, 6,12, 9,41, 9,38, 7,49, 3},
            { 7, 1,10, 6,16, 9,39, 9,32, 7,40, 3},
            {18, 1,18, 6,19, 9,37, 9,25, 7,32, 3},
            {29, 1,26, 6,22, 9,35, 9,19, 7,23, 3},
            {40, 1,34, 6,25, 9,33, 9,12, 7,14, 3},
            {51, 1,42, 6,28, 9,30, 9, 5, 7, 5, 3},
            { 2, 2,53, 6,31, 9,28, 9,59, 6,56, 2},
            {13, 2,58, 6,34, 9,25, 9,52, 6,47, 2},
            {23, 2,06, 7,36, 9,21, 9,45, 6,38, 2},
            {34, 2,13, 7,38, 9,18, 9,37, 6,29, 2},
            {45, 2,21, 7,40, 9,15, 9,30, 6,20, 2},
            {55, 2,28, 7,42, 9,12, 9,23, 6,10, 2},
            { 6, 3,35, 7,44, 9, 8, 9,15, 6, 1, 2},
            {17, 3,42, 7,45, 9, 4, 9, 8, 6,52, 1},
            {27, 3,48, 7,47, 9, 0, 9, 0, 6,43, 1},
            {37, 3,55, 7,48, 9,56, 8,53, 5,33, 1},
            {48, 3, 1, 8,48, 9,52, 8,45, 5,24, 1},
            {58, 3, 7, 8,49, 9,47, 8,37, 5,15, 1},
            { 8, 4,13, 8,50, 9,43, 8,29, 5, 6, 1},
            {18, 4,19, 8,50, 9,38, 8,21, 5,56, 0},
            {28, 4,25, 8,50, 9,33, 8,13, 5,47, 0},
            {38, 4,31, 8,50, 9,28, 8, 5, 5,38, 0},
            {47, 4,36, 8,50, 9,23, 8,57, 4,28, 0},
            {57, 4,41, 8,49, 9,18, 8,49, 4,19, 0},
            { 6, 5,46, 8,49, 9,13, 8,41, 4, 9, 0},
            {16, 5,51, 8,48, 9, 7, 8,32, 4, 0, 0}};
        
        static {
            for (double[] row : table_5_org) {
                List<List<Double>> rowList = new ArrayList<>();
                for (int i = 0; i < row.length; i += 2) {
                    List<Double> pair = new ArrayList<>();
                    pair.add(row[i]);
                    pair.add(row[i + 1]);
                    rowList.add(pair);
                }
                table_5.add(rowList);
            }
        }
        
      //coordinates lines contains the coordinates in indexes 4,5
        public static Line getMoonDiffLine(Line coordinatesLine) {
        	int degNumber = (int)(double)coordinatesLine.get(MOON_LAW_DEG_INDEX);
        	int zodiacNumber = (int)(double)coordinatesLine.get(MOON_LAW_ZOD_INDEX); 
        	Line result = new Line();
        	if(!isValidZodNumber(zodiacNumber))
        		Calculations.errorAlert("Error" , "invalid Zodiac value" , "");
        	else if(isStraightDirectionZodNumber(zodiacNumber)) {
        		result.set(CONNECT_MIN_INDEX , table_5.get(degNumber).get(zodiacNumber).get(0));//afik
        		result.set(CONNECT_HOUR_INDEX , table_5.get(degNumber).get(zodiacNumber).get(1));
        		result.setSign(POSITIVE_SIGN);
        	}
        	else {
        		int oppositeDegNumberIndex = MAX_DEG_COUNT - degNumber;
        		int oppositeZodNumberIndex = LAST_ZODIAC_INDEX - zodiacNumber;
        		result.set(CONNECT_MIN_INDEX , table_5.get(oppositeDegNumberIndex).get(oppositeZodNumberIndex).get(0));//afik
        		result.set(CONNECT_HOUR_INDEX , table_5.get(oppositeDegNumberIndex).get(oppositeZodNumberIndex).get(1));
        		result.setSign(NEGATIVE_SIGN);
        	}
        	return result;
        }
        
        private static boolean isValidZodNumber(int zodNumber) {
        	return (MIN_ZODIAC_COUNT <= zodNumber && zodNumber < MAX_ZODIAC_COUNT); //check max
        }
        
        private static boolean isStraightDirectionZodNumber(int zodNumber) {
        	return (MIN_STRAIGHT_ZOD_COUNT <= zodNumber && zodNumber <= MAX_STRAIGHT_ZOD_COUNT);
        }
        
        /* public static double[] get_line(int zodiac , int degrees) {
            double[] line = new double[4];
            line[0] = 0;
            if((zodiac < 6 && zodiac >= 0) || zodiac == 12){
                line[1] = table_5_org[degrees][2 * zodiac]; //minuetes
                line[2] = table_5_org[degrees][2 * zodiac + 1]; //minuetes
                line[3] = 1;
            }
            else if(zodiac >=6 && zodiac <=11){
                line[1] = table_5_org[DEGREES_COMPLEMENT - degrees][2 * (ZODIAC_COMPLEMENT - zodiac)];
                line[2] = table_5_org[DEGREES_COMPLEMENT - degrees][2 * (ZODIAC_COMPLEMENT - zodiac) + 1];
                line[3] = -1;
            }
            else System.out.println("Error");
            return line;
        } */
}

