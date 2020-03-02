import {GoodsAnswers} from './goods-answers.interface';

/**
 * Found musics.
 */
export interface FoundMusics {
	[Theme.ANNEES_60]?: GoodsAnswers,
	[Theme.ANNEES_70]?: GoodsAnswers,
	[Theme.ANNEES_80]?: GoodsAnswers,
	[Theme.ANNEES_90]?: GoodsAnswers,
	[Theme.ANNEES_2000]?: GoodsAnswers,
	[Theme.ANNEES_2010]?: GoodsAnswers,
	[Theme.DISNEY]?: GoodsAnswers,
	[Theme.SERIES_CINEMAS]?: GoodsAnswers
}
