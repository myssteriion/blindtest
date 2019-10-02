import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfileViewComponent } from './components/profile/profile-view/profile-view.component'

const routes: Routes = [
  { path: 'profiles', component: ProfileViewComponent },
  { path: '', redirectTo: 'profiles', pathMatch: 'full' },
  { path: '**', redirectTo: 'profiles' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
