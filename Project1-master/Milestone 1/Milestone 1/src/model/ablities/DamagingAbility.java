package model.ablities;

public class DamagingAbility extends Ability {
	private int damageAmount;

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}

	public DamagingAbility(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required,
			int damageAmount) {
		super(name, cost, baseCoolDown, castRange, area, required);
		this.damageAmount = damageAmount;
	}
}
