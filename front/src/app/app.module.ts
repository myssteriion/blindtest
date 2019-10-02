import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { ProfileViewComponent } from './components/profile/profile-view/profile-view.component'
import { ProfileCardComponent } from './components/profile/profile-card/profile-card.component';

import { ProfileResource } from './resources/profile.resource';
import { AvatarResource } from './resources/avatar.resource';
import { ProfileEditComponent } from './components/profile/profile-edit/profile-edit.component';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    ProfileViewComponent,
    ProfileCardComponent,
    ProfileEditComponent
  ],
  entryComponents: [
    ProfileEditComponent
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
    ProfileResource,
    AvatarResource
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
