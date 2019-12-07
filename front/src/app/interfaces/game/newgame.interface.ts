/**
 * NewGame.
 */
export interface NewGame {
	playersNames: string[],
	duration: Duration,
	themes: Theme[],
	effects: Effect[],
	connectionMode: ConnectionMode
}