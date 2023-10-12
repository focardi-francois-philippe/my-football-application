package Utils;

import Model.Equipe;
import Model.Joueur;

import java.util.Random;

public class Joueurs {
    private static final String[] FIRST_NAMES = {
            "Lucas", "Emma", "Louis", "Léa", "Hugo", "Chloé", "Jules", "Manon", "Léo", "Camille"
    };

    private static final String[] LAST_NAMES = {
            "Martin", "Bernard", "Dubois", "Thomas", "Robert", "Petit", "Richard", "Durand", "Leroy", "Moreau"
    };

    private static final Random random = new Random();

    public  static void createJoueurs(Equipe e,int nbrJoueurs)
    {
        for (int i =0 ; i<nbrJoueurs;i++)
        {
            e.joueurs.add(new Joueur(generateFullName(),e.id));
        }

    }
    public static String generateFullName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
}
