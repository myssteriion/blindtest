import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ProfileViewComponent} from './profile/profile-view/profile-view.component'
import {GameNewViewComponent} from "./game/game-new-view/game-new-view.component";
import {HomeViewComponent} from './home-view/home-view.component'


const routes: Routes = [
	{ path: 'home', component: HomeViewComponent },
	{ path: 'game/new', component: GameNewViewComponent },
	{ path: 'profiles', component: ProfileViewComponent },
	{ path: '', redirectTo: 'home', pathMatch: 'full' },
	{ path: '**', redirectTo: 'home' }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
