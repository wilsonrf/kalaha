package com.wilsonfranca.kalaha.games;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	
	GamesService gamesService;
	
	GamesRepository gamesRepository;
	
	@Before
	public void setup() {
		gamesRepository = mock(GamesRepository.class);
		gamesService = new GamesService(gamesRepository);
	}
	
	@Test
	public void testSuccessfullyCreateANewGame() {
		
		when(gamesRepository.findByFirstPlayerAndFinishedIsFalseAndPlayingIsFalse("user1@gmail.com")).thenReturn(Optional.empty());
		when(gamesRepository.save(any(Game.class))).thenReturn(new Game("user1@gmail.com"));
		
		Game game = gamesService.create("user1@gmail.com");
		
		assertThat(game.getFirstPlayer()).isEqualTo("user1@gmail.com");
		assertThat(game.getSecondPlayer()).isNull();
		assertThat(game.isFinished()).isFalse();
		assertThat(game.isPlaying()).isFalse();
			
	}
	
	@Test
	public void testSucessFullyJoinAGameAsASecondPlayer() {
		
		String gameId = "5b1d3cdff8b8e42ad951b358";
		String firstPlayer = "user1@gmail.com";
		String secondPlayer = "user2@gmail.com";
		
		Game currentGame = new Game(firstPlayer);
		
		when(gamesRepository.findById(eq(new ObjectId(gameId)))).thenReturn(Optional.of(currentGame));
		when(gamesRepository.save(eq(currentGame))).thenReturn(currentGame);
		
		gamesService.joinGame(secondPlayer, gameId);
		
		assertThat(currentGame.getSecondPlayer()).isEqualTo(secondPlayer);
		assertThat(currentGame.getFirstPlayer()).isEqualTo(firstPlayer);
		assertThat(currentGame.isPlaying()).isTrue();
		assertThat(currentGame.isFinished()).isFalse();
		
		verify(gamesRepository, times(1)).findById(any());
		verify(gamesRepository, times(1)).save(any());
	}

}
