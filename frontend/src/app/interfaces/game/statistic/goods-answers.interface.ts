import { GoodAnswer } from "../../common/good-answer.enum";
/**
 * Goods Answers.
 */
export interface GoodsAnswers {
	[GoodAnswer.AUTHOR]?: number,
	[GoodAnswer.TITLE]?: number,
	[GoodAnswer.BOTH]?: number,
}
