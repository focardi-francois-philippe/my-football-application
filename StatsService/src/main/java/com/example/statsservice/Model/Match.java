package com.example.statsservice.Model;



import java.util.ArrayList;
import java.util.List;

public class Match {

    public int idMatch;
    public Equipe equipeDom;
    public  Equipe equipeExt;

    public  int scoreDom;
    public  int scoreExt;

    public List<Joueur> lstButeurDom;

    public List<Joueur> lstButeurExt;


    public static int aiIdMatch = 1;

    public Match() {
        equipeDom = null;
        equipeExt = null;
        scoreDom = 0;
        scoreExt = 0;
        lstButeurDom = new ArrayList<>();
        lstButeurExt = new ArrayList<>();
        idMatch = aiIdMatch;
        aiIdMatch++;
    }

    public Match(Equipe equipeDom, Equipe equipeExt) {
        this();
        this.equipeDom = equipeDom;
        this.equipeExt = equipeExt;
    }
}

