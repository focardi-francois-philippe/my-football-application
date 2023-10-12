package Model;

public class Joueur {
    public int id;
    public String nom;

    public int equipe_id;

    public static int ai_id = 1;

    public Joueur(String nom,int equipe_id) {
        this.nom = nom;
        id = ai_id;
        this.equipe_id = equipe_id;
        ai_id++;
    }
}
