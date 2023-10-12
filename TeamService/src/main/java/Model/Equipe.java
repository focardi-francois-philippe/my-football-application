package Model;


public class Equipe {
    public String nom;

    public Integer id;


    public static int identifiantVal = 1;

    public Equipe(String nom) {
        this.nom = nom;
        this.id = identifiantVal;
        identifiantVal++;
    }
}
