package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.LuckyContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import com.myssteriion.blindtest.tools.Tool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegrationTest extends AbstractIntegrationTest {

    @Before
    public void before() throws ConflictException, NotFoundException {

        musicService = Mockito.spy( new MusicService(musicDAO, spotifyService) );
        Mockito.doNothing().when(musicService).refresh();

        gameService = new GameService(musicService, profileService, profileStatService, spotifyService);

        clearDataBase();
        insertData();
    }


    @Test
    public void testShortGame() throws NotFoundException, SpotifyException, ConflictException {

        Set<String> playersNames = PROFILES_LIST.stream().map(ProfileDTO::getName).collect(Collectors.toSet());
        Duration duration = Duration.SHORT;

        NewGame newGame = new NewGame(playersNames, duration, Arrays.asList(Theme.ANNEES_80, Theme.ANNEES_90, Theme.ANNEES_2000), Arrays.asList(Effect.NONE, Effect.SPEED), ConnectionMode.OFFLINE);
        Game game = gameService.newGame(newGame);



        /********** CLASSIC Round **********/
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );

        AbstractRoundContent roundContent = game.getRoundContent();
        int nbMusicPlayed = 0;
        int nbPointWon = roundContent.getNbPointWon();
        int foundName1 = 0;
        int foundName2 = 0;
        int foundName3 = 0;
        int scoreName1 = 0;
        int scoreName2 = 0;
        int scoreName3 = 0;

        for (int i = 0; i < roundContent.getNbMusics(); i++) {

            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();

            for (int j = 1; j < 4; j++) {

                String name = "name" + j;
                if ( Tool.RANDOM.nextBoolean() ) {

                    winners.add(name);

                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
            }

            MusicResult musicResult = new MusicResult(game.getId(), MUSICS_LIST.get(0), winners, null, losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }

        Assert.assertEquals( scoreName1, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( scoreName2, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( scoreName3, game.getPlayers().get(2).getScore() );



        /********** CHOICE Round **********/
        Assert.assertEquals( Round.CHOICE, game.getRound() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );

        roundContent = game.getRoundContent();
        nbPointWon = roundContent.getNbPointWon();
        int nbPointBonus = ((ChoiceContent) roundContent).getNbPointBonus();
        int nbPointMalus = ((ChoiceContent) roundContent).getNbPointMalus();

        for (int i = 0; i < roundContent.getNbMusics(); i++) {

            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();

            for (int j = 1; j < 4; j++) {

                String name = "name" + j;
                if ( Tool.RANDOM.nextBoolean() ) {

                    winners.add(name);

                    switch (j) {
                        case 1:
                            scoreName1 += nbPointWon;
                            foundName1++;
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName1 += nbPointBonus;
                            break;

                        case 2:
                            scoreName2 += nbPointWon;
                            foundName2++;
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName2 += nbPointBonus;
                            break;

                        case 3:
                            scoreName3 += nbPointWon;
                            foundName3++;
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName3 += nbPointBonus;
                            break;

                        default:
                    }
                }
                else {
                    switch (j) {
                        case 1:
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName1 += nbPointMalus;
                            break;

                        case 2:
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName2 += nbPointMalus;
                            break;

                        case 3:
                            if ( game.getPlayers().get(j-1).isTurnToChoose() )
                                scoreName3 += nbPointMalus;
                            break;

                        default:
                    }
                }
            }

            MusicResult musicResult = new MusicResult(game.getId(), MUSICS_LIST.get(0), winners, null, losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }

        Assert.assertEquals( scoreName1, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( scoreName2, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( scoreName3, game.getPlayers().get(2).getScore() );



        /********** LUCKY Round **********/
        Assert.assertEquals( Round.LUCKY, game.getRound() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );

        roundContent = game.getRoundContent();
        nbPointWon = roundContent.getNbPointWon();

        for (int i = 0; i < roundContent.getNbMusics(); i++) {

            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();

            for (int j = 1; j < 10; j++) {

                String name = "name" + j;
                if ( Tool.RANDOM.nextBoolean() ) {
                    winners.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
            }

            MusicResult musicResult = new MusicResult(game.getId(), MUSICS_LIST.get(0), winners, null, losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }



        /********** THIEF Round **********/
        Assert.assertEquals( Round.THIEF, game.getRound() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );

        roundContent = game.getRoundContent();
        nbPointWon = roundContent.getNbPointWon();
        int nbPointLoose = ((ThiefContent) roundContent).getNbPointLoose();

        for (int i = 0; i < roundContent.getNbMusics(); i++) {

            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();

            for (int j = 1; j < 20; j++) {

                String name = "name" + j;
                if ( Tool.RANDOM.nextBoolean() ) {
                    winners.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
                else {
                    losers.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointLoose; break;
                        case 2: scoreName2 += nbPointLoose; break;
                        case 3: scoreName3 += nbPointLoose; break;
                        default:
                    }
                }
            }

            MusicResult musicResult = new MusicResult(game.getId(), MUSICS_LIST.get(0), winners, null, losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }

        Assert.assertTrue( game.getPlayers().get(0).getScore() >= scoreName1 );
        Assert.assertTrue( game.getPlayers().get(1).getScore() >= scoreName2 );
        Assert.assertTrue( game.getPlayers().get(2).getScore() >= scoreName3 );



        /********** RECOVERY Round **********/
        Assert.assertEquals( Round.RECOVERY, game.getRound() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );

        roundContent = game.getRoundContent();
        nbPointWon = roundContent.getNbPointWon();

        for (int i = 0; i < roundContent.getNbMusics(); i++) {

            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();

            for (int j = 1; j < 20; j++) {

                String name = "name" + j;
                if ( Tool.RANDOM.nextBoolean() ) {
                    winners.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
                else {
                    losers.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointLoose; break;
                        case 2: scoreName2 += nbPointLoose; break;
                        case 3: scoreName3 += nbPointLoose; break;
                        default:
                    }
                }
            }

            MusicResult musicResult = new MusicResult(game.getId(), MUSICS_LIST.get(0), winners, null, losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }

        Assert.assertTrue( game.getPlayers().get(0).getScore() >= scoreName1 );
        Assert.assertTrue( game.getPlayers().get(1).getScore() >= scoreName2 );
        Assert.assertTrue( game.getPlayers().get(2).getScore() >= scoreName3 );

        ProfileStatDTO profileStat = profileStatService.findByProfile( profileService.find(PROFILES_LIST.get(0)) );
        Assert.assertEquals( new Integer(1), profileStat.getPlayedGames().get( game.getDuration() ) );
        Assert.assertEquals( new Integer(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( new Integer(foundName1), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName1);

        profileStat = profileStatService.findByProfile( profileService.find(PROFILES_LIST.get(1)) );
        Assert.assertEquals( new Integer(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( new Integer(foundName2), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName2);

        profileStat = profileStatService.findByProfile( profileService.find(PROFILES_LIST.get(2)) );
        Assert.assertEquals( new Integer(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( new Integer(foundName3), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName3);

        MusicDTO music = musicService.find( MUSICS_LIST.get(0) );
        Assert.assertEquals( nbMusicPlayed, music.getPlayed() );

        Assert.assertTrue( game.isFinished() );
    }

}
