package com.example.playersservice.Controller;

import com.example.playersservice.Model.Joueur;
import com.example.playersservice.Utils.Joueurs;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "Players service ",description = "Gestions des joueurs")
public class PlayersController {


    public final  String BASE_URL_TEAMS_SERVICE = "http://desktop-1g4113b.mshome.net:3031/";
    @Autowired
    RestTemplate restTemplate;
    ArrayList<Joueur> listJoueurs = new ArrayList<>();

    @ApiOperation(value = "Get list of players  in the System ", response = Iterable.class, tags = "getAllPlayers")
    @GetMapping(value = "/players/create/{nombreJoueurs}")
    public List<Joueur> AllJoueurs(@PathVariable(required = false, name = "nombreJoueurs") int  nombreJoueurs)
    {

        return Joueurs.createJoueurs(nombreJoueurs);
    }

    @ApiOperation(value = "add player in the System ", response = ResponseEntity.class, tags = "addPlayer")
    @HystrixCommand(fallbackMethod = "serviceTeamsNotFound")
    @PostMapping(value = "/players/teams/{idEquipe}")
    public  ResponseEntity<?> addJoueur(@PathVariable int idEquipe)
    {
        Joueur j = Joueurs.createJoueurs(1).get(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Joueur> entity = new HttpEntity<>(j,headers);
        String url = BASE_URL_TEAMS_SERVICE+idEquipe+"/addPlayers";
        restTemplate.exchange(url, HttpMethod.POST, entity,Joueur.class);
        return ResponseEntity.ok(j);
    }

    public ResponseEntity<?> serviceTeamsNotFound(int idEquipe)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le service teams n'est pas disponible");
    }

    @ApiOperation(value = "get player in the System ", response = ResponseEntity.class, tags = "getOnePlayer")

    @HystrixCommand(fallbackMethod = "serviceTeamsNotFound")
    @GetMapping(value = "/players/{id}")
    public ResponseEntity<?> joueurById(@PathVariable int id)
    {
        String url = BASE_URL_TEAMS_SERVICE+"teams/players/"+id;
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "update name of player in the System ", response = ResponseEntity.class, tags = "UpdateName")

    @HystrixCommand(fallbackMethod = "serviceTeamsNotFound")
    @PutMapping("/players/{id}")
    public Joueur updateName(@PathVariable int id,@RequestBody String name)
    {
        String url = BASE_URL_TEAMS_SERVICE+"teams/players/"+id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // ou le type de contenu que vous souhaitez définir
        HttpEntity<String> requestEntity = new HttpEntity<>(name, headers);
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();
        return  response;
    }

    @ApiOperation(value = "delete player in the System ", response = ResponseEntity.class, tags = "DeletePlayer")

    @HystrixCommand(fallbackMethod = "serviceTeamsNotFound")
    @DeleteMapping("/players/{id}")
    public Joueur joueur(@PathVariable int id)
    {
        String url = BASE_URL_TEAMS_SERVICE+"teams/players/"+id;
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();

        return response;
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return  new RestTemplate();
    }
}
