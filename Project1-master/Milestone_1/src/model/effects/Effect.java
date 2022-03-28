package model.effects;
public class Effect {

private String name;
private int duration;
public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
}
private EffectType type;

public Effect(String name, int duration, EffectType type) {
	this.name=name;
	this.duration=duration;
	this.type=type;
}
}
