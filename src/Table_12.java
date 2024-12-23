/**
 * Write a description of class table_12 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_12
{
    static final double table_12[][] = {
        {  0 ,  0} ,
        { 40 , 40} ,
        { 45 , 44} ,
        { 50 , 48} ,
        { 55 , 52} ,
        { 60 , 56} ,
        { 65 , 60} ,
        {-99 , 65} ,
        { 70 , 70} ,
        { 65 , 65} ,
        { 60 , 60} ,
        { 55 , 55} ,
        { 50 , 50} ,
        { 45 , 45}}; 

    public static double get_mult_num( boolean isLeap , int month_num) { //נדרשת בדיקה ושיטה נוספת
        if(!isLeap){
            if(month_num <= 6) 
                return table_12[month_num][0];
            return table_12[month_num + 1][0];
        }
        return table_12[month_num][1];
    }
}
