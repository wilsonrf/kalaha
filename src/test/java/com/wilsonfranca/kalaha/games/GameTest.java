package com.wilsonfranca.kalaha.games;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wilsonfranca.kalaha.games.Game;

@RunWith(JUnit4.class)
public class GameTest {

	@Test
	public void testMove6StonesFromPit1() {
		
		Game game = new Game("user1@gmail.com");
		game.setSecondPlayer("user2@gmail.com");
		game.start();
		game.movePit(1, "user1@gmail.com");
		
		assertThat(game.getFirstPlayerPits().getFirst()).isEqualTo(0);
		assertThat(game.getFirstPlayerPits().getSecond()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getThird()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getFourth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getFifth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getSixth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getKalaha()).isEqualTo(1);
		
		assertThat(game.getSecondPlayerPits().getFirst()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getSecond()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getThird()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFourth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFifth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getSixth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getKalaha()).isEqualTo(0);
		
		assertThat(game.getRound()).isEqualTo("user1@gmail.com");
		
	}
	
	@Test
	public void testFirstPlayerMoveFromPit1AndSecondFromPit1() {
		
		Game game = new Game("user1@gmail.com");
		game.setSecondPlayer("user2@gmail.com");
		game.start();
		game.movePit(1, "user1@gmail.com");
		
		assertThat(game.getFirstPlayerPits().getFirst()).isEqualTo(0);
		assertThat(game.getFirstPlayerPits().getSecond()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getThird()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getFourth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getFifth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getSixth()).isEqualTo(7);
		assertThat(game.getFirstPlayerPits().getKalaha()).isEqualTo(1);
		
		assertThat(game.getSecondPlayerPits().getFirst()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getSecond()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getThird()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFourth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFifth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getSixth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getKalaha()).isEqualTo(0);
		
		assertThat(game.getRound()).isEqualTo("user1@gmail.com");
		
		game.movePit(2, "user1@gmail.com");
		
		assertThat(game.getFirstPlayerPits().getFirst()).isEqualTo(0);
		assertThat(game.getFirstPlayerPits().getSecond()).isEqualTo(0);
		assertThat(game.getFirstPlayerPits().getThird()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getFourth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getFifth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getSixth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getKalaha()).isEqualTo(2);
		
		assertThat(game.getSecondPlayerPits().getFirst()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getSecond()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getThird()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFourth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getFifth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getSixth()).isEqualTo(6);
		assertThat(game.getSecondPlayerPits().getKalaha()).isEqualTo(0);
		
		assertThat(game.getRound()).isEqualTo("user2@gmail.com");
		
	}
	
	
	@Test
	public void testFirstPlayerMoveFromPit1AndSecondFromPit1AndFirstFromPit2() {
		
		Game game = new Game("user1@gmail.com");
		game.setSecondPlayer("user2@gmail.com");
		game.start();
		game.movePit(1, "user1@gmail.com");
		game.movePit(2, "user1@gmail.com");
		game.movePit(1, "user2@gmail.com");
		
		assertThat(game.getFirstPlayerPits().getFirst()).isEqualTo(1);
		assertThat(game.getFirstPlayerPits().getSecond()).isEqualTo(0);
		assertThat(game.getFirstPlayerPits().getThird()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getFourth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getFifth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getSixth()).isEqualTo(8);
		assertThat(game.getFirstPlayerPits().getKalaha()).isEqualTo(2);
		
		assertThat(game.getSecondPlayerPits().getFirst()).isEqualTo(0);
		assertThat(game.getSecondPlayerPits().getSecond()).isEqualTo(8);
		assertThat(game.getSecondPlayerPits().getThird()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getFourth()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getFifth()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getSixth()).isEqualTo(7);
		assertThat(game.getSecondPlayerPits().getKalaha()).isEqualTo(8);
	
		assertThat(game.getRound()).isEqualTo("user1@gmail.com");
		
	}
	
	@Test
	public void testFirstPlayerIsWinner() {
		Game game = new Game("user1@gmail.com");
		game.setSecondPlayer("user2@gmail.com");
		game.start();
		game.getFirstPlayerPits().setFirst(0);
		game.getFirstPlayerPits().setSecond(0);
		game.getFirstPlayerPits().setThird(0);
		game.getFirstPlayerPits().setFourth(0);
		game.getFirstPlayerPits().setFifth(0);
		game.getFirstPlayerPits().setSixth(1);
		game.getFirstPlayerPits().setKalaha(59);
		
		game.getSecondPlayerPits().setFirst(1);
		game.getSecondPlayerPits().setSecond(0);
		game.getSecondPlayerPits().setThird(2);
		game.getSecondPlayerPits().setFourth(0);
		game.getSecondPlayerPits().setFifth(1);
		game.getSecondPlayerPits().setSixth(0);
		game.getSecondPlayerPits().setKalaha(10);
		
		game.movePit(6, "user1@gmail.com");
		
		assertThat(game.getWinner()).isEqualTo(game.getFirstPlayer());
		assertThat(game.isFinished()).isTrue();
		
	}
}
