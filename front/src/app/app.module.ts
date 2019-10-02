import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { ProfileViewComponent } from './components/profile/profile-view/profile-view.component'
import { ProfileCardComponent } from './components/profile/profile-card/profile-card.component';

import { ProfileResource, UrlInterceptor } from './resources/profile.resource';
import { AvatarResource } from './resources/avatar.resource';


@NgModule({
  declarations: [
    AppComponent,
    ProfileViewComponent,
    ProfileCardComponent
  ],
  entryComponents: [],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule
  ],
  providers: [
    ProfileResource,
    AvatarResource,
    { provide: HTTP_INTERCEPTORS, useClass: UrlInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
