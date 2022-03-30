package engine;

import java.awt.Point;
import java.lang.Math;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Disarm;
import model.effects.Dodge;
import model.effects.Effect;
import model.effects.Embrace;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

public class Game {
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private final static int BOARDHEIGHT = 5;
	private final static int BOARDWIDTH = 5;

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

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public Game(Player first, Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		this.turnOrder = new PriorityQueue(6);
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		placeCovers();
		placeChampions();
	}

	private void placeChampions() {
		for (int i = 0; i < firstPlayer.getTeam().size(); i++) {
			board[0][i + 1] = firstPlayer.getTeam().get(i);
			firstPlayer.getTeam().get(i).setLocation(new Point(0, i + 1));
		}
		for (int i = 0; i < secondPlayer.getTeam().size(); i++) {
			board[4][i + 1] = secondPlayer.getTeam().get(i);
			secondPlayer.getTeam().get(i).setLocation(new Point(4, i + 1));
		}
	}

	private void placeCovers() {
		int c = 0;
		while (c < 5) {
			int x = (int) (Math.random() * 3) + 1;
			int y = (int) (Math.random() * 5);
			System.out.println(x + " " + y);
			if (board[x][y] == null && x != 0 && x != 4) {
				board[x][y] = new Cover(x, y);
				c++;
			}
		}
	}

	public static void loadAbilities(String filePath) throws IOException {
		availableAbilities = getAvailableAbilities();
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine = br.readLine();
		while (currentLine != null) {
			String[] jdm = currentLine.split(",");
			String name = jdm[1];
			int manaCost = Integer.parseInt(jdm[2]);
			int castRange = Integer.parseInt(jdm[3]);
			int baseCooldown = Integer.parseInt(jdm[4]);
			AreaOfEffect area = AreaOfEffect.valueOf(jdm[5]);
			int requiredActionPoints = Integer.parseInt(jdm[6]);
			switch (jdm[0]) {
			case "DMG":
				DamagingAbility d = new DamagingAbility(name, manaCost, baseCooldown, castRange, area,
						requiredActionPoints, Integer.parseInt(jdm[7]));
				availableAbilities.add(d);
				break;
			case "CC":
				CrowdControlAbility c = new CrowdControlAbility(name, manaCost, baseCooldown, castRange, area,
						requiredActionPoints, createEffect(jdm[7], Integer.parseInt(jdm[8])));
				availableAbilities.add(c);
				break;
			case "HEL":
				HealingAbility h = new HealingAbility(name, manaCost, baseCooldown, castRange, area,
						requiredActionPoints, Integer.parseInt(jdm[7]));
				availableAbilities.add(h);
				break;
			}
			currentLine = br.readLine();
		}
	}

	private static Effect createEffect(String name, int duration) {
		if (name.equals("Disarm"))
			return new Disarm(duration);
		if (name.equals("Dodge"))
			return new Dodge(duration);
		if (name.equals("Dodge"))
			return new Dodge(duration);
		if (name.equals("Embrace"))
			return new Embrace(duration);
		if (name.equals("Root"))
			return new Root(duration);
		if (name.equals("Shield"))
			return new Shield(duration);
		if (name.equals("Shock"))
			return new Shock(duration);
		if (name.equals("Silence"))
			return new Silence(duration);
		if (name.equals("SpeedUp"))
			return new SpeedUp(duration);
		if (name.equals("Stun"))
			return new Stun(duration);
		return null;
	}

	public static void loadChampions(String filePath) throws IOException {
		availableChampions = getAvailableChampions();
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		String currentLine = br.readLine();
		wh ile (currentLine != null) {
			String[] ae = currentLine.split(",");
			String name = ae[1];
			int maxHP = Integer.parseInt(ae[2]);
			int mana = Integer.parseInt(ae[3]);
			int maxActions = Integer.parseInt(ae[4]);
			int speed = Integer.parseInt(ae[5]);
			int attackRange = Integer.parseInt(ae[6]);
			int attackDamage = Integer.parseInt(ae[7]);
			String ability1Name = ae[8];
			String ability2Name = ae[9];
			String ability3Name = ae[10];
			switch (ae[0]) {
			case "A":
				AntiHero a = new AntiHero(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
				availableChampions.add(a);
				Ability a1 = getAbilityFromAvailable(ability1Name);
				if (a1 != null)
					a.getAbilities().add(a1);
				Ability a2 = getAbilityFromAvailable(ability1Name);
				if (a2 != null)
					a.getAbilities().add(a2);
				Ability a3 = getAbilityFromAvailable(ability1Name);
				if (a3 != null)
					a.getAbilities().add(a3);
				availableChampions.add(a);
			case "H":
				Hero h = new Hero(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
				availableChampions.add(h);
				Ability h1 = getAbilityFromAvailable(ability1Name);
				if (h1 != null)
					h.getAbilities().add(h1);
				Ability h2 = getAbilityFromAvailable(ability1Name);
				if (h2 != null)
					h.getAbilities().add(h2);
				Ability h3 = getAbilityFromAvailable(ability1Name);
				if (h3 != null)
					h.getAbilities().add(h3);
				availableChampions.add(h);
			case "V":
				Villain v = new Villain(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
				availableChampions.add(v);
				Ability v1 = getAbilityFromAvailable(ability1Name);
				if (v1 != null)
					v.getAbilities().add(v1);
				Ability v2 = getAbilityFromAvailable(ability1Name);
				if (v2 != null)
					v.getAbilities().add(v2);
				Ability v3 = getAbilityFromAvailable(ability1Name);
				if (v3 != null)
					v.getAbilities().add(v3);
				availableChampions.add(v);
			}
			currentLine = br.readLine();
		}
	}

	private static Ability getAbilityFromAvailable(String abilityName) {
		for (Ability ability : availableAbilities) {
			if (ability.getName().equals(abilityName))
				return ability;
		}
		return null;
	}
}
