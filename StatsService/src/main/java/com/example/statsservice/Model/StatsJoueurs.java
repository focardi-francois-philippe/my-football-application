package com.example.statsservice.Model;

public class StatsJoueurs {

    public  Joueur joueur;

    public int nbrButMarque;

    public int nombreButMarqueExt;

    public int nombreButMarqueDom;

    public StatsJoueurs() {

        this.nombreButMarqueExt = 0;
        this.nombreButMarqueDom = 0;
        this.nbrButMarque = nombreButMarqueExt+nombreButMarqueDom;
    }

    public StatsJoueurs(Joueur j, int nombreButMarqueExt, int nombreButMarqueDom) {
        this.joueur  = j;
        this.nbrButMarque = nombreButMarqueExt+nombreButMarqueDom;
        this.nombreButMarqueExt = nombreButMarqueExt;
        this.nombreButMarqueDom = nombreButMarqueDom;
    }
}
