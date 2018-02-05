package com.webgame.game.enums;

public enum Direction implements State{
	UP(0), UPRIGHT(1), RIGHT(2) ,  RIGHTDOWN(3),  DOWN(4), LEFTDOWN(5), LEFT(6), UPLEFT(7);

	private int dirIndex;

	Direction(int dirIndex){
		this.dirIndex = dirIndex;
	}

	public int getDirIndex(){
		return dirIndex;
	}
}
