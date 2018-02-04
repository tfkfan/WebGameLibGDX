package com.webgame.game.world.skills.cooldown;

import java.util.ArrayList;

import com.webgame.game.world.skills.Skill;

public class SkillContainer extends ArrayList<Skill<?>> {
	private Long cooldown_start;
	private Long cooldown_time;

	private Boolean isAvailable;

	private Skill<?> skill;

	private static final long serialVersionUID = 1L;

	public SkillContainer() {
		init();
	}

	public SkillContainer(Skill<?> skill) {
		setSkill(skill);
		init();
	}

	public void animate(float dt) {
		if(this.isEmpty())
			return;

		for (int i = 0; i < this.size(); i++){
			Skill<?> currSkill = this.get(i);
			if (currSkill.getSkillState().isActive())
				currSkill.animateSkill(dt);
			else
				remove(currSkill);
		}
	}

	protected void init() {
		isAvailable = true;
		cooldown_start = 0L;
		cooldown_time = 0L;
	}

	public Skill<?> getSkill() {
		return skill;
	}

	public void setSkill(Skill<?> skill) {
		this.skill = skill;
	}
	
	public Skill<?> createSkill(){
		return this.skill.clone();
	}

	public Long getCooldown_start() {
		return cooldown_start;
	}

	public void setCooldown_start(Long cooldown_start) {
		this.cooldown_start = cooldown_start;
	}

	public Long getCooldown_time() {
		return cooldown_time;
	}

	public void setCooldown_time(Long cooldown_time) {
		this.cooldown_time = cooldown_time;
	}

	public Boolean isAvailable() {
		Long now = System.currentTimeMillis();
		if (now - cooldown_start < cooldown_time)
			isAvailable = false;
		else{
			cooldown_start = now;
			isAvailable = true;
		}
		return isAvailable;
	}
}
