import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfileViewComponent } from './profile/profile-view/profile-view.component'
import {GameNewViewComponent} from "./game/game-new-view/game-new-view.component";

const routes: Routes = [
  { path: 'game/new', component: GameNewViewComponent },
  { path: 'profiles', component: ProfileViewComponent },
  { path: '', redirectTo: 'game/new', pathMatch: 'full' },
  { path: '**', redirectTo: 'game/new' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
