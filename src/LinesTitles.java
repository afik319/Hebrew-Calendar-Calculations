import javafx.scene.control.TextField;

/**
 * LinesTitles class provides methods to set titles for lines in a calendar.
 * 
 */
public class LinesTitles {
	
    private static CalendarCreator cal1;

	/**
     * Sets the title for a given line number by using a switch statement to determine the specific title.
     * 
     * @param t1 the TextField to set the title on.
     * @param lineNumber the line number for which to set the title.
     */
	public static void setLineTitle(TextField t1 , int lineNumber) {
		String lineNumberView = "" + lineNumber + ". " ;
		String lineContent = lineNumberView;
		switch(lineNumber) {
			case 1: 
				int previousYearCycle = cal1.getPrevYearCycleNumber();
				int nextCycle = previousYearCycle + 1;
				lineContent += ("ממחזור: " + previousYearCycle + " למחזור: " + nextCycle);
				break;
			case 2:
				int previousYears = cal1.getPrevRemainYears();
				int curYears = cal1.getRemainYears();
				lineContent += ("משנה: " + previousYears + " לשנה: " + curYears);
		        break;
			case 3:
				lineContent += ("שורש שנת " + Calculations.getGematriaYear(cal1.getYearNum()));
				break;
			case 4:
				lineContent += ("מחזור לחודש " + Calculations.getMonthName(cal1.getCurMonthIndex()));
				break;
			case 5:
				lineContent += ("מולד שווה");
				break;
			case 11:
				String sign = cal1.getLine(lineNumber).getSignString();
				lineContent += ("תיקונים" + " ( " + sign + " )");
				break;
			case 12:
				String monthName = Calculations.getMonthName(cal1.getCurMonthIndex());
				lineContent += ("מולד אמיתי לחדש " + monthName);
				break;
		    default:
		    	lineContent += ("");
		    	break;
		}
		t1.setText(lineContent);
	}
    
    /**
     * Sets the CalendarController instance to be used by the CalendarCreator.
     * 
     * @param controller the CalendarController instance.
     */
    public static void setCreator(CalendarCreator cal) {
    	cal1 = cal;
    }
	
}
