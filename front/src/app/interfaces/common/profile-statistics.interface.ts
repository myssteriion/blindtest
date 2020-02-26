/**
 * Profile statistics.
 */
export interface ProfileStatistics {
    id: number,
    profileId: number,
    playedGames: number,
    wonGames: number,
    listenedMusics: Theme,
    foundMusics: Theme,
    bestScores: Duration
}