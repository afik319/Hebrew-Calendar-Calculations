/**
 * Write a description of class table_9 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_9
{
    static final int FULL_TABLE_LEN = 16;
    
    static final double table_9[][] = { //לבדוק טבלה פעמיים?
        { 0, 0},
        {35, 0},
        {39, 0},
        {43, 0},
        {47, 0},
        {51, 0},
        {55, 0},
        {58, 0},
        { 0, 1},
        {55, 0},
        {51, 0},
        {47, 0},
        {43, 0},
        {39, 0}};
        
    public static double[] get_line(boolean isLeap , int month_num) {
        int i = month_num + ((month_num > 6 && !isLeap)? 1 : 0);        
        double[] line = new double[] {-1 , table_9[i][0] , table_9[i][1] , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
        return line;
    }

    }

