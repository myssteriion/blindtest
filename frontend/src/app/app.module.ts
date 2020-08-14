import {BrowserModule} from "@angular/platform-browser";
import {ErrorHandler, NgModule} from "@angular/core";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ToastrModule} from "ngx-toastr";
import {AppRoutingModule} from "./app-routing.module";
import {Ng2OdometerModule} from "ng2-odometer";
import {CountdownModule} from "ngx-countdown";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {NgHttpLoaderModule} from "ng-http-loader";
import {MatRadioModule} from "@angular/material/radio";
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTooltipModule} from '@angular/material/tooltip';
import {NavbarModule, WavesModule, ButtonsModule, IconsModule} from 'angular-bootstrap-md'

import {AppComponent} from "./app.component";
import {NgxChartsModule} from "@swimlane/ngx-charts"

import {ProfileCardComponent} from "./profile/profile-card/profile-card.component";
import {ProfileCardEmptyComponent} from "./profile/profile-card-empty/profile-card-empty.component";
import {ProfileEditModalComponent} from "./profile/profile-edit-modal/profile-edit-modal.component";
import {ProfilePageComponent} from "./profile/profile-page/profile-page.component";
import {ProfilePageModalComponent} from "./profile/profile-page-modal/profile-page-modal.component";
import {ProfileViewComponent} from "./profile/profile-view/profile-view.component"
import {ConfirmModalComponent} from "./common/modal/confirm/confirm-modal.component";
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";
import {GameNewViewComponent} from "./game/game-new-view/game-new-view.component";
import {HeaderComponent} from "./common/header/header.component";
import {HomeViewComponent} from "./home-view/home-view.component";
import {SignatureViewComponent} from "./common/signature-view/signature-view.component";
import {GameCurrentViewComponent} from "./game/game-current-view/game-current-view.component";
import {GameResumeViewComponent} from "./game/game-resume-view/game-resume-view.component";
import {PlayerCardComponent} from "./player/player-card/player-card.component";
import {ThemeEffectComponent} from "./game/factoring-part/theme-effect/theme-effect.component";
import {CustomCountdownComponent} from "./game/factoring-part/custom-countdown/custom-countdown.component";
import {MusicResultModalComponent} from "./game/factoring-part/music-result-modal/music-result-modal.component";
import {RoundInfoModalComponent} from "./game/factoring-part/round-info-modal/round-info-modal.component";
import {ChoiceThemeModalComponent} from "./game/factoring-part/choice-theme-modal/choice-theme-modal.component";
import {GameEndViewComponent} from "./game/game-end-view/game-end-view.component";
import {ErrorAlertModalComponent} from "./common/error-alert/error-alert-modal.component";
import {ProfilesStatisticsViewComponent} from "./statistics/profiles-statistics-view/profiles-statistics-view.component";
import {GlobalPercentagesComponent} from "./statistics/graphs/comparison/global-percentages/global-percentages.component";
import {ThemePercentagesComponent} from "./statistics/graphs/single-user/theme-percentages/theme-percentages.component";
import {ThemeListeningComponent} from "./statistics/graphs/single-user/theme-listening/theme-listening.component";
import {ThemeComparisonViewComponent} from "./statistics/graphs/theme-comparison/theme-comparison-view.component";
import {UserThemeComparisonViewComponent} from "./statistics/graphs/user-comparison/theme-comparison/user-theme-comparison-view.component";
import {UserThemeComparisonQuestionDetailComponent} from "./statistics/graphs/user-comparison/theme-comparison/user-theme-comparison-question-detail/user-theme-comparison-question-detail.component";
import {UserThemeComparisonQuestionNumberComponent} from "./statistics/graphs/user-comparison/theme-comparison/user-theme-comparison-question-number/user-theme-comparison-question-number.component";
import {UserRankComparisonComponent} from "./statistics/graphs/user-comparison/user-rank-comparison/user-rank-comparison.component";
import {ThemeComparisonQuestionDetailComponent} from "./statistics/graphs/theme-comparison/theme-comparison-question-detail/theme-comparison-question-detail.component";
import {ThemeComparisonQuestionNumberComponent} from "./statistics/graphs/theme-comparison/theme-comparison-question-number/theme-comparison-question-number.component";
import {ScoreByGameTypeComponent} from "./statistics/graphs/comparison/score-by-game-type/score-by-game-type.component";
import {PlayedGamesComponent} from "./statistics/graphs/comparison/played-games/played-games.component";
import {RankCounterComponent} from "./statistics/graphs/single-user/rank-counter/rank-counter.component";
import {FoundListenedMusicsRatioComponent} from "./statistics/graphs/single-user/found-listened-musics-ratio/found-listened-musics-ratio.component";
import {EndGameStatisticsViewComponent} from "./game/game-end-view/end-game-statistics/end-game-statistics-view.component";
import {ProfilesComparisonViewComponent} from "./statistics/profiles-comparison-view/profiles-comparison-view.component";
import {AnswerTypesComponent} from "./statistics/graphs/comparison/answer-types/answer-types.component";
import {ThemeSelectionComponent} from "./statistics/theme-selection/theme-selection.component";
import {EndGameRanksComponent} from "./game/game-end-view/end-game-ranks/end-game-ranks.component";
import {ParamsViewComponent} from './params-view/params-view.component';
import {SpotifyParamViewComponent} from './params-view/spotify-param/spotify-param-view.component';
import {GameCardComponent} from './game/game-card/game-card.component';

import {GlobalErrorHandler} from "./tools/global-error-handler";

import {ToolsService} from "./tools/tools.service";
import {ToasterService} from "./services/toaster.service";

import {ProfileResource} from "./resources/profile.resource";
import {AvatarResource} from "./resources/avatar.resource";
import {ProfileStatisticsResource} from "./resources/profile-statistics.resource";
import {GameResource} from "./resources/game.resource";
import {MusicResource} from "./resources/music.resource";
import {SpotifyParamResource} from "./resources/params/spotify-param.resource";
import {SpotifyResource} from "./resources/spotify.resource";

import {NgSelectModule} from "@ng-select/ng-select";
import {MatTabsModule} from "@angular/material/tabs";

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
	return new TranslateHttpLoader(http);
}

@NgModule({
	declarations: [
		AppComponent,
		ProfileViewComponent,
		ProfileCardComponent,
		ProfileCardEmptyComponent,
		ProfilePageComponent,
		ProfilePageModalComponent,
		ProfileEditModalComponent,
		ConfirmModalComponent,
		HeaderComponent,
		GameNewViewComponent,
		NavbarMenuComponent,
		HomeViewComponent,
		SignatureViewComponent,
		GameCurrentViewComponent,
		GameResumeViewComponent,
		PlayerCardComponent,
		ThemeEffectComponent,
		CustomCountdownComponent,
		MusicResultModalComponent,
		RoundInfoModalComponent,
		ChoiceThemeModalComponent,
		GameEndViewComponent,
		ErrorAlertModalComponent,
		GameResumeViewComponent,
		ProfilesStatisticsViewComponent,
		ThemePercentagesComponent,
		ThemeListeningComponent,
		ThemeComparisonViewComponent,
		ThemeComparisonQuestionDetailComponent,
		ThemeComparisonQuestionNumberComponent,
		ScoreByGameTypeComponent,
		RankCounterComponent,
		FoundListenedMusicsRatioComponent,
		UserThemeComparisonViewComponent,
		UserThemeComparisonQuestionDetailComponent,
		UserThemeComparisonQuestionNumberComponent,
		ProfilesComparisonViewComponent,
		UserRankComparisonComponent,
		EndGameStatisticsViewComponent,
		GlobalPercentagesComponent,
		PlayedGamesComponent,
		AnswerTypesComponent,
		ThemeSelectionComponent,
		EndGameRanksComponent,
		ParamsViewComponent,
		SpotifyParamViewComponent,
		GameCardComponent
	],
	entryComponents: [
		ProfileEditModalComponent,
		ConfirmModalComponent,
		ProfilePageModalComponent,
		MusicResultModalComponent,
		RoundInfoModalComponent,
		ChoiceThemeModalComponent,
		ErrorAlertModalComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		NgbModule,
		HttpClientModule,
		FormsModule,
		ReactiveFormsModule,
		FontAwesomeModule,
		BrowserAnimationsModule,
		NgxChartsModule,
		ToastrModule.forRoot(),
		TranslateModule.forRoot({
			loader: {
				provide: TranslateLoader,
				useFactory: HttpLoaderFactory,
				deps: [HttpClient]
			}
		}),
		Ng2OdometerModule.forRoot(),
		CountdownModule,
		MatCheckboxModule,
		NgHttpLoaderModule.forRoot(),
		MatRadioModule,
		NgSelectModule,
		MatTabsModule,
		MatSlideToggleModule,
		MatTooltipModule,
		NavbarModule,
		WavesModule,
		ButtonsModule,
		IconsModule
	],
	providers: [
		[{provide: ErrorHandler, useClass: GlobalErrorHandler}],
		ProfileResource,
		AvatarResource,
		ProfileStatisticsResource,
		GameResource,
		ToolsService,
		ToasterService,
		MusicResource,
		SpotifyParamResource,
		SpotifyResource
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
