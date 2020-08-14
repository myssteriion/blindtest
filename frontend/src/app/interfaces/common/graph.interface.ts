/**
 * Simple graph statistics.
 */
export interface SimpleGraphStatisticsInterface {
	name: string,
	value: number
}

/**
 * Complex graph statistics.
 */
export interface ComplexGraphStatisticsInterface {
	name: string,
	series: SimpleGraphStatisticsInterface[]
}