import {BrowserModule} from '@angular/platform-browser';
import {NgModule, ErrorHandler} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';

import {ProfileCardComponent} from './components/profile/profile-card/profile-card.component';
import {ProfileEditComponent} from './components/profile/profile-edit/profile-edit.component';
import {ProfileViewComponent} from './components/profile/profile-view/profile-view.component'
import {FaIconCustomComponent} from './common/fa-icon-custom/fa-icon-custom.component';
import {ModalConfirmComponent} from './common/modal/confirm/modal-confirm.component';

import {ProfileResource} from './resources/profile.resource';
import {AvatarResource} from './resources/avatar.resource';
import {ToolsService} from "./tools/tools.service";
import {GlobalErrorHandler} from "./tools/global-error-handler";
import { HeaderComponent } from './common/header/header.component';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    ProfileViewComponent,
    ProfileCardComponent,
    ProfileEditComponent,
    FaIconCustomComponent,
    ModalConfirmComponent,
    HeaderComponent
  ],
  entryComponents: [
    ProfileEditComponent,
    ModalConfirmComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
  })  ],
  providers: [
    [{provide: ErrorHandler, useClass: GlobalErrorHandler}],
    ProfileResource,
    AvatarResource,
    ToolsService
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
