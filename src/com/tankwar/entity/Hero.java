package com.tankwar.entity;

/**
 * hero
 * @author Administrator
 *
 */
public class Hero extends Tank implements TankAdapter{

	public Hero(String[] mapArray) {
		super(mapArray);	
	}

	@Override
	public void fire() {
		super.fire();
	}

	@Override
	public void go_left() {
		super.go_left();
	}

	@Override
	public void go_right() {
		super.go_right();
	}

	@Override
	public void go_up() {
		super.go_up();
	}

	@Override
	public void go_down() {
		super.go_down();
	}

}
