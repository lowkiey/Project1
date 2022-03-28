package engine;

import java.util.*;

import model.ablities.Ability;
import model.world.Champion;

public class Game {
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbility;

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return board;
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbility() {
		return availableAbility;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBOARDHEIGHT() {
		return BOARDHEIGHT;
	}

	public static int getBOARDWIDTH() {
		return BOARDWIDTH;
	}

	private PriorityQueue turnOrder;
	private static int BOARDHEIGHT;
	private static int BOARDWIDTH;

	public Game(Player first, Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		Object[][] board = new Object[5][5];
	}

	private void placeChampion() {

	}

	private void placeCovers() {

	}

	public static void loadAbilities(String filePath) {

	}

	public static void loadChampions(String filePath) {

	}
}
