package Model;


import java.util.ArrayList;

public class Equipe {
    public String nom;

    public Integer id;

    public ArrayList<Joueur> joueurs;

    public static int identifiantVal = 1;

    public Equipe(String nom) {
        this.nom = nom;
        this.id = identifiantVal;
        this.joueurs = new ArrayList<>();
        identifiantVal++;
    }
}
