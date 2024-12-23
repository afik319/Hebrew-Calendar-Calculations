/**
 * Write a description of class table_10 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_10
{
    static final int ZODIAC_COMPLEMENT = 11;
    static final int FULL_TABLE_LEN = 16;
    static final int DEGREES_COMPLEMENT = 30;
    
    static final double table_10[][] = { //לבדוק טבלה פעמיים?
        { 0, 0,34, 2,27, 4},
        { 5, 0,39, 2,30, 4},
        {11, 0,44, 2,33, 4},
        {16, 0,48, 2,35, 4},
        {22, 0,53, 2,38, 4},
        {27, 0,57, 2,40, 4},
        {32, 0, 1, 3,42, 4},
        {39, 0, 6, 3,44, 4},
        {43, 0,10, 3,46, 4},
        {48, 0,14, 3,48, 4},
        {54, 0,18, 3,50, 4},
        {59, 0,22, 3,52, 4},
        { 4, 1,27, 3,54, 4},
        { 9, 1,31, 3,55, 4},
        {15, 1,34, 3,57, 4},
        {20, 1,38, 3,58, 4},
        {25, 1,42, 3, 0, 5},
        {30, 1,46, 3, 1, 5},
        {35, 1,49, 3, 2, 5},
        {40, 1,53, 3, 3, 5},
        {46, 1,56, 3, 4, 5},
        {51, 1, 0, 4, 5, 5},
        {56, 1, 3, 4, 6, 5},
        { 1, 2, 7, 4, 7, 5},
        { 6, 2,10, 4, 7, 5},
        {14, 2,13, 4, 8, 5},
        {15, 2,16, 4, 8, 5},
        {20, 2,19, 4, 8, 5},
        {25, 2,22, 4, 9, 5},
        {30, 2,25, 4, 9, 5},
        {34, 2,27, 4, 9, 5}};

        
    public static double[] get_line(int zodiac , int degrees) {
        double[] line = new double[] {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
        int fit_zodiac;
        boolean isNorth , down_direction;
        if(zodiac == 0 || zodiac == 5 || zodiac == 6 || zodiac == 11) 
            fit_zodiac = 0;
        else if (zodiac == 1 || zodiac == 4 || zodiac == 7 || zodiac == 10)
            fit_zodiac = 1;
        else if (zodiac == 2 || zodiac == 3 || zodiac == 8 || zodiac == 9)
            fit_zodiac = 2;
        else {
            System.out.println("Error-----------------------------------------Error");
            fit_zodiac = -1;}
        down_direction = (zodiac == 0 || zodiac == 1 || zodiac == 2 || zodiac == 6 || zodiac == 7 || zodiac == 8)? true : false; 
        isNorth = (0 <= zodiac && zodiac <= 5)? true : false; 
        line[1] = (down_direction)? table_10[degrees][2 * fit_zodiac]
                                  : table_10[DEGREES_COMPLEMENT - degrees][2 * fit_zodiac]; //minuetes
        line[2] = (down_direction)? table_10[degrees][2 * fit_zodiac + 1] 
                                  : table_10[DEGREES_COMPLEMENT - degrees][2 * fit_zodiac + 1]; //deg
        line[0] = (isNorth)? -1 : 1;
        return line;
    }
}
