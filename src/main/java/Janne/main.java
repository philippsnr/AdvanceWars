package Janne;

import com.example.advancedwars.Troop;

import java.util.Random;


public class main {
    private static final int[] Bellcurve ={1,1,1,2,2,2,2,2,2,3,3,3,4,4,5};
    public static void kampf(Troop attekingTroop,Troop defendingTroop){


    double Faktor=faktor.getFaktor(attekingTroop.getIdentification(), defendingTroop.getIdentification());
    System.out.println(Faktor);
        Random rand = new Random();
    int beforFaktor=0;
       for(int i=1; i<= attekingTroop.getHealth(); i++){
           int randomNumber = rand.nextInt(3) + 1;
            beforFaktor+=randomNumber;

       }
       System.out.println(beforFaktor);
       double afterFaktor=beforFaktor*Faktor;
       System.out.println(afterFaktor);
       int damage= Math.toIntExact(Math.round(afterFaktor));
       System.out.println(attekingTroop+" hat "+damage+" schaden gemacht");
       defendingTroop.recieveDamage(damage);


         Faktor=faktor.getFaktor(defendingTroop.getIdentification(),attekingTroop.getIdentification());
        System.out.println(Faktor);

        beforFaktor=0;
        for(int i=1; i<= defendingTroop.getHealth(); i++){
            int randomNumber = rand.nextInt(3) + 1;
            beforFaktor+=randomNumber;

        }
        System.out.println(beforFaktor);
        afterFaktor=beforFaktor*Faktor;
        System.out.println(afterFaktor);
        damage= Math.toIntExact(Math.round(afterFaktor));
        System.out.println(defendingTroop+" hat "+damage+" schaden gemacht");
        attekingTroop.recieveDamage(damage);
}
    public static void main(String[] args) {
        Troop inf1=new Infantry(1,1,1);
        Troop mech1 = new Mech(1,1,1);
        Troop Tank1 = new Tank(1,1,1);
        Troop arty1 = new Artillery(1,1,1);
        Troop anti1 = new AntiAir(1,1,1);

        Random rand = new Random();
        double beforFaktor=0;
        for (int i=1;i<100;i++){
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor+=randomNumber;
        }
        System.out.println(beforFaktor/100);

    }




}
