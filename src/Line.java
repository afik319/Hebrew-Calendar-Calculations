import java.util.ArrayList;

/**
 * Line class represents a line of data with multiple categories and columns.
 * It extends ArrayList<Double> to store the data and provides various methods 
 * for manipulating the line's data.
 * 
 * @version (04.06.2024)
 */
public class Line extends ArrayList<Double>{
	private static final long serialVersionUID = 1L;
	private static final int CATEGORIES_COUNT = 5;
	private static final int COLUMNS_IN_CATEGORY = 3;
	private static final int MIN_TO_MIN = 1;
	private static final int MIN_TO_DEG = 60;
	private static final int DEG_TO_ZOD = 30;
	private static final int MIN_TO_ZOD = MIN_TO_DEG * DEG_TO_ZOD;
	private static final int MAX_ZOD = 12;
	private static final int MIN_TO_HOUR = 60;
	private static final int HOUR_TO_DAY = 24;
	private static final int MIN_TO_DAY = 1440;
	private static final int MAX_DAY = 7;
	private static final int ONE_UNIT_STEP = 1;
	private static final int TWO_UNIT_STEP = 2;
	//max value for each column
	private static final int[] LIMITS = {MIN_TO_DEG  , DEG_TO_ZOD  , MAX_ZOD , 
									     MIN_TO_DEG  , DEG_TO_ZOD  , MAX_ZOD , 
									     MIN_TO_DEG  , DEG_TO_ZOD  , MAX_ZOD ,
									     MIN_TO_DEG  , DEG_TO_ZOD  , MAX_ZOD ,
									     MIN_TO_HOUR , HOUR_TO_DAY , MAX_DAY};
	private static final int COLUMNS_COUNT = 15;
	private static final int FLAG_COLUMNS_COUNT = 3;
	static final int FULL_LINE_COLUMNS_COUNT = COLUMNS_COUNT + FLAG_COLUMNS_COUNT;
	static final int SIGN_COLUMN_INDEX = 15;
	static final int LIGHTS_PLACE_FIX_SIGN_COLUMN_INDEX = 16;
	//private static final double POSITIVE_SIGN = 1;
	private static final int[] MinColRatioInDegCategory = {MIN_TO_MIN , MIN_TO_DEG , MIN_TO_ZOD};
	//private static final int FIX_LINE_CONTENT_START_INDEX = 12;
	private static final int NEGATIVE_SIGN = -1;
	
	//afik remove next line
	private static final int[] MinColRatioInDegCategory2 = {MIN_TO_MIN , MIN_TO_HOUR , MIN_TO_DAY};
	
	private int lineNum;
	
	/**
	 * Default constructor that initializes the line with zero values.
	 */
	public Line() {
		super();
		for(int i = 0 ; i < COLUMNS_COUNT + FLAG_COLUMNS_COUNT ; i++){
			add(0.0);
		}
	}
	
	/**
	 * Constructor that initializes the line with the given array of values.
	 * 
	 * @param arr the array of values to initialize the line with.
	 */
	public Line(double[] arr) {
		super();
		for(int i = 0 ; i < COLUMNS_COUNT + FLAG_COLUMNS_COUNT ; i++){
			add(arr[i]);
		}
	} //afik fix 3 last cells and check hashpaot
	
	public Line(Line other) {
		super();
		for(int i = 0 ; i < COLUMNS_COUNT + FLAG_COLUMNS_COUNT ; i++){
			add(other.get(i));
		}
	} //new afik
	
	public void setSign(double sign){
		set(SIGN_COLUMN_INDEX , sign);
	}
	
	public int getSign(){
		return (int)(double)get(SIGN_COLUMN_INDEX);
	}
	
	public int getLightsPlaceSign() {
		return (int)(double)get(LIGHTS_PLACE_FIX_SIGN_COLUMN_INDEX);
	}
	
	public void setLightsPlaceSign(double sign){
		set(LIGHTS_PLACE_FIX_SIGN_COLUMN_INDEX , sign);
	}
	
	public String getSignString(){
		int sign = (int)(double)get(SIGN_COLUMN_INDEX);
		String signString = Calculations.convertSignValueToString(sign);
		return signString;
	}
	
	/**
	 * Gets the line number.
	 * 
	 * @return the line number.
	 */
	public int getLineNum() {
	    return lineNum;
	}
	
	/**
	 * Sets the line number. If the provided line number is invalid, 
	 * an error alert is shown.
	 * 
	 * @param lineNum the line number to set.
	 */
	public void setLineNum(int lineNum) {
		if(lineNum > 0) {
			this.lineNum = lineNum;
		}
		else Calculations.errorAlert("Error!" , "Invalid line number" , "\"" + lineNum + "\"");
	}
	
	/**
	 * Adds the values of another line to this line and returns the result as a new Line object.
	 * 
	 * @param other the other line to add.
	 * @return a new Line object containing the result.
	 */
	public Line add(Line other) {
		Line result = new Line();
		for(int category = 0 ; category < CATEGORIES_COUNT ; category++) {
			int categoryStartIndex = category * COLUMNS_IN_CATEGORY;
			addCategoryNoModulo(other , result , categoryStartIndex);
			moduloFix(result , categoryStartIndex);
		}
		return result;
	}
		
	//remove boolean afik
	public Line fixLineSignedAdd(Line other , int categoryStartIndex , boolean afik) {
		Line result = fixLineSignedAddNoModulo(other , categoryStartIndex , afik);
		moduloFix(result , categoryStartIndex); //lines 6+7 cannot sum to 1 day
		return result;
	} //new afik. 
	
	//remove boolean afik
	public Line fixLineSignedAddNoModulo(Line other , int categoryStartIndex , boolean afik) {
		Line result = new Line();
		boolean firstPositive = (!(getSign() == NEGATIVE_SIGN)); //TO ALLOW ZERO TOO
		boolean otherPositive = (!(other.getSign() == NEGATIVE_SIGN));
		int categoryEndIndex = categoryStartIndex + COLUMNS_IN_CATEGORY;
		double firstContent , otherContent;
		
		if(!(firstPositive ^ otherPositive)) {
			for(int column = categoryStartIndex ; column <  categoryEndIndex ; column++) {
				firstContent = get(column);
				otherContent = other.get(column);
				result.set(column , firstContent + otherContent);	
			}
			result.setSign(this.getSign());
		}
		else {
			Line bigger = getBiggerInCategory(other , categoryStartIndex);
			Line smaller = (bigger == other)? this : other;
			if(afik) //afik remove keep only this line - regular line
				result = subDegCategoryNoModulo(bigger , smaller , categoryStartIndex);
			else result = subDegCategoryNoModulo2(bigger , smaller , categoryStartIndex);
			boolean resultPositive = (bigger == this) ^ otherPositive;
			result.setSign(Calculations.convertBooleanSignToDouble(resultPositive));
		}
		return result;
	} //new afik
	
	private static Line subDegCategoryNoModulo2(Line bigger , Line smaller , int categoryStartIndex) {
		Line result = new Line();
		double firstTotal = 0 , secondTotal = 0 , resultTotal;
		for(int col = 0 ; col < COLUMNS_IN_CATEGORY ; col++) {
			int curIndex = categoryStartIndex + col;
			firstTotal += bigger.get(curIndex) * MinColRatioInDegCategory2[col];
			secondTotal += smaller.get(curIndex) * MinColRatioInDegCategory2[col];
		}
		resultTotal = firstTotal - secondTotal;
		result.set(categoryStartIndex, resultTotal);
		return result;
	} //remove method afik
	
	private static Line subDegCategoryNoModulo(Line bigger , Line smaller , int categoryStartIndex) {
		Line result = new Line();
		double firstTotal = 0 , secondTotal = 0 , resultTotal;
		for(int col = 0 ; col < COLUMNS_IN_CATEGORY ; col++) {
			int curIndex = categoryStartIndex + col;
			firstTotal += bigger.get(curIndex) * MinColRatioInDegCategory[col];
			secondTotal += smaller.get(curIndex) * MinColRatioInDegCategory[col];
		}
		resultTotal = firstTotal - secondTotal;
		result.set(categoryStartIndex, resultTotal);
		return result;
	} //new afik
	
	private Line getBiggerInCategory(Line other , int categoryStartIndex) {
		boolean isFirstBigger = true;
		for(int col = COLUMNS_IN_CATEGORY ; col > 0; col--) {
			int curIndex = categoryStartIndex - 1 + col;
			if(Calculations.isDifferDouble(get(curIndex), other.get(curIndex))){
				isFirstBigger = Calculations.isBiggerDouble(get(curIndex), other.get(curIndex));
				break;
			}
		}
		return isFirstBigger? this : other;
	} //new afik
	
	/**
	 * Multiplies the values of this line by a given ratio and returns the result as a new Line object.
	 * 
	 * @param ratio the ratio to multiply by.
	 * @return a new Line object containing the result.
	 */
	public Line mult(double ratio) {
		Line result = new Line();
		multCategoryNoModulo(result , ratio);
		for(int category = 0 ; category < CATEGORIES_COUNT ; category++) {
			int categoryStartIndex = category * COLUMNS_IN_CATEGORY;
			moduloFix(result , categoryStartIndex);
		}
		return result;
	}
	
	/**
	 * Fixes the values in the line to stay within their respective limits.
	 * 
	 * @param line the line to fix.
	 * @param categoryStartIndex the starting index of the category to fix.
	 */
	public static void moduloFix(Line line , int categoryStartIndex) {
		int col1Index = categoryStartIndex;
		int col2Index = categoryStartIndex + ONE_UNIT_STEP;
		int col3Index = categoryStartIndex + TWO_UNIT_STEP;

		double col2UnitsInCol1 = (int)(double)line.get(col1Index) / LIMITS[col1Index];
		columnModulo(line , col1Index);
		increaseColumn(line , col2Index , col2UnitsInCol1);
		
		double col3UnitsInCol2 = (int)(double)line.get(col2Index) / LIMITS[col2Index];
		columnModulo(line , col2Index);
		increaseColumn(line , col3Index , col3UnitsInCol2);
		
		columnModulo(line , col3Index);
	}
	
	/**
	 * Applies the modulo operation to the value at the given index in the line.
	 * 
	 * @param result the result line.
	 * @param index the index to apply the modulo operation to.
	 */
	private static void columnModulo(Line line , int index) {
		line.set(index , line.get(index) % LIMITS[index]);
	}
	
	/**
	 * Increases the value at the given index in the line by the given amount.
	 * 
	 * @param result the result line.
	 * @param index the index to increase.
	 * @param add the amount to increase by.
	 */
	private static void increaseColumn(Line line , int index , double add) {
		line.set(index , line.get(index) + add);
	}
	
	/**
	 * Adds the values of the corresponding columns of specific category from this
	 * line and another line to the result line without applying the modulo operation.
	 * 
	 * @param other the other line to add.
	 * @param result the result line.
	 * @param categoryStartIndex the starting index of the category.
	 */
	public void addCategoryNoModulo(Line other , Line result , int categoryStartIndex) {
		for(int column = 0 ; column < COLUMNS_IN_CATEGORY ; column++) {
			int index = categoryStartIndex + column;
			double firstContent = get(index);
			double otherContent = other.get(index);
			result.set(index , firstContent + otherContent);	
		}
	}
	
	/**
	 * Multiplies the values of the columns in this line by the given ratio
	 * and stores the result in the result line without applying the modulo operation.
	 * 
	 * @param result the result line.
	 * @param ratio the ratio to multiply by.
	 */
	public void multCategoryNoModulo(Line result , double ratio) {
		for(int index = 0 ; index < COLUMNS_COUNT + FLAG_COLUMNS_COUNT ; index++) {
			result.set(index , get(index) * ratio);	
		}
	}
}
