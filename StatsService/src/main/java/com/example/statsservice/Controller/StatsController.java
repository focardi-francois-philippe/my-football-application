package com.example.statsservice.Controller;

import com.example.statsservice.Model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController
@Api(value = "Stats service ",description = "Gestions des stats des joueurs et equipe")
public class StatsController {
    public final  String BASE_URL_MATCHS_SERVICE = "http://desktop-1g4113b.mshome.net:3033/";
    public final  String BASE_URL_TEAMS_SERVICE = "http://desktop-1g4113b.mshome.net:3031/";

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "get stats for team in Season", response = StatsEquipe.class, tags = "getStatsTeams")
    @GetMapping("/team-stats/{teamId}")
    public StatsEquipe statsEquipeByIdTeams(@PathVariable int teamId)
    {

        List<Match> matchList = allMatchByTeamsId(teamId);


        if (matchList.size() == 0)
        {
            return null;
        }
        Equipe e;
        if (matchList.get(0).equipeDom.id == teamId)
            e = matchList.get(0).equipeDom;
        else
            e = matchList.get(0).equipeExt;
        int nbrButMarque = countNbrButMarque(matchList,teamId);
        int nbrButEncaisse = countNbrButEncaisse(matchList,teamId);

        return  new StatsEquipe(e,nbrButMarque,nbrButEncaisse);
    }

    @ApiOperation(value = "get stats for one player in Season", response = StatsJoueurs.class, tags = "getStatsPlayers")
    @GetMapping("/player-stats/{playerId}")
    public StatsJoueurs playerStatsById(@PathVariable int playerId)
    {
        String url = BASE_URL_TEAMS_SERVICE+"players/"+playerId +"/teams";
        Equipe response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Equipe>() {}
        ).getBody();

        if (response == null)
            return null;
        List<Match> matchList =  allMatchByTeamsId(response.id);

        if (matchList.size() == 0)
            return null;


        Joueur j = playerById(playerId);
        if (j == null)
            return null;

        return nbrButMarqueJoueurs(matchList,j,response.id);
    }

    public Joueur playerById(int playerId)
    {
        String url = BASE_URL_TEAMS_SERVICE+"teams/players/"+playerId;
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();

        return response;
    }

    public List<Match> allMatchByTeamsId(int id)
    {
        String url = BASE_URL_MATCHS_SERVICE+"matches/teams/"+id+"/all";
        List<Match> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Match>>() {}
        ).getBody();

        return response;
    }

    public StatsJoueurs nbrButMarqueJoueurs(List<Match> matchList,Joueur joueur,int idEquipe)
    {

        int nbrButMarqueDom = 0;
        int nbrButMarqueExt = 0;
        for (Match m: matchList) {
            if (m.equipeDom.id == idEquipe)
            {
                for (Joueur j: m.lstButeurDom) {
                    if (j.id == joueur.id)
                        nbrButMarqueDom++;

                }
            }
            else
            {
                for (Joueur j: m.lstButeurExt) {
                    if (j.id == joueur.id)
                        nbrButMarqueExt++;
                }
            }
        }

        StatsJoueurs statsJoueurs = new StatsJoueurs(joueur,nbrButMarqueExt,nbrButMarqueDom);
        return statsJoueurs;
    }

    public int countNbrButMarque(List<Match> matchList,int idEquipe)
    {
        int nbrButMarque = 0;
        for (Match m :matchList) {
            if (m.equipeDom.id == idEquipe)
                nbrButMarque+= m.scoreDom;
            else
                nbrButMarque+=m.scoreExt;
        }
        return nbrButMarque;
    }
    public int countNbrButEncaisse(List<Match> matchList,int idEquipe)
    {
        int nbrButEncaisse = 0;
        for (Match m :matchList) {
            if (m.equipeDom.id == idEquipe)
                nbrButEncaisse+= m.scoreExt;
            else
                nbrButEncaisse+=m.scoreDom;
        }
        return nbrButEncaisse;
    }


    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
