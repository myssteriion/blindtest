import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ToastrModule} from 'ngx-toastr';
import {AppRoutingModule} from './app-routing.module';
import {Ng2OdometerModule} from 'ng2-odometer';
import {CountdownModule} from 'ngx-countdown';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {NgHttpLoaderModule} from 'ng-http-loader';
import {MatRadioModule} from '@angular/material/radio';

import {AppComponent} from './app.component';
import {ProfileCardComponent} from './profile/profile-card/profile-card.component';
import {ProfileCardEmptyComponent} from './profile/profile-card-empty/profile-card-empty.component';
import {ProfileEditModalComponent} from './profile/profile-edit-modal/profile-edit-modal.component';
import {ProfilePageComponent} from './profile/profile-page/profile-page.component';
import {ProfilePageModalComponent} from './profile/profile-page-modal/profile-page-modal.component';
import {ProfileViewComponent} from './profile/profile-view/profile-view.component'
import {ConfirmModalComponent} from './common/modal/confirm/confirm-modal.component';
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";
import {GameNewViewComponent} from './game/game-new-view/game-new-view.component';
import {HeaderComponent} from './common/header/header.component';
import {HomeViewComponent} from './home-view/home-view.component';
import {VersionViewComponent} from './common/version-view/version-view.component';
import {GenericViewComponent} from "./generic-view/generic-view.component";
import {GameCurrentViewComponent} from './game/game-current-view/game-current-view.component';
import {GameResumeViewComponent} from './game/game-resume-view/game-resume-view.component';
import {PlayerCardComponent} from './player/player-card/player-card.component';
import {ThemeEffectComponent} from "./game/factoring-part/theme-effect/theme-effect.component";
import {CustomCountdownComponent} from './game/factoring-part/custom-countdown/custom-countdown.component';
import {MusicResultModalComponent} from "./game/factoring-part/music-result-modal/music-result-modal.component";
import {RoundInfoModalComponent} from './game/factoring-part/round-info-modal/round-info-modal.component';
import {ChoiceThemeModalComponent} from "./game/factoring-part/choice-theme-modal/choice-theme-modal.component";
import {GameEndViewComponent} from "./game/game-end-view/game-end-view.component";

import {GlobalErrorHandler} from "./tools/global-error-handler";

import {ToolsService} from "./tools/tools.service";
import {ToasterService} from "./services/toaster.service";

import {ProfileResource} from './resources/profile.resource';
import {AvatarResource} from './resources/avatar.resource';
import {GameResource} from "./resources/game.resource";
import {MusicResource} from "./resources/music.resource";
import {ErrorAlertModalComponent} from './common/error-alert/error-alert-modal.component';

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
		VersionViewComponent,
		GenericViewComponent,
		GameCurrentViewComponent,
		GameResumeViewComponent,
		PlayerCardComponent,
		ThemeEffectComponent,
		CustomCountdownComponent,
		MusicResultModalComponent,
		RoundInfoModalComponent,
		ChoiceThemeModalComponent,
		GameEndViewComponent,
		ErrorAlertModalComponent
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
		FontAwesomeModule,
		BrowserAnimationsModule,
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
		MatRadioModule
	],
	providers: [
		[{provide: ErrorHandler, useClass: GlobalErrorHandler}],
		ProfileResource,
		AvatarResource,
		GameResource,
		ToolsService,
		ToasterService,
		MusicResource
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
