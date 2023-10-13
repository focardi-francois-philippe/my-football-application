package com.example.teamsservice.Model;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    @ApiModelProperty(notes = "Name of the team",name="name",required=true)
    public String nom;
    @ApiModelProperty(notes = "Id of the team",name="id",required=true)
    public Integer id;
    @ApiModelProperty(notes = "List of players",name="joueurs",required=true)
    public List<Joueur> joueurs;

    public static int identifiantVal = 1;

    public Equipe(String nom) {
        this.nom = nom;
        this.id = identifiantVal;
        this.joueurs = new ArrayList<>();
        identifiantVal++;
    }
}