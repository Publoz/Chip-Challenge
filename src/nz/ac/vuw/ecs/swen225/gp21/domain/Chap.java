package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.awt.image.BufferedImage;

public class Chap implements Actor{
	
	private Position chapPos;
	
	Chap(Position start){
		chapPos = start;
	}

	@Override
	public void move(Game game) {
		throw new UnsupportedOperationException("Chap is a special actor that doesn't use move");
		
	}
	
	@Override
	public String toString() {
		return "Chap";
	}

	@Override
	public Position getPos() {
		return chapPos;
	}

	@Override
	public void setPos(Position pos) {
		chapPos = pos;
		
	}

	@Override
	public BufferedImage getImage() {
		throw new UnsupportedOperationException("Chap is not an external actor");
	}

}
