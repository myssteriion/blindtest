package com.myssteriion.blindtest.model.common;

/**
 * The Rank enum.
 */
public enum Rank {

	UN_RANKED(null),
	TWELFTH(null),
	ELEVENTH(TWELFTH),
	TENTH(ELEVENTH),
	NINTH(TENTH),
	EIGHTH(NINTH),
	SEVENTH(EIGHTH),
	SIXTH(SEVENTH),
	FIFTH(SIXTH),
	FOURTH(FIFTH),
	THIRD(FOURTH),
	SECOND(THIRD),
	FIRST(SECOND);



	private Rank next;

	Rank(Rank next) {
		this.next = next;
	}

	public Rank getNext() {
		return next;
	}

}
