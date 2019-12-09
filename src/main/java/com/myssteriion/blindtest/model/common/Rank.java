package com.myssteriion.blindtest.model.common;

/**
 * The Rank enum.
 */
public enum Rank {

	/**
	 * Twelfth rank.
	 */
	TWELFTH(12, null),
	/**
	 * Eleventh rank.
	 */
	ELEVENTH(11, TWELFTH ),
	/**
	 * Tenth rank.
	 */
	TENTH(10, ELEVENTH ),
	/**
	 * Ninth rank.
	 */
	NINTH(9, TENTH),
	/**
	 * Eighth rank.
	 */
	EIGHTH(8, NINTH),
	/**
	 * Seventh rank.
	 */
	SEVENTH(7, EIGHTH),
	/**
	 * Sixth rank.
	 */
	SIXTH(6, SEVENTH),
	/**
	 * Fifth rank.
	 */
	FIFTH(5, SIXTH),
	/**
	 * Fourth rank.
	 */
	FOURTH(4, FIFTH),
	/**
	 * Third rank.
	 */
	THIRD(3, FOURTH),
	/**
	 * Second rank.
	 */
	SECOND(2, THIRD),
	/**
	 * First rank.
	 */
	FIRST(1, SECOND);



	private int num;

	private Rank next;

	Rank(int num, Rank next) {
		this.num = num;
		this.next = next;
	}

	/**
	 * Gets num.
	 *
	 * @return The num.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Gets next.
	 *
	 * @return the next
	 */
	public Rank getNext() {
		return next;
	}

}
