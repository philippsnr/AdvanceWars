package Janne;

public class faktor {
    public static char KIA[][]={{'d','d','e','d','e','x','x','e'},{'c','d','c','b','c','x','x','e'},{'b','b','d','b','c','x','x','e'},{'a','a','b','c','c','x','x','x'},{'a','a','d','c','d','c','c','a'},{'x','x','x','x','x','c','a','a'},{'a','a','a','a','a','x','x','x'},{'b','b','c','c','d','x','x','c'}};

    public static double getFaktor(int unit1, int unit2) {
        if (KIA[unit1][unit2]=='a'){
            return 0.5;
        }
        else if (KIA[unit1][unit2]=='b'){
            return 0.4;
        }
        else if (KIA[unit1][unit2]=='c'){
            return 0.22;
        }
        else if (KIA[unit1][unit2]=='d'){
            return 0.17;
        }
        else if (KIA[unit1][unit2]=='e'){
            return 0.024;
        }
        else return 0;
    }
}
