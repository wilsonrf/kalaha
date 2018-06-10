package com.wilsonfranca.kalaha.games;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wilsonfranca.kalaha.auth.SignupFormData;

@Controller
@RequestMapping
public class GamesController {

    GamesService gamesService;

	private static final Logger log = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @GetMapping(value = "games.html")
    public String games(Model model, @PageableDefault(size = 3) Pageable pageable) {
        Page<Game> games = gamesService.findAll(pageable);
        model.addAttribute("games", games);
        return "game/games";
    }

    @GetMapping(value = "board.html")
    public String board(Model model, @AuthenticationPrincipal Principal principal) {
    		Game game = gamesService.getGame(principal.getName());
    		model.addAttribute("game", game);
        return "game/board";
    }

    @GetMapping(value = "new-game.html")
    public String newGame(@RequestParam MultiValueMap<String, String> parameters, @AuthenticationPrincipal Principal principal) {
    		
    		String player = parameters.getFirst("player");
    		
    		if (principal.getName().equalsIgnoreCase(player)) {
    			gamesService.create(player);
    		} else {
    			// show error, when try to create a game on behalf of other user
    			return "games/error.html";
    		}
    		
        return "redirect:/board.html";
    }

    @GetMapping(value = "join-game.html")
    public String joinGame(Model model, @RequestParam MultiValueMap<String, String> parameters, @AuthenticationPrincipal Principal principal) {
    		
    		String player = parameters.getFirst("player");
    		String game = parameters.getFirst("game");
    		
    		log.info("Joining the game: [{}] as [{}]", game, player);
    		
    		if (principal.getName().equalsIgnoreCase(player)) {
    			Game currentGame = gamesService.joinGame(player, game);
    			model.addAttribute("game", currentGame);
    		}
    		
        return "game/board";
    }
    
    @PostMapping(value = "move-pit.html")
    public @ResponseBody String movePit(@Valid MovePitData data, BindingResult bindingResult, Model model, @AuthenticationPrincipal Principal principal) {
    		
    		if(bindingResult.hasErrors()) {
    			log.warn("Data with error: [{}]", data);
    			return "error";
    		} else {
    			log.info("Start processing move: [{}]", data);
    			// pit_xx
    			int pit = Integer.valueOf(data.getPit().substring(5));
    			
    			Game game = gamesService.movePit(data.getGame(), data.getUser(), pit);
    			
    			return game.toString();
    		}
    }
}
