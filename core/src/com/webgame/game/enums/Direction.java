package com.webgame.game.enums;

public enum Direction implements State{
	UP(0), DOWN(1), RIGHT(2), LEFT(3), UPRIGHT(4), RIGHTDOWN(5), LEFTDOWN(6), UPLEFT(7);

	private int dirIndex;

	Direction(int dirIndex){
		this.dirIndex = dirIndex;
	}

	public int getDirIndex(){
		return dirIndex;
	}
}
