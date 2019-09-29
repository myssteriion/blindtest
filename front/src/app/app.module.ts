import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { UserResourceService, UrlInterceptor } from './resources/userResources'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { AddUserComponent } from './features/users/add-user/add-user.component';
import { StartGameComponent } from './features/start-game/start-game.component';
import { ProfilesComponent } from './components/profiles/profiles.component'

@NgModule({
  declarations: [
    AppComponent,
    UserCardComponent,
    AddUserComponent,
    StartGameComponent,
    ProfilesComponent
  ],
  entryComponents: [
    StartGameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [
    UserResourceService,
    { provide: HTTP_INTERCEPTORS, useClass: UrlInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
