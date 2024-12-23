import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * CalendarController class is responsible for handling the UI interactions 
 * and updating the calendar display based on the selected year and month.
 */
public class CalendarController {
	
	private static final int INFORMATION_COLUMN = 1 , DATA_COLUMNS = 15;
	private static final int INFORMATION_COLUMN_INDEX = DATA_COLUMNS;
	private static final int COLUMNS_COUNT = DATA_COLUMNS + INFORMATION_COLUMN;
	
	private static final int DATA_WIDTH = 50 , DATA_HEIGHT = 30;
	private static final int INFORMATION_DATA_WIDTH_RATIO = 5;
	private static final double INFORMATION_WIDTH = DATA_WIDTH * INFORMATION_DATA_WIDTH_RATIO;
	private static final int FIRST_X_INDEX = 0;
	
	private static final String DEFAULT_FONT = "Arial";
	private static final int TITLE_FONT_SIZE = 16;
				   	
	private static final int NOT_SET_YET = -1;
	private static final int VALID = 0;
	private static final int TOO_BIG = 1;
	private static final int TOO_SMALL = -1;
	
	private static final int MIN_MONTH_INPUT = 0;
	private static final int TISHREI_INDEX = 0;
	private static final int REG_ELUL_INDEX = 11;
	private static final int LEAP_ELUL_INDEX = 12;
	private static final int MAX_MONTHES_COUNT = 13;
    private static final int MIN_YEAR_INPUT = 5778 , MAX_YEAR_INPUT = 9999;
    
    static final String[] MONTHES_NAME_REGULAR = {"תשרי" , "חשוון" , "כסלו" , "טבת" , "שבט" , "אדר" , "ניסן" , "אייר" , "סיוון" , "תמוז" , "אב" , "אלול"};           
    static final String[] MONTHES_NAME_LEAP = {"תשרי" , "חשוון" , "כסלו" , "טבת" , "שבט" , "אדר א'" , "אדר ב'" , "ניסן" , "אייר" , "סיוון" , "תמוז" , "אב" , "אלול"};
    private static final int FIRST_FIX_LINE = 6;
    private static final int LAST_FIX_LINE = 10;
    private static final int FIX_LINES_LAYOUT_Y_ADD = 2 * DATA_HEIGHT;
	private static final int FIX_LINE_COLUMNS_COUNT = 3;
	private static final int CONNECT_START_INDEX = 12;
	private static final int FIX_LINE_SIGN_INDEX = 2;
	private static final int ERROR = -1;
	private static final int RIGHT_FIX_LINES_LAYOUT_Y_ADD = -5 * DATA_HEIGHT;
	private static final int RIGHT_FIX_LINE_SIGN_INDEX = 11;
	private static final int RIGHT_FIX_LINE_INDEX = 11;
	private static final int RIGHT_FIX_LINE_COLUMNS = 7;
	private static final int RIGHT_FIX_LINE_START_INDEX = 9;
	private static final int RIGHT_FIX_LINE_DATA_TEXT_FIELD_INDEX = RIGHT_FIX_LINE_COLUMNS - 1;
	
	
    @FXML
    private ComboBox<String> yearCombo , monthCombo; 
    @FXML
    private AnchorPane mainTitlesAnchro , dataAnchor;
    @FXML
    public TextField textFieldData; //remove afik
    @FXML
    public TextField textFieldHour; //remove afik
    private ObservableList<Node> linesSixTillTenTitles;
    private static CalendarCreator cal1;
         
    /**
     * Initializes the controller. Clears the data anchor pane and sets the 
     * controller reference in the CalendarCreator class.
     */
    public void initialize() { 	
    	linesSixTillTenTitles = FXCollections.observableArrayList(dataAnchor.getChildren());
    	CalendarCreator.setController(this); 
    	comboBoxesInitialize();
    }
    
    /**
     * Initializes the combo boxes. Sets a listener on the yearCombo to allow only numeric input
     * and sets the maximum number of visible rows in the monthCombo.
     */
    private void comboBoxesInitialize() {  	
    	setNumericOnly(yearCombo);
    	monthCombo.setVisibleRowCount(MAX_MONTHES_COUNT);
    	monthCombo.setEditable(false);
    }
    
    /**
     * Sets a ComboBox to accept only numeric input.
     * 
     * @param comboBox the ComboBox to restrict to numeric input.
     */
    private static void setNumericOnly(ComboBox<String> comboBox) {
    	comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                	comboBox.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    /* Resets the data anchor pane. */    
    private void dataAnchorReset() {
    	dataAnchor.getChildren().clear(); 
		dataAnchor.getChildren().addAll(linesSixTillTenTitles);
    }
    
    /**
     * Handles the action event triggered by the user selecting the year and month.
     * Initializes the CalendarCreator and prints the lines.
     * 
     * @param event the action event triggered by the user.
     */
    @FXML
    void linesCalc(ActionEvent event) {   	
    	monthComboSet(event.getSource());
    	if(!isNullOrEmpty(yearCombo) && !isNullOrEmpty(monthCombo) && validYearStatus(getYearNum()) == VALID) {
    		dataAnchorReset();
    		cal1 = new CalendarCreator();
    		printLines();    		 
		}
    }
                
    /**
     * Handles the key pressed event to change the year or month based on the pressed key and Alt status.
     * 
     * @param event the key event.
     */
    @FXML
    void keyPressed(KeyEvent event) {
    	KeyCode pressed = event.getCode();
    	boolean isAltDown = event.isAltDown();
    	if(!isNullOrEmpty(yearCombo) && !isNullOrEmpty(monthCombo))
	    	if(pressed == KeyCode.Y || pressed == KeyCode.M)
	    		oneChangeSet(pressed , isAltDown); 
    }
    
    /**
     * Retrieves the year value selected by the user from the yearCombo ComboBox.
     * 
     * @return the year value as an integer.
     */
    public int getYearNum() { 
    	if(isNullOrEmpty(yearCombo))
    		return NOT_SET_YET;
    	String userYearInput = yearCombo.getValue();  	
    	int userYear = Integer.parseInt(userYearInput);
    	return userYear;
    }
    
    /**
     * Retrieves the current selected month index.
     * 
     * @return the current selected month index.
     */
    public int getCurMonthIndex() {
    	return monthCombo.getSelectionModel().getSelectedIndex();
    }
    
    /**
     * Sets the change for either the year or the month based on the pressed key and Alt status.
     * 
     * @param pressed the pressed key.
     * @param isAltDown whether the Alt key is pressed.
     */
    private void oneChangeSet(KeyCode pressed , boolean isAltDown) {
    	int change = isAltDown? -1 : 1;
    	if(pressed == KeyCode.Y) 
    		setOneYearChange(change);   
    	else if(pressed == KeyCode.M) 
    		setOneMonthChange(change);	
    }
    
    /**
     * Sets a change of one year and updates the combo boxes accordingly.
     * 
     * @param change the change to apply to the year.
     */
    private void setOneYearChange(int change) {
    	int currentYearNum = getYearNum();
    	int newYearNum = getYearAfterChange(change);

    	if(validYearStatus(newYearNum) == VALID) {
        	int appropriateMonthIndex = Calculations.getMonthIndexOtherYear(getCurMonthIndex() , currentYearNum , newYearNum);
        	yearSelect(newYearNum); //yearSelect triger month reset.
        	monthSelect(appropriateMonthIndex);       	
        }
    }
    
    /**
     * Sets a change of one month and updates the combo boxes accordingly.
     * 
     * @param change the change to apply to the month.
     */
    private void setOneMonthChange(int change) { 
    	int currentMonthIndex = getCurMonthIndex();
		int newMonthIndex = currentMonthIndex + change;
		int newMonthValidStatus = validMonthStatus(newMonthIndex); 
		int yearsToAdd = newMonthValidStatus; //valid status = years to add to current year (-1/0/1)
		
		if(newMonthValidStatus == VALID) 
			monthSelect(newMonthIndex); //trigger		
		else { //month change cause to year change
			int newYear = getYearNum() + yearsToAdd;
			if(validYearStatus(newYear) == VALID) {
				yearSelect(newYear); //new afik
				if(newMonthValidStatus == TOO_BIG)
					monthSelect(TISHREI_INDEX);
				else {
					int newLastMonth = Calculations.isLeap(newYear)? LEAP_ELUL_INDEX : REG_ELUL_INDEX;
					monthSelect(newLastMonth);
				}
			}
		}
    }
    
    /**
     * Calculates the new year value after applying a change.
     * 
     * @param change the change to apply to the current year.
     * @return the new year value.
     */
    private int getYearAfterChange(int change) {
        int currentYearNum = getYearNum();
        int newYearValue = currentYearNum + change;
        return newYearValue;
    }
        
    /**
     * Validates the year status.
     * 
     * @param yearNum the year number to validate.
     * @return the validation status.
     */
    private int validYearStatus(int yearNum){
    	if(MIN_YEAR_INPUT <= yearNum && yearNum <= MAX_YEAR_INPUT)
    		return VALID;
    	else if(yearNum > MAX_YEAR_INPUT)
    		return TOO_BIG;
    	else {
    		Calculations.errorAlert("אין אפשרות לחשב!" , "שנה מוקדמת מדי" , "נא הזן שנה בין 5778 ל 9999");
    		return TOO_SMALL;
    	}
    }
    
    /**
     * Validates the month status.
     * 
     * @param monthNum the month number to validate.
     * @return the validation status.
     */
    private int validMonthStatus(int monthNum){
    	int max_month_index = monthCombo.getItems().size() - 1;
    	if(MIN_MONTH_INPUT <= monthNum && monthNum <= max_month_index) //afik: pls check correction
    		return VALID;
    	else if(monthNum > max_month_index)
    		return TOO_BIG;
    	else return TOO_SMALL;
    }
    
    /**
     * Checks if the given string is null or empty.
     * 
     * @param str the string to check.
     * @return true if the string is null or empty, false otherwise.
     */
    private static boolean isNullOrEmpty(String str) {
    	return (str == null || str.isEmpty());
    }
    
    private static boolean isNullOrEmpty(ComboBox<String> comboBox) {
    	String comboContent = comboBox.getValue();
    	return isNullOrEmpty(comboContent);
    }
    
    /**
     * Updates the month combo box items based on the selected year.
     * 
     * @param source the source object of the event.
     */
    private void monthComboSet(Object source) {
    	if(!isNullOrEmpty(yearCombo) && source == yearCombo) {
    		monthCombo.getItems().clear();
    		String[] monthList = Calculations.isLeap(getYearNum())? MONTHES_NAME_LEAP : MONTHES_NAME_REGULAR;
    		monthCombo.getItems().addAll(monthList);
    	}
    }
    
    /**
     * Selects the specified year in the combo box.
     * 
     * @param newYearValue the new year value to select.
     */
    private void yearSelect(int newYearValue) { 
    	yearCombo.setValue("" + newYearValue); //trigger the yearCombo onAction
    }
      
    /**
     * Selects the specified month in the combo box.
     * 
     * @param newMonthIndex the new month index to select.
     */
    private void monthSelect(int newMonthIndex) {
    	monthCombo.getSelectionModel().select(newMonthIndex); //trigger the yearCombo onAction
    }  
    
    /**
     * Prints the lines generated by the CalendarCreator. Iterates through the lines
     * and creates a new text line for each.
     */
	private void printLines() {
    	int curLineNum = CalendarCreator.getCurLineNum();
    	for(int lineNumber = 1 ; lineNumber < FIRST_FIX_LINE ; lineNumber++) 
    		newTextLine(FIRST_X_INDEX , COLUMNS_COUNT , cal1.getLine(lineNumber));
    	for(int lineNum = FIRST_FIX_LINE ; lineNum <= LAST_FIX_LINE ; lineNum++) {
    		newFixLine(cal1.getLine(lineNum));
    	}   
    	for(int lineNumber = 11 ; lineNumber < curLineNum ; lineNumber++) {
    		newRightFixLine(cal1.getLine(lineNumber));
    	}  //afik fix this */
    	//afik remove next line
    	
    }
    
    /**
     * Creates a new text line filled by text fields in the data anchor pane 
     * based on the provided line data.
     * 
     * @param startIndex the starting index for the text fields.
     * @param columnsCount the total number of columns to print.
     * @param line the line data to be displayed.
     */
    private void newTextLine(int startIndex , int columnsCount , Line line) {
    	int curLineNum = line.getLineNum();
    	TextField[] data = new TextField[columnsCount]; 
    	int lastIndex = startIndex + columnsCount;
    	for(int col = startIndex ; col < lastIndex ; col++) {  
    		data[col] = new TextField("");
    		setTextField(data[col] , col , col  , line);
    	}
    	setDataFieldProperties(data[INFORMATION_COLUMN_INDEX]);
    	LinesTitles.setLineTitle(data[INFORMATION_COLUMN_INDEX] , curLineNum);
    }
    
    /**
     * Creates a new fixed line in the data anchor pane based on the provided line data.
     * 
     * @param line the line data to be displayed.
     */
    private void newFixLine(Line line) {
    	TextField[] data = new TextField[FIX_LINE_COLUMNS_COUNT];   	
    	for(int col = 0 ; col < FIX_LINE_COLUMNS_COUNT ; col++) {
    		data[col] = new TextField("");
    		setTextField(data[col] , col , CONNECT_START_INDEX + col , line);
    	}
    }
    
    private void newRightFixLine(Line line) {
    	int curLineNum = line.getLineNum();
    	TextField[] data = new TextField[RIGHT_FIX_LINE_COLUMNS]; 
    	for(int col = 0 ; col < RIGHT_FIX_LINE_COLUMNS ; col++) {
    		int curIndex = RIGHT_FIX_LINE_START_INDEX + col;
    		data[col] = new TextField("");
    		setTextField(data[col] , curIndex , curIndex , line);
    	}
    	setDataFieldProperties(data[RIGHT_FIX_LINE_DATA_TEXT_FIELD_INDEX]);
    	LinesTitles.setLineTitle(data[RIGHT_FIX_LINE_DATA_TEXT_FIELD_INDEX] , curLineNum);
    } //afik check
    
    /**
     * Sets the properties for each text field in the data anchor pane.
     * 
     * @param t1 the text field to set properties for.
     * @param indexInShownLine the index of the text field in the displayed line.
     * @param contentIndex the position index of the text field in the content line.
     * @param line the line data to be displayed.
     */
    private void setTextField(TextField t1 , int indexInShownLine , int contentIndex , Line line) {
    	textFieldInitialize(t1); //new afik
    	int curLineNum = line.getLineNum();
    	setTextFieldContent(t1 , indexInShownLine , contentIndex , line , curLineNum); 
    	setTextFieldPlacement(t1 , curLineNum , indexInShownLine);	
    }
    
    /**
     * Initializes the text field with default properties and adds it to the data anchor pane.
     * 
     * @param t1 the text field to initialize.
     */
    private void textFieldInitialize(TextField t1) {
    	t1.setAlignment(Pos.CENTER);
    	t1.setEditable(false);
		t1.setFocusTraversable(false);		   	
		dataAnchor.getChildren().add(t1); //afik changed place
    }
       
    /**
     * Sets the content of the text field based on its index and the line data.
     * 
     * @param t1 the text field to set content for.
     * @param indexInShownLine the index of the text field in the displayed line.
     * @param contentIndex the position index of the text field in the content line.
     * @param line the line data to be displayed.
     * @param curLineNum the current line number.
     */
    private void setTextFieldContent(TextField t1 , int indexInShownLine , int contentIndex , Line line , int curLineNum) {
    	if(indexInShownLine != INFORMATION_COLUMN_INDEX)
			t1.setText("" + line.get(contentIndex));
    	if(isFixLineNum(curLineNum) && isShownLineSignIndex(indexInShownLine)) 
    		t1.setText(Calculations.convertSignValueToString(line.getSign())); 
    	else if(isRightFixLineNum(curLineNum) && isShownSignIndexRightFixLine(indexInShownLine))
    		t1.setText(Calculations.convertSignValueToString(line.getLightsPlaceSign())); 
    }
    
    /**
     * Sets the design properties for the text field, including alignment, size, and layout.
     * 
     * @param t1 the text field to set design properties for.
     * @param curLineNum the current line number.
     * @param indexInShownLine the index of the text field in the displayed line.
     */
    private void setTextFieldPlacement(TextField t1 , int curLineNum , int indexInShownLine) {
    	t1.setPrefSize(DATA_WIDTH , DATA_HEIGHT);
    	t1.setLayoutX(DATA_WIDTH * indexInShownLine);
    	t1.setLayoutY((curLineNum - 1) * DATA_HEIGHT + getAdditionLayoutY(curLineNum));
    }
    
    /**
     * Calculates the additional Y layout based on the line number.
     * 
     * @param lineNum the line number to calculate the additional Y layout for.
     * @return the additional Y layout value.
     */
    private int getAdditionLayoutY(int lineNum) {
    	if(lineNum < FIRST_FIX_LINE)
    		return 0; //afik consider add start index
    	else if(isFixLineNum(lineNum))
    		return FIX_LINES_LAYOUT_Y_ADD;
    	else if(isRightFixLineNum(lineNum)) {
    		return RIGHT_FIX_LINES_LAYOUT_Y_ADD;
    	}
    	else {
    		Calculations.errorAlert("Error!" , "Invalid line number" , "" + lineNum);
    		return ERROR;
    	}
    }
    
    /**
     * Checks if the line number is a fix line number.
     * 
     * @param lineNumber the line number to check.
     * @return true if the line number is a fix line number, false otherwise.
     */
    private boolean isFixLineNum(int lineNumber) {
    	return (FIRST_FIX_LINE <= lineNumber && lineNumber <= LAST_FIX_LINE);
    }
    
    private boolean isRightFixLineNum(int lineNumber) {
    	return (lineNumber >= RIGHT_FIX_LINE_INDEX);
    }
    
    /**
     * Checks if the index is the sign index for the shown line.
     * Assumes that this is fix line.
     * 
     * @param index the index to check.
     * @return true if the index is the sign index for the shown line, false otherwise.
     */
    private boolean isShownLineSignIndex(int index) {
    	return index == FIX_LINE_SIGN_INDEX;
    }
    
    private boolean isShownSignIndexRightFixLine(int index) {
    	return index == RIGHT_FIX_LINE_SIGN_INDEX;
    }
    
    /**
     * Sets the properties for the data field in the data anchor pane.
     * 
     * @param t1 the data field to set properties for.
     */
    private void setDataFieldProperties(TextField t1) {
    	t1.setPrefWidth(INFORMATION_WIDTH);
    	t1.setFont(Font.font(DEFAULT_FONT , FontWeight.BOLD, FontPosture.ITALIC, TITLE_FONT_SIZE));
    	t1.setAlignment(Pos.CENTER_RIGHT);
    }
    
    /**
     * Sets the CalendarController instance to be used by the CalendarCreator.
     * 
     * @param cal the CalendarCreator instance.
     */
    public void setCreator(CalendarCreator cal) {
    	cal1 = cal;
    }
    
}
