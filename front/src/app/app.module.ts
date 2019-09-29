import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { ProfileComponent } from './components/profile/profile.component'
import { ProfileCardComponent } from './components/common/profile-card/profile-card.component';

import { ProfileResource, UrlInterceptor } from './resources/profile.resource';

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    ProfileCardComponent
  ],
  entryComponents: [],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [
    ProfileResource,
    { provide: HTTP_INTERCEPTORS, useClass: UrlInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
