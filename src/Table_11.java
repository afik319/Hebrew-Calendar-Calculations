/**
 * Write a description of class table_11 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Table_11
{
    static final int ZODIAC_COMPLEMENT = 11;
    static final int DEGREES_COMPLEMENT = 30;
    static final double table_11[][] = {
        {2.0/5 , 1.0/3 , 1.0/ 5 , 0.0/ 1 , 1.0/5 , 1.0/3},
        {2.0/5 , 1.0/3 , 1.0/ 5 , 1.0/24 , 1.0/5 , 1.0/3},
        {2.0/5 , 1.0/4 , 1.0/12 , 1.0/12 , 1.0/4 , 2.0/5},
        {1.0/3 , 1.0/5 , 1.0/24 , 1.0/ 5 , 1.0/3 , 2.0/5},
        {1.0/3 , 1.0/5 , 0.0/ 1 , 1.0/ 5 , 1.0/3 , 2.0/5}};
        
    public static double get_mult_num(int zodiac , int degrees) { //נדרשת השלמה ושיטה נוספת
        int fit_zodiac;
        int fit_line;
        if(0 <= zodiac && zodiac <= 5) 
            fit_zodiac = zodiac;
        else if (6 <= zodiac && zodiac <= 11)
            fit_zodiac = zodiac - 6;
        else {
            System.out.println("Error-----------------------------------------Error");
            fit_zodiac = -1;}
        if(1 <= degrees && degrees <= 5)
            fit_line = 0;
        else if(6 <= degrees && degrees <= 10)
            fit_line = 1;
        else if(11 <= degrees && degrees <= 20)
            fit_line = 2;
        else if(21 <= degrees && degrees <= 25)
            fit_line = 3;
        else fit_line = 4;
        return table_11[fit_line][fit_zodiac];
    }

}

