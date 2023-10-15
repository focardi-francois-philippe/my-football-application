package com.example.matchservice.Controller;

import com.example.matchservice.Model.Equipe;
import com.example.matchservice.Model.Joueur;
import com.example.matchservice.Model.Match;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@Api(value = "Match service",description = "Service permettant de créeer des matchs et gerer les scores")
public class MatchController {


    @Autowired
    Environment environment;
    public final  String BASE_URL_TEAMS_SERVICE = "http://desktop-1g4113b.mshome.net:3031/";
    @Autowired
    public RestTemplate restTemplate;

    public List<Match> listMatch;

    public MatchController() {
        listMatch = new ArrayList<>() ;
    }

    @ApiOperation(value = "get all matchs in base", response = Iterable.class, tags = "getAllMatchs")
    @GetMapping("/matches")
    public List<Match> allMatch()
    {

        return  listMatch;
    }
    @ApiOperation(value = "get one matchs in base by id", response = Match.class, tags = "getOneMatch")
    @GetMapping("/matches/{id}")
    public Match matchById(@PathVariable int id)
    {
        Match match = getMatchById(id);
        return  match;
    }


    @HystrixCommand(fallbackMethod = "fallBackcreateMatch")
    @PostMapping("/matches/teams/{idEquipeDom}/{idEquipeExt}")
    @ApiOperation(value = "Add matches enter two teams", response = ResponseEntity.class, tags = "add match")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Equipe domicile ou exterieur pas definie ") })
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
    public ResponseEntity<?> fallBackcreateMatch(int equipeDom,int equipeExt)
    {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le service teams n'est pas disponible");
    }
    @ApiOperation(value = "Increment score domicile teams in one match", response = Match.class, tags = "scoreDom")
    @PutMapping("matches/{id}/teams/dom")
    public Match incrementScoreDom(@PathVariable int id)
    {
        Match m = getMatchById(id);

        if (m == null)
            return null;

        m.scoreDom +=1;

        addButeur(m.lstButeurDom,choiceRandomButeur(m.equipeDom));
        return m;
    }
    @ApiOperation(value = "Increment score exterieur teams in one match", response = Match.class, tags = "scoreExt")
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
    @ApiOperation(value = "Delete one match", response = Match.class, tags = "delete match")
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
    @ApiOperation(value = "Get all matchs for one teams", response = Iterable.class, tags = "scoreDom")
    @GetMapping("/matches/teams/{id}/all")
    public List<Match> allMatchesTeams(@PathVariable int id)
    {
        List<Match> listMatchTeams = new ArrayList<>();

        for (Match m:listMatch ) {
            if (m.equipeDom.id == id || m.equipeExt.id == id)
            {
                listMatchTeams.add(m);
            }

        }
        return listMatchTeams;
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

    @GetMapping("/backend")
    public String backend() {
        System.out.println("Inside MyRestController::backend...");

        String serverPort = environment.getProperty("local.server.port");

        System.out.println("Port : " + serverPort);

        return "Hello form Backend!!! " + " Host : localhost " + " :: Port : " + serverPort;
    }

    @Bean
    public  RestTemplate restTemplate()
    {
        return new RestTemplate();
    }


}
