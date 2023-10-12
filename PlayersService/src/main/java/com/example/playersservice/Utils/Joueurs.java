package com.example.playersservice.Utils;



import com.example.playersservice.Model.Joueur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joueurs {
    private static final String[] FIRST_NAMES = {
            "Lucas", "Emma", "Louis", "Léa", "Hugo", "Chloé", "Jules", "Manon", "Léo", "Camille"
    };

    private static final String[] LAST_NAMES = {
            "Martin", "Bernard", "Dubois", "Thomas", "Robert", "Petit", "Richard", "Durand", "Leroy", "Moreau"
    };

    private static final Random random = new Random();

    public  static List<Joueur> createJoueurs(int nbrJoueurs)
    {
        List<Joueur> lstJoueurs = new ArrayList<>();
        for (int i =0 ; i<nbrJoueurs;i++)
        {
            lstJoueurs.add(new Joueur(generateFullName()));
        }
        return lstJoueurs;

    }
    public static String generateFullName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
}
