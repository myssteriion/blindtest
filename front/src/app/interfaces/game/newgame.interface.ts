/**
 * NewGame.
 */
export interface NewGame {
	playersNames: string[],
	duration: Duration,
	themes: Theme[],
	onlineMode: boolean
}