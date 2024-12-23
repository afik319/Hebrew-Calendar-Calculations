/**
 * Table_2 class represents a table of data with methods to retrieve and set year data.
 * It provides information about leap years and handles calculations related to year cycles.
 * 
 * @version (04.06.2024)
 */
public class Table_2
{
    private static CalendarCreator cal1;
	
    private static final double table_2[][] = {
          { 3, 8, 0,48, 9,10,16,19,11,17,19,11,49, 8, 4, 0, 0, 0},
          { 6,16, 0,36,19, 8,32, 8,11,34, 8,11,37,17, 1, 0, 0, 0},
          {49,24, 1,14,25, 7,54,26,11,56,26,11,10,15, 7, 0, 0, 0},
          {51, 2, 2, 2, 5, 6,10,16,11,14,16,11,25, 2, 4, 0, 0, 0},
          {54,10, 2,50,14, 4,26, 5,11,31, 5,11,47, 8, 2, 0, 0, 0},
          {37,19, 3,27,20, 3,48,23,11,54,23,11,19, 6, 1, 0, 0, 0},
          {40,27, 3,15, 0, 2, 4,13,11,11,13,11, 8,15, 5, 0, 0, 0},
          {23, 6, 5,52, 5, 1,26, 1, 0,34, 1, 0,41,12, 4, 0, 0, 0},
          {26,14, 5,41,15,11,42,20,11,51,20,11,29,21, 1, 0, 0, 0},
          {28,22, 5,29,25, 9,58, 9,11, 8,10,11,18, 6, 6, 0, 0, 0},
          {11, 1, 7, 6, 1, 9,20,28,11,31,28,11,50, 3, 5, 0, 0, 0},
          {14, 9, 7,54,10, 7,36,17,11,48,17,11,39,12, 2, 0, 0, 0},
          {17,17, 7,42,20, 5,51, 6,11, 5, 7,11,27,21, 6, 0, 0, 0},
          { 0,26, 8,19,26, 4,14,25,11,28,25,11, 0,19, 5, 0, 0, 0},
          { 3, 4, 9, 8, 6, 3,29,14,11,45,14,11,49, 3, 3, 0, 0, 0},
          { 6,12, 9,56,15, 1,45, 3,11, 2, 4,11,37,12, 7, 0, 0, 0},
          {49,20,10,33,21, 0, 7,22,11,25,22,11,10,10, 6, 0, 0, 0},
          {51,28,10,21, 1,11,23,11,11,43,11,11,58,18, 3, 0, 0, 0},
          {34, 7, 0,58, 6,10,45,29,11, 6, 0, 0,38,16, 2, 0, 0, 0}};
	    
    /**
     * Retrieves a Line object representing the current year's data from the table.
     * 
     * @return Line object containing the current year's data.
     */
    public static Line getTableLine() {
    	int prevRemainYearsIndex = cal1.getPrevRemainYears() - 1;
    	return new Line(table_2[prevRemainYearsIndex]);
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
