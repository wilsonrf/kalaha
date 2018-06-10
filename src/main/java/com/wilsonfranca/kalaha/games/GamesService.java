package com.wilsonfranca.kalaha.games;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GamesService {

    GamesRepository gamesRepository;
    
	private static final Logger log = LoggerFactory.getLogger(GamesService.class);

    @Autowired
    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public Page<Game> findAll(Pageable pageable) {
        return gamesRepository.findAll(pageable);
    }
    
    public Game create(String player) {
    		
    		Optional<Game> currentGame = gamesRepository.findByFirstPlayerAndFinishedIsFalseAndPlayingIsFalse(player);
    		Game game = currentGame.orElse(new Game(player));
    		game = gamesRepository.save(game);
    		return game;
    }

	public Game getGame(String player) {
		Game currentGame = create(player);
		return currentGame;
	}

	public Game joinGame(String player, String gameId) {
		
		if (ObjectId.isValid(gameId)) {
			
			Optional<Game> optional = gamesRepository.findById(new ObjectId(gameId));
			Game game = optional
					.filter(g -> !g.isFinished())
					.orElseThrow(() -> new InvalidGameExeption("The game is already finished"));
			
			// check if you're not the first player
			if (!player.equalsIgnoreCase(game.getFirstPlayer())) {
				//check if there is second player, and it's you
				if ("".equalsIgnoreCase(game.getSecondPlayer()) || game.getSecondPlayer() == null) {
					// there is no second player, so, join as second player
					log.info("Joining the game [{}] as a second player: [{}]", gameId, player);
					game.setSecondPlayer(player);
					game.start();
				} else if (!player.equalsIgnoreCase(game.getSecondPlayer())) {
					// there is a second player, and it's not you
					throw new InvalidGameExeption("You can't join this game.");
				}
				
				gamesRepository.save(game);
			} else {
				// you're the first player
				log.info("You are the first player.");
			}
			
			return game;
		} else {
			throw new InvalidGameExeption("This game is not valid");
		}
	}
	
	public Game movePit(String gameId, String player, int pit) {
		
		Game game = null;
		
		if (ObjectId.isValid(gameId)) {
			
			Optional<Game> optional = gamesRepository.findById(new ObjectId(gameId));
			game = optional
					.filter(g -> !g.isFinished())
					.orElseThrow(() -> new InvalidGameExeption("The game is already finished"));
			
			int pitValue = game.getPitValueById(pit, player);
			
			//check if pit value is 0, which means empty pit
			if(pitValue == 0) {
				throw new IvalidMoveException("Pit is empty");
			} else {
				game.movePit(pit, player);
				gamesRepository.save(game);
			}
			
		}
		
		return game;
		
	}
}
