import java.time.LocalDate;
import java.util.ArrayList;

/**
 * CalendarCreator class is responsible for calculating and managing the lines of the calendar
 * based on the selected month. It acts as a service class for CalendarController.
 * 
 * @version (04.06.2024)
 */
public class CalendarCreator {
	
    private static final int CYCLE_YAERS = 19;

	private static final int FIX_LINE_CONTENT_START_INDEX = 12;
	private static final int LIGHTS_PLACE_DEG_INDEX = 10;
	private static final int TISHREI_INDEX = 0;

	private static final int NISSAN_TO_ELUL_MONTHES = 5;
	private static final double TISHREI_OR_NISSAN_FIX = 2;
	private static final double POSITIVE_SIGN = 1;
	private static final double NEGATIVE_SIGN = -1;

	private static final double NO_SIGN = 0;

	//private static final int RIGHT_FIX_LINE_LIGHTS_START_INDEX = 9; afik repair
		
    private ArrayList<Line> lines;
    private static CalendarController controller;
    private static int curLineNum;
    static int yearNumber , prevYearCycleNumber , prevRemainYears , remainYears;
    static int monthIndex;
    private boolean isLeap;
    
    /**
     * Constructs a CalendarCreator object, initializes the year and month,
     * connects to other components, and calculates the lines for the calendar.
     */
    public CalendarCreator() {
    	exportConnectionsToPackage();
    	pullYearAndMonth();
    	
    	lines = new ArrayList<Line>();
    	curLineNum = 0;
    	isLeap = Calculations.isLeap(yearNumber); //was checked if yearNumber not set yet. removed afik.	
    	linesCalc();
    } 
    
    /* Exports connections to other components in the package. */
    private void exportConnectionsToPackage() {
    	controller.setCreator(this);
    	Table_2.setCreator(this);
    	Calculations.setCreator(this);
    	LinesTitles.setCreator(this);
    }
    
    /**
     * Retrieves the year and month from the controller.
     */
    private void pullYearAndMonth() {
    	yearNumber = controller.getYearNum();
    	monthIndex = controller.getCurMonthIndex();
    }
    
    /**
     * Sets the CalendarController instance to be used by the CalendarCreator.
     * 
     * @param otherController the CalendarController instance.
     */
    public static void setController(CalendarController otherController) {
        controller = otherController;
    }
    
    /**
     * Gets the current line number (that is not set yet).
     * 
     * @return the current line number.
     */
	public static int getCurLineNum() {
	    return curLineNum;
	}
	
	/**
     * Retrieves the current month index.
     * 
     * @return the current month index.
     */
	public int getCurMonthIndex() {
	    return monthIndex;
	}
	
	/**
     * Retrieves the remaining years in the current cycle.
     * 
     * @return the remaining years.
     */
    public int getRemainYears() {
        return remainYears;
    }
    
    /**
     * Retrieves the previous remaining years in the cycle.
     * 
     * @return the previous remaining years.
     */
    public int getPrevRemainYears() {
        return prevRemainYears;
    }
	
	/**
     * Retrieves the previous year cycle number.
     * 
     * @return the previous year cycle number.
     */
    public int getPrevYearCycleNumber() {
        return prevYearCycleNumber;
    }
    
    /**
     * Returns whether the current Hebrew year is a leap year.
     * 
     * @return true if the current year is a leap year, false otherwise.
     */
    public boolean getIsLeap() {
    	return isLeap;
    }
    
    /**
     * Retrieves the year number.
     * 
     * @return the year number.
     */
    public int getYearNum() { 
    	return yearNumber;
    }
    
    /**
     * Retrieves a line from the lines list by its index.
     * 
     * @param index the index of the line to retrieve.
     * @return the Line object at the specified index.
     */
    public Line getLine(int index) {
    	return lines.get(index);
    }
       
    /**
     * Sets the year data based on the given year number.
     * 
     * @param yearNum the year number to set the data for.
     */
    public void setDependedYears(int yearNum) {
    	prevYearCycleNumber = (yearNum - 1) / CYCLE_YAERS;
    	prevRemainYears = (yearNum - 1) % CYCLE_YAERS;
    	remainYears = yearNum % CYCLE_YAERS;
    	fullCycleFix();
    }
    
    /**
     * Adjusts the cycle year and remaining years to ensure correct values.
     */
    private void fullCycleFix() {
    	if(prevRemainYears == 0) {
    		prevRemainYears = CYCLE_YAERS;
    		prevYearCycleNumber--;
    	}
    	if(remainYears == 0) {
    		remainYears = CYCLE_YAERS;
    	}
    }
    	    
    /**
     * Calculates the lines for the calendar based on the selected year from the controller.
     * Sets the year data in Table_2 and generates new line content for the calendar.
     */
    public void linesCalc() {
    	setDependedYears(yearNumber);
    	lines.add(null); //to make the line index appropriate to the line number. 
    	curLineNum++;
    	newLineContent(Table_1.halichotCycleCalc(prevYearCycleNumber)); //line 1
    	newLineContent(Table_2.getTableLine()); //line 2
    	newLineContent(lines.get(1).add(lines.get(2))); //line 3
    	newLineContent(Table_3.get_line(monthIndex)); //line 4
    	newLineContent(lines.get(3).add(lines.get(4))); //line 5
    	newLineContent(Table_4.getSunDiffLine(lines.get(5))); //line 6
    	newLineContent(Table_5.getMoonDiffLine(lines.get(5))); //line 7
    	newLineContent(lines.get(6).fixLineSignedAdd(lines.get(7) , FIX_LINE_CONTENT_START_INDEX , true)); //line 8
    	newLineContent(Table_6.getAlligatorDiffLine(lines.get(5))); //line 9
    	newLineContent(lines.get(8).fixLineSignedAdd(lines.get(9) , FIX_LINE_CONTENT_START_INDEX , true)); //line 10
    	Line line11 = new Line(lines.get(10));
    	line11.set(LIGHTS_PLACE_DEG_INDEX , getLightsPlaceFix());
    	line11.setLightsPlaceSign(getLightsPlaceSign());
    	newLineContent(line11); //line 11
    	Line line12ConnectPart = lines.get(5).fixLineSignedAdd(lines.get(11) , FIX_LINE_CONTENT_START_INDEX , false);
    	//Line line12LightPlacePart = lines.get(5).fixLineSignedAdd(lines.get(11) , RIGHT_FIX_LINE_LIGHTS_START_INDEX);
    	
    	//part to remove - afik
    	newLineContent(line12ConnectPart);
    	int OCT97_CYCLES              =   303; 
    	int MONTH_IN_CYCLE            =   235;
    	int REG_MONTH_COUNT           =    12;
        int[] cumulative_leap_years = {0 , 0 , 0 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 4 , 4 , 4 , 5 , 5 , 5 , 6 , 6 , 7}; //index i contain the cumulative amount in the cycle till the i year. 
    	int month_count = (prevYearCycleNumber - OCT97_CYCLES) * MONTH_IN_CYCLE + getPrevRemainYears() * REG_MONTH_COUNT +  cumulative_leap_years[getPrevRemainYears()] + getCurMonthIndex(); //new
    	new_moon_date(1 , 10 , 1997 , month_count , 0 , false);
    	//line 12 - should be with modulo if this is minus sign
    	//newLineContent(line12ConnectPart.add(line12LightPlacePart)); //afik add 2 signs fix
    	
    	//end part to remove - afik
    	
	} // end of method linesCalc
    
    private Double getLightsPlaceSign() {
		int elulYearIndex = Calculations.getLastMonthIndex(yearNumber);
		boolean nissanMonth = (monthIndex + NISSAN_TO_ELUL_MONTHES == elulYearIndex);
		boolean tishreiMonth = (monthIndex == TISHREI_INDEX);
		if(tishreiMonth)
			return POSITIVE_SIGN;
		if(nissanMonth)
			return NEGATIVE_SIGN;
		return NO_SIGN;
	}

	private Double getLightsPlaceFix() {
		int elulYearIndex = Calculations.getLastMonthIndex(yearNumber);
		boolean nissanMonth = (monthIndex + NISSAN_TO_ELUL_MONTHES == elulYearIndex);
		boolean tishreiMonth = (monthIndex == TISHREI_INDEX);
		if(tishreiMonth || nissanMonth)
			return TISHREI_OR_NISSAN_FIX;
		else return 0.0;
	}

	/**
     * Adds new line content to the lines list and increments the current line number.
     * 
     * @param line the Line object containing the line content.
     */
    public void newLineContent(Line line) {
    	line.setLineNum(curLineNum);
    	lines.add(line);
    	curLineNum++;
    }
    
    //method to delete - afik
    public void new_moon_date(int day , int month , int year , int month_count , int days_to_add , boolean after_adjustment) 
    {
    	String[] days = {"שבת" , "א'" , "ב'" , "ג'" , "ד'" , "ה'" , "ו'" , "שבת"};
    	int NEW_CLOCK_ADJUSTMENT      =    12;
    	int HOUR_TO_DAY               =    24;
        double MONTH_AVERAGE_DAYS = 29 + (12 + ((44 + (33.0 / 60)) / 60)) / 24;
        int TWO_DIGITS_PLACE          =    10;
        double addition_days = month_count * MONTH_AVERAGE_DAYS + 0.5 + lines.get(11).get(15) * (((lines.get(11).get(14) + (lines.get(11).get(13) / 60.0)) / 24));
        int floor_addition_days = (int)addition_days;
        
        LocalDate date = LocalDate.of(year, month, day);
        LocalDate newDate = date.plusDays(floor_addition_days);
        
        int dayOfWeek = (newDate.getDayOfWeek().getValue() + 1);
        if(dayOfWeek == 8)
            dayOfWeek -= 7;//7 15
        int tableDay = ((int)(lines.get(12).get(14) + (lines.get(12).get(13) + 12) / 24)) % 7;
        if(tableDay + 1 == dayOfWeek)
            newDate = newDate.minusDays(1);
        else if(tableDay - 1 == dayOfWeek || (tableDay == 0 && dayOfWeek == 6) || (tableDay == 1 && dayOfWeek == 7))
            newDate = newDate.plusDays(1);
        controller.textFieldData.setStyle("-fx-text-fill: #b8860b;");
        controller.textFieldData.setText("תאריך המולד: " + newDate.getDayOfMonth() + "." + newDate.getMonthValue() + "." + newDate.getYear() + "  ");
               
        //System.out.println("שעון חורף");
        //System.out.print("תאריך המולד האמיתי: " + newDate.getDayOfMonth() + "." + newDate.getMonthValue() + "." + newDate.getYear() + "  ");
        
        int new_moon_hour = ((int)(double)lines.get(12).get(13) + NEW_CLOCK_ADJUSTMENT) % HOUR_TO_DAY;
        if(3 <= newDate.getMonthValue() && newDate.getMonthValue() <= 10)
        	new_moon_hour++;
        int new_moon_min = (int)(double)lines.get(12).get(12);
        //System.out.println("בשעה " + ((new_moon_hour < TWO_DIGITS_PLACE)? "0" : "") + new_moon_hour + ":" + (new_moon_min < TWO_DIGITS_PLACE? "0" : "") + new_moon_min);
        controller.textFieldHour.setStyle("-fx-text-fill: #b8860b; -fx-font-size: 15px;  -fx-font-weight: bold;");
        String dayS = "יום " + days[(int)(lines.get(12).get(14) + (lines.get(12).get(13) + 12) / 24)] + " ";
        controller.textFieldHour.setText(dayS + "בשעה " + ((new_moon_hour < TWO_DIGITS_PLACE)? "0" : "") + new_moon_hour + ":" + (new_moon_min < TWO_DIGITS_PLACE? "0" : "") + new_moon_min);
    }
  
} //end of class CalendarCreator
