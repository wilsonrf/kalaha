package com.wilsonfranca.kalaha.games;

import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;

@Document
public class Game {

	@Id
	private ObjectId id;

	private String firstPlayer;

	private String secondPlayer;

	@CreatedDate
	private Instant createdDate;

	@LastModifiedDate
	private Instant lastUpdated;

	private boolean playing;

	private boolean finished;

	private String winner;

	private String round;

	private Pit firstPlayerPits;

	private Pit secondPlayerPits;

	@Version
	private Long version;

	protected static class Pit {

		public Pit() {
			this.first = 6;
			this.second = 6;
			this.third = 6;
			this.fourth = 6;
			this.fifth = 6;
			this.sixth = 6;
			this.kalaha = 0;
		}

		@Field("1")
		private int first;

		@Field("2")
		private int second;

		@Field("3")
		private int third;

		@Field("4")
		private int fourth;

		@Field("5")
		private int fifth;

		@Field("6")
		private int sixth;

		private int kalaha;

		public int getFirst() {
			return first;
		}

		public void setFirst(int first) {
			this.first = first;
		}

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		public int getThird() {
			return third;
		}

		public void setThird(int third) {
			this.third = third;
		}

		public int getFourth() {
			return fourth;
		}

		public void setFourth(int fourth) {
			this.fourth = fourth;
		}

		public int getFifth() {
			return fifth;
		}

		public void setFifth(int fifth) {
			this.fifth = fifth;
		}

		public int getSixth() {
			return sixth;
		}

		public void setSixth(int sixth) {
			this.sixth = sixth;
		}

		public int getKalaha() {
			return kalaha;
		}

		public void setKalaha(int kalaha) {
			this.kalaha = kalaha;
		}

	}

	public Game() {}

	public Game(String player) {
		this.firstPlayer = player;
	}


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(String firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public String getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(String secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Instant lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}


	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public Pit getFirstPlayerPits() {
		return firstPlayerPits;
	}

	public void setFirstPlayerPits(Pit firstPlayerPits) {
		this.firstPlayerPits = firstPlayerPits;
	}

	public Pit getSecondPlayerPits() {
		return secondPlayerPits;
	}

	public void setSecondPlayerPits(Pit secondPlayerPits) {
		this.secondPlayerPits = secondPlayerPits;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void start() {
		this.playing = true;
		this.round = this.firstPlayer;
		this.firstPlayerPits = new Pit();
		this.secondPlayerPits = new Pit();
	}

	public int getPitValueById(int pit, String player) {

		Pit playerPit = getPitByPlayer(player);

		switch (pit) {

		case 1:
			return playerPit.getFirst();
		case 2:
			return playerPit.getSecond();
		case 3:
			return playerPit.getThird();
		case 4:
			return playerPit.getFourth();
		case 5:
			return playerPit.getFifth();
		case 6:
			return playerPit.getSixth();
		default:
			return 0;
		}
	}

	private Pit getPitByPlayer(String player) {

		Assert.hasLength(player,"Player can't be null");

		if (getFirstPlayer().equalsIgnoreCase(player)) {
			return getFirstPlayerPits();
		} else if (getSecondPlayer().equalsIgnoreCase(player)) {
			return getSecondPlayerPits();
		}
		throw new IllegalArgumentException("Player not exits");
	}

	public Game movePit(final int pit, String player) {

		int pitValue = getPitValueById(pit, player);

		int lastPitValue = pitValue;
		
		int lastPit = pit;

		for (int i = pit + 1; i <= 7 && pitValue > 0; i++) {
			lastPitValue = addToPit(player, i);
			pitValue--;
			lastPit++;
		}

		if (pitValue > 0) {
			//fill the oponent pit, not insert on oponent pit
			for (int i = 1; i < 7 && pitValue > 0; i++) {
				String oponent = getOponent(player);
				lastPitValue = addToPit(oponent, i);
				pitValue--;
				lastPit++;
			}

		}

		// verify if last pit was kalaha
		if (lastPit % 7 != 0) {
			// nextTurn is for the same player
			nextTurn(player);
		}
		
		// verify if the lastPitValue was 1, which means capture opposite
		if (lastPitValue == 1) {
			int opposite = lastPit;
			if (lastPit > 7) {
				opposite = lastPit - 7;
			}
			
			int oppositePitValue = getPitValueById(opposite, player);
			// add to playerKalaha
			addToPit(player, 7, oppositePitValue);
		}

		// empty the pit
		emptyPit(player, pit);
		
		// check if game is finished
		if (hasWinner()) {
			end();
		}

		return this;
	}

	private void end() {
		
		// sum all firstPlayerPit
		int totalFirstPlayer = getTotalStones(getFirstPlayer());
		// sum all secondPlayerPit
		int totalSecondPlayer = getTotalStones(getSecondPlayer());
		
		if( totalFirstPlayer > totalSecondPlayer) {
			this.winner = getFirstPlayer();
		}
		
		this.finished = true;
		
	}

	private int getTotalStones(String player) {
		
		int total = 0;
		
		Pit pit = getPitByPlayer(player);
		
		total = pit.first + pit.second + pit.third + pit.fourth + pit.fifth + pit.sixth + pit.kalaha;
		
		return total;
	}

	private void nextTurn(String player) {

		if (this.getFirstPlayer().equalsIgnoreCase(player)) {
			this.setRound(this.getSecondPlayer());
		} else {
			this.setRound(this.getFirstPlayer());
		}

	}

	private void emptyPit(String player, int pit) {
		Pit playerPit = getPitByPlayer(player);

		switch (pit) {

		case 1:
			playerPit.first = 0;
			break;
		case 2:
			playerPit.second = 0;
			break;
		case 3:
			playerPit.third = 0;
			break;
		case 4:
			playerPit.fourth = 0;
			break;
		case 5:
			playerPit.fifth = 0;
			break;
		case 6:
			playerPit.sixth = 0;
			break;
		default:
			throw new IllegalArgumentException();
		}

	}

	private String getOponent(String player) {
		if (this.getFirstPlayer().equalsIgnoreCase(player)) {
			return this.getSecondPlayer();
		}
		return this.getFirstPlayer();
	}

	public int addToPit(String player, int pit, int value) {
		Pit playerPit = getPitByPlayer(player);

		switch (pit) {

		case 1:
			return playerPit.first+=value;
		case 2:
			return playerPit.second+=value;
		case 3:
			return playerPit.third+=value;
		case 4:
			return playerPit.fourth+=value;
		case 5:
			return playerPit.fifth+=value;
		case 6:
			return playerPit.sixth+=value;
		default:
			return playerPit.kalaha+=value;
		}
	}
	
	public int addToPit(String player, int pit) {
		return addToPit(player, pit , 1);
	}
	
	private boolean hasWinner() {
		return isPlayerPitEmpty(getFirstPlayer()) || isPlayerPitEmpty(getSecondPlayer());
	}
	
	private boolean isPlayerPitEmpty(String player) {
		
		Pit pit = getPitByPlayer(player);
		
		if (pit.getFirst() == 0 && pit.getSecond() == 0 
				&& pit.getThird() == 0 && pit.getFourth() == 0
				&& pit.getFifth() == 0 && pit.getSixth() == 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.append("id", id);
		builder.append("firstPlayer", firstPlayer);
		builder.append("secondPlayer", secondPlayer);
		builder.append("createdDate", createdDate);
		builder.append("lastUpdated", lastUpdated);
		builder.append("playing", playing);
		builder.append("finished", finished);
		builder.append("winner", winner);
		builder.append("round", round);
		builder.append("firstPlayerPits", firstPlayerPits);
		builder.append("secondPlayerPits", secondPlayerPits);
		builder.append("version", version);
		return builder.build();
	}
	
	

}
