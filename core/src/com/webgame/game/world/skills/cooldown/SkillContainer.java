package com.webgame.game.world.skills.cooldown;

import java.util.ArrayList;

import com.webgame.game.entities.skill.SkillOrig;

public class SkillContainer extends ArrayList<SkillOrig<?>> {
	private Long cooldown_start;
	private Long cooldown_time;

	private Boolean isAvailable;

	private SkillOrig<?> skillOrig;

	private static final long serialVersionUID = 1L;

	public SkillContainer() {
		init();
	}

	public SkillContainer(SkillOrig<?> skillOrig) {
		setSkillOrig(skillOrig);
		init();
	}

	public void animate(float dt) {
		if(this.isEmpty())
			return;

		for (int i = 0; i < this.size(); i++){
			SkillOrig<?> currSkillOrig = this.get(i);
			if (currSkillOrig.getSkillState().isActive())
				currSkillOrig.animateSkill(dt);
			else
				remove(currSkillOrig);
		}
	}

	protected void init() {
		isAvailable = true;
		cooldown_start = 0L;
		cooldown_time = 0L;
	}

	public SkillOrig<?> getSkillOrig() {
		return skillOrig;
	}

	public void setSkillOrig(SkillOrig<?> skillOrig) {
		this.skillOrig = skillOrig;
	}
	
	public SkillOrig<?> createSkill(){
		return this.skillOrig.clone();
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
