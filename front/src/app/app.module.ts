import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';

import {ProfileCardComponent} from './profile/profile-card/profile-card.component';
import {ProfileCardEmptyComponent} from './profile/profile-card-empty/profile-card-empty.component';
import {ProfileEditComponent} from './profile/profile-edit/profile-edit.component';
import {ProfilePageComponent} from './profile/profile-page/profile-page.component';
import {ProfilePageModalComponent} from './profile/profile-page-modal/profile-page-modal.component';
import {ProfileViewComponent} from './profile/profile-view/profile-view.component'
import {FaIconCustomComponent} from './common/fa-icon-custom/fa-icon-custom.component';
import {ModalConfirmComponent} from './common/modal/confirm/modal-confirm.component';
import {NavbarMenuComponent} from "./common/navbar-menu/navbar-menu.component";
import {GameNewViewComponent} from './game/game-new-view/game-new-view.component';
import {HeaderComponent} from './common/header/header.component';

import {ProfileResource} from './resources/profile.resource';
import {AvatarResource} from './resources/avatar.resource';
import {ToolsService} from "./tools/tools.service";
import {ToasterService} from "./services/toaster.service";
import {GlobalErrorHandler} from "./tools/global-error-handler";

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
		ProfileEditComponent,
		FaIconCustomComponent,
		ModalConfirmComponent,
		HeaderComponent,
		GameNewViewComponent,
		NavbarMenuComponent
	],
	entryComponents: [
		ProfileEditComponent,
		ModalConfirmComponent,
		ProfilePageModalComponent
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
		})
	],
	providers: [
		[{provide: ErrorHandler, useClass: GlobalErrorHandler}],
		ProfileResource,
		AvatarResource,
		ToolsService,
		ToasterService
	],
	bootstrap: [AppComponent]
})

export class AppModule { }
