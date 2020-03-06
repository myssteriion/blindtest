/**
 * NewGame.
 */
export interface NewGame {
	profilesId: number[],
	duration: Duration,
	sameProbability: boolean,
	themes: Theme[],
	effects: Effect[],
	connectionMode: ConnectionMode
}