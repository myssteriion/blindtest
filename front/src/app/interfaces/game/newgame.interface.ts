/**
 * NewGame.
 */
export interface NewGame {
	playersNames: string[],
	duration: Duration,
	sameProbability: boolean,
	themes: Theme[],
	effects: Effect[],
	connectionMode: ConnectionMode
}