import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserCardComponent } from './users/user-card/user-card.component';
import { AddUserComponent } from './users/add-user/add-user.component';
import { StartGameComponent } from './start-game/start-game.component'

@NgModule({
  declarations: [
    AppComponent,
    UserCardComponent,
    AddUserComponent,
    StartGameComponent
  ],
  entryComponents: [
    StartGameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
