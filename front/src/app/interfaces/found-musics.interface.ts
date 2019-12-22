/**
 * Found musics.
 */
import {GoodAnswer} from "./good-answer.interface";

export interface FoundMusics {
    [Theme.ANNEES_60]?: GoodAnswer,
    [Theme.ANNEES_70]?: GoodAnswer,
    [Theme.ANNEES_80]?: GoodAnswer,
    [Theme.ANNEES_90]?: GoodAnswer,
    [Theme.ANNEES_2000]?: GoodAnswer,
    [Theme.ANNEES_2010]?: GoodAnswer,
    [Theme.DISNEY]?: GoodAnswer,
    [Theme.SERIES_CINEMAS]?: GoodAnswer,
    [Theme.JEUX]?: GoodAnswer,
    [Theme.CLASSIQUES]?: GoodAnswer
}
