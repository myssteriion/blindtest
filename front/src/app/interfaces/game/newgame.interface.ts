/**
 * NewGame.
 */
export interface NewGame {
	playersNames: string[],
	duration: Duration,
	themes: Theme[],
	connectionMode: ConnectionMode
}