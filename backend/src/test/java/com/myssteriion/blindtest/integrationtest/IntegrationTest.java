package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.model.round.impl.Choice;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.blindtest.model.round.impl.Friendship;
import com.myssteriion.blindtest.model.round.impl.Lucky;
import com.myssteriion.blindtest.model.round.impl.Recovery;
import com.myssteriion.blindtest.model.round.impl.Thief;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.entity.ProfileStatEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegrationTest extends AbstractIntegrationTest {
    
    @Test
    public void testShortGame() throws NotFoundException, ConflictException {
        
        Set<Integer> profilesId = PROFILES_LIST.stream().map(ProfileEntity::getId).collect(Collectors.toSet());
        Duration duration = Duration.SHORT;
        
        NewGame newGame = new NewGame().setProfilesId(profilesId).setDuration(duration)
                .setThemes(Arrays.asList(Theme.ANNEES_80, Theme.ANNEES_90, Theme.ANNEES_2000))
                .setEffects(Arrays.asList(Effect.NONE, Effect.SPEED));
        Game game = gameService.newGame(newGame);
        
        
        
        /********** CLASSIC Round **********/
        Assert.assertEquals( Classic.class, game.getRound().getClass() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        AbstractRound round = game.getRound();
        int nbMusicPlayed = 0;
        int nbPointWon = round.getNbPointWon();
        int foundName1 = 0;
        int foundName2 = 0;
        int foundName3 = 0;
        int scoreName1 = 0;
        int scoreName2 = 0;
        int scoreName3 = 0;
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 4; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
                    
                    winners.add(name);
                    
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
            }
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        Assert.assertEquals( scoreName1, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( scoreName2, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( scoreName3, game.getPlayers().get(2).getScore() );
        
        
        
        /********** CHOICE Round **********/
        Assert.assertEquals( Choice.class, game.getRound().getClass() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        round = game.getRound();
        nbPointWon = round.getNbPointWon();
        int nbPointBonus = ((Choice) round).getNbPointBonus();
        int nbPointMalus = ((Choice) round).getNbPointMalus();
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 4; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
                    
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
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        Assert.assertEquals( scoreName1, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( scoreName2, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( scoreName3, game.getPlayers().get(2).getScore() );
        
        
        
        /********** LUCKY Round **********/
        Assert.assertEquals( Lucky.class, game.getRound().getClass() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        round = game.getRound();
        nbPointWon = round.getNbPointWon();
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 10; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
                    winners.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
            }
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        
        
        /********** FRIENDSHIP Round **********/
        Assert.assertEquals( Friendship.class, game.getRound().getClass() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        round = game.getRound();
        nbPointWon = round.getNbPointWon();
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 10; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
                    winners.add(name);
                    switch (j) {
                        case 1: scoreName1 += nbPointWon; foundName1++; break;
                        case 2: scoreName2 += nbPointWon; foundName2++; break;
                        case 3: scoreName3 += nbPointWon; foundName3++; break;
                        default:
                    }
                }
            }
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        
        
        /********** THIEF Round **********/
        Assert.assertEquals( Thief.class, game.getRound().getClass() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        round = game.getRound();
        nbPointWon = round.getNbPointWon();
        int nbPointLoose = ((Thief) round).getNbPointLoose();
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 20; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
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
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        Assert.assertTrue( game.getPlayers().get(0).getScore() >= scoreName1 );
        Assert.assertTrue( game.getPlayers().get(1).getScore() >= scoreName2 );
        Assert.assertTrue( game.getPlayers().get(2).getScore() >= scoreName3 );
        
        
        
        /********** RECOVERY Round **********/
        Assert.assertEquals( Recovery.class, game.getRound().getClass() );
        Assert.assertEquals( nbMusicPlayed, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        
        round = game.getRound();
        nbPointWon = round.getNbPointWon();
        
        for (int i = 0; i < round.getNbMusics(); i++) {
            
            List<String> winners = new ArrayList<>();
            List<String> losers = new ArrayList<>();
            
            for (int j = 1; j < 20; j++) {
                
                String name = "name" + j;
                if ( Constant.RANDOM.nextBoolean() ) {
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
            
            MusicResult musicResult = new MusicResult().setGameId(game.getId()).setMusic(MUSICS_LIST.get(0)).setAuthorWinners(winners).setLosers(losers);
            game = gameService.apply(musicResult);
            nbMusicPlayed++;
        }
        
        Assert.assertTrue( game.getPlayers().get(0).getScore() >= scoreName1 );
        Assert.assertTrue( game.getPlayers().get(1).getScore() >= scoreName2 );
        Assert.assertTrue( game.getPlayers().get(2).getScore() >= scoreName3 );
        
        ProfileStatEntity profileStat = profileService.find(PROFILES_LIST.get(0)).getProfileStat();
        Assert.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get( game.getDuration() ) );
        Assert.assertEquals( Integer.valueOf(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( Integer.valueOf(foundName1), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName1);
        
        profileStat = profileService.find(PROFILES_LIST.get(1)).getProfileStat();
        Assert.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( Integer.valueOf(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( Integer.valueOf(foundName2), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName2);
        
        profileStat = profileService.find(PROFILES_LIST.get(2)).getProfileStat();
        Assert.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( Integer.valueOf(game.getNbMusicsPlayed()), profileStat.getListenedMusics().get(Theme.ANNEES_80) );
        Assert.assertEquals( Integer.valueOf(foundName3), profileStat.getFoundMusics().get(Theme.ANNEES_80).get(GoodAnswer.AUTHOR) );
        Assert.assertTrue(profileStat.getBestScores().get(duration) >= scoreName3);
        
        MusicEntity music = musicService.find( MUSICS_LIST.get(0) );
        Assert.assertEquals( nbMusicPlayed, music.getPlayed() );
        
        Assert.assertTrue( game.isFinished() );
    }
    
}
