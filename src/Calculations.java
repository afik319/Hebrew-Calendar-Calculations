import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Calculations {
	private static final ArrayList<Integer> lEAPYEARS =
			new ArrayList<Integer>(Arrays.asList(3,6,8,11,14,17,19)); 
    private static final int CYCLE_YAERS = 19;
	private static final int LEAP_ADAR_B_INDEX = 6; //AFIK CHECK
	private static final int REG_NISSAN_INDEX = 6; //AFIK CHECK
	private static final int LEAP_TO_REGULAR_INDEX_FIX = -1;
	private static final int REGULAR_TO_LEAP_INDEX_FIX = -LEAP_TO_REGULAR_INDEX_FIX;
	private static final double POSITIVE_SIGN = 1;
	private static final double NEGATIVE_SIGN = -1;
	private static final double EPSILON = 1e-12; 
	private static final int REG_ELUL_INDEX = 11;
	private static final int LEAP_ELUL_INDEX = 12;
	//without spaces
    static final String[] MONTHES_NAME_REGULAR = {"תשרי" , "חשוון" , "כסלו" , "טבת" , "שבט" , "אדר" , "ניסן" , "אייר" , "סיוון" , "תמוז" , "אב" , "אלול"};           
    static final String[] MONTHES_NAME_LEAP = {"תשרי" , "חשוון" , "כסלו" , "טבת" , "שבט" , "אדר א'" , "אדר ב'" , "ניסן" , "אייר" , "סיוון" , "תמוז" , "אב" , "אלול"};

	private static final int ERROR = -1;
	private static final int NO_SIGN = 0;
	private static final String POSITIVE_SIGN_STRING = "+";
	private static final String NEGATIVE_SIGN_STRING = "-";
	private static final String NO_SIGN_STRING = "0.0";  
    
    private static CalendarCreator cal1;
	
	
	/**
	 * Converts a Hebrew year number to its gematria representation.
	 *
	 * @param num The Hebrew year to convert.
	 * @return A string representing the Hebrew year in gematria.
	 */
     public static String convertToGematria(int num){
    	int originalNum = num;
    	String result = ""; 
        String[] units = {"א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט"};
        String[] tens = {"י" , "כ" , "ל" , "מ" , "נ" , "ס" , "ע" , "פ" , "צ"};
        String[] hundreds = {"ק" , "ר" , "ש" , "ת"};
        String[] hebrewYear = new String[7];
        int hundreds_letter = 399;
        int countOfDigits = 0;
        boolean special = (num / 1000 * 1000 == num);
        
        if(num / 1000 != 0){ //thousand's digit
            hebrewYear[countOfDigits] = units[num / 1000 - 1];
            countOfDigits++;
            num %= 1000;   
            }

        while(hundreds_letter > 0) { //hundred's digit
            while(num > hundreds_letter){
                hebrewYear[countOfDigits] = (hundreds[(hundreds_letter + 1) / 100 - 1]);
                num -= (hundreds_letter + 1);
                countOfDigits++;
                }
            hundreds_letter -= 100;
            }
        num %= 100;
        
        if(num / 10 != 0){ //tens digit
            hebrewYear[countOfDigits] = tens[num / 10 - 1];
            countOfDigits++;
            num %= 10;
            }
        
        if(num % 10 != 0){ //unity digit
            hebrewYear[countOfDigits] = units[num - 1];
            countOfDigits++;
            num = 0;
            }
            
        for(int i = 0 ; i < countOfDigits - 1 ; i++)
        	result += hebrewYear[i];
        if(countOfDigits > 1) {
        	result +=  "\"";
        	result += hebrewYear[countOfDigits - 1] + " ";
        	}
        else {
        	result += hebrewYear[countOfDigits - 1];
        	result += "'" + ((special)? " אלפים" : "") + ". ";
            }
        result += isLeap(originalNum)? "(מעוברת)" : "(פשוטה)" ;
        return result;
    }
     
     /**
      * Retrieves the current Hebrew year in gematria representation.
      *
      * @return A string representing the current Hebrew year in gematria.
      */
      public static String getGematriaYear(int yearNum){  
    	  return(convertToGematria(yearNum));
      }
      
      /**
  	 * Displays an error alert with the given title, header text, and content.
  	 * 
  	 * @param title the title of the alert.
  	 * @param headerText the header text of the alert.
  	 * @param content the content of the alert.
  	 */
  	public static void errorAlert(String title , String headerText , String content) {
  		Alert alert1 = new Alert(AlertType.ERROR); 
  		alert1.setTitle(title); 
  		alert1.setHeaderText(headerText); 
  		alert1.setContentText(content); 
  		alert1.showAndWait();
  	}
  	
  	public static int getMonthIndexOtherYear(int monthIndex , int thisYear , int otherYear) {
  		if(isLeap(thisYear) ^ isLeap(otherYear)) {
  			if(isLeap(thisYear) && monthIndex >= LEAP_ADAR_B_INDEX)
  				return monthIndex + LEAP_TO_REGULAR_INDEX_FIX;
  			else if(!isLeap(thisYear) && monthIndex >= REG_NISSAN_INDEX)
  				return monthIndex + REGULAR_TO_LEAP_INDEX_FIX;
  		}
  		return monthIndex;
  	} //afik add documantation and check
  	
  	/**
     * Checks if the given year is a leap year based on the current cycle.
     * 
     * @param yearNum the year number to check.
     * @return true if the year is a leap year, false otherwise.
     */
    public static boolean isLeap(int yearNum) {
    	int remainYears = yearNum % CYCLE_YAERS;
    	if(remainYears == 0)
    		remainYears = CYCLE_YAERS;
    	return lEAPYEARS.contains(remainYears);
    }
    
    public static int getMonthIndex(String currMonthName) {
    	String[] monthNames = cal1.getIsLeap()? MONTHES_NAME_LEAP : MONTHES_NAME_REGULAR;
    	for(int i = 0 ; i < monthNames.length ; i++) {
    		if(monthNames[i].equals(currMonthName)) {
    			return i;
    		}
    	}
    	errorAlert("Error!" , "Month not found." , "\"" + currMonthName + "\"");
    	return ERROR;
    }
    
    public static String getMonthName(int monthIndex) {
    	String[] monthNames = cal1.getIsLeap()? MONTHES_NAME_LEAP : MONTHES_NAME_REGULAR;
    	return monthNames[monthIndex];
    }
    
    /**
     * Sets the CalendarController instance to be used by the CalendarCreator.
     * 
     * @param controller the CalendarController instance.
     */
    public static void setCreator(CalendarCreator cal) {
    	cal1 = cal;
    }
    
    public static String convertSignValueToString(int signValue) {
    	if(signValue == POSITIVE_SIGN)
    		return POSITIVE_SIGN_STRING;
    	else if(signValue == NEGATIVE_SIGN)
    		return NEGATIVE_SIGN_STRING;
    	else if(signValue == NO_SIGN)
    		return NO_SIGN_STRING;
    	else errorAlert("Error!" , "Invalid sign value." , "");
    	return "Error";
    }
    
    public static double convertBooleanSignToDouble(boolean isPositiveSign) {
    	return isPositiveSign? POSITIVE_SIGN : NEGATIVE_SIGN;
    } 
    
    public static boolean isDifferDouble(double first , double second) {
    	return (Math.abs(first - second) > EPSILON);
    }
    
    public static boolean isBiggerDouble(double first , double second) {
    	boolean notEquals = isDifferDouble(first , second);
    	return (notEquals && (first > second));
    }
    
    public static int getLastMonthIndex(int yearNum) {
    	if(isLeap(yearNum))
    		return LEAP_ELUL_INDEX;
    	return REG_ELUL_INDEX;
    }

}
