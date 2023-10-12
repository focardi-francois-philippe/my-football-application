package com.example.teamsservice.Utils;

import com.example.teamsservice.Model.Equipe;
import com.example.teamsservice.Model.Joueur;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

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


    public static String generateFullName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
}
