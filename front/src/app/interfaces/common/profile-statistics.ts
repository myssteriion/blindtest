/**
 * Profile statistics.
 */
export interface ProfileStatistics {
    id: number,
    profileId: number,
    playedGames: number,
    wonGames: Rank,
    listenedMusics: Theme,
    foundMusics: Theme,
    bestScores: Duration
}