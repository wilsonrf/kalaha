package com.wilsonfranca.kalaha.games;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository  extends MongoRepository<Game, ObjectId>, PagingAndSortingRepository<Game, ObjectId> {

	Optional<Game> findByFirstPlayerAndFinishedIsFalseAndPlayingIsFalse(String firstPlayer);

}
