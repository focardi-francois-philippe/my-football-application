package com.example.matchservice.Controller;

import com.example.matchservice.Model.Equipe;
import com.example.matchservice.Model.Joueur;
import com.example.matchservice.Model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class MatchController {
    public final  String BASE_URL_TEAMS_SERVICE = "http://desktop-1g4113b.mshome.net:3031/";
    @Autowired
    public RestTemplate restTemplate;

    public List<Match> listMatch;

    public MatchController() {
        listMatch = new ArrayList<>() ;
    }
    @GetMapping("/matches")
    public List<Match> allMatch()
    {

        return  listMatch;
    }
    @GetMapping("/matches/{id}")
    public Match matchById(@PathVariable int id)
    {
        Match match = getMatchById(id);
        return  match;
    }
    @PostMapping("/matches/teams/{idEquipeDom}/{idEquipeExt}")
    public ResponseEntity<?> createMatch(@PathVariable int idEquipeDom,@PathVariable int idEquipeExt)
    {
        Equipe equipeDom = getEquipeById(idEquipeDom);
        Equipe equipeExt = getEquipeById(idEquipeExt);
        if (equipeDom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'équipe à domicile n'a pas été trouvée.");
        }
        if (equipeExt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'équipe à l'exterieur n'a pas été trouvée.");
        }


        Match match = new Match(equipeDom,equipeExt);
        listMatch.add(match);
        return ResponseEntity.ok(match);

    }
    @PutMapping("matches/{id}/teams/dom")
    public Match incrementScoreDom(@PathVariable int id)
    {
        Match m = getMatchById(id);

        if (m == null)
            return new Match();

        m.scoreDom +=1;

        addButeur(m.lstButeurDom,choiceRandomButeur(m.equipeDom));
        return m;
    }
    @PutMapping("matches/{id}/teams/ext")
    public Match incrementScoreExt(@PathVariable int id)
    {
        Match m = getMatchById(id);

        if (m == null)
            return new Match();

        m.scoreExt +=1;
        addButeur(m.lstButeurExt,choiceRandomButeur(m.equipeExt));
        return m;
    }

    @DeleteMapping("/matches/{id}")
    public Match SupmatchById(@PathVariable int id)
    {
        Match m = getMatchById(id);
        if (m== null)
        {
            return null;
        }

        listMatch.remove(m);
        return m;
    }

    public Match getMatchById(int id)
    {
        for (Match m : listMatch) {
            if (m.idMatch == id)
                return m;
        }
        return null;
    }
    public Equipe getEquipeById(int idEquipe)
    {
        String url = BASE_URL_TEAMS_SERVICE+"/teams/"+idEquipe;
        Equipe response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Equipe>() {}
        ).getBody();


        return response;
    }

    public void addButeur(List<Joueur> listButeur,Joueur buteur)
    {
        listButeur.add(buteur);
    }
    public Joueur choiceRandomButeur(Equipe e)
    {
        return e.joueurs.get(new Random().nextInt(e.joueurs.size()));
    }

    @Bean
    public  RestTemplate restTemplate()
    {
        return new RestTemplate();
    }


}
