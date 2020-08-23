import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ProfileViewComponent} from './profile/profile-view/profile-view.component'
import {GameNewViewComponent} from "./game/game-new-view/game-new-view.component";
import {HomeViewComponent} from './home-view/home-view.component'
import {GameCurrentViewComponent} from "./game/game-current-view/game-current-view.component";
import {GameResumeViewComponent} from './game/game-resume-view/game-resume-view.component';
import {GameEndViewComponent} from "./game/game-end-view/game-end-view.component";
import {ProfilesStatisticsViewComponent} from './statistics/profiles-statistics-view/profiles-statistics-view.component';
import {ParamsViewComponent} from "./params-view/params-view.component";


const routes: Routes = [
	{ path: 'home', component: HomeViewComponent },
	{ path: 'game/new', component: GameNewViewComponent },
	{ path: 'game/resume', component: GameResumeViewComponent },
	{ path: 'game/:id', component: GameCurrentViewComponent },
	{ path: 'game/end/:id', component: GameEndViewComponent },
	{ path: 'profiles', component: ProfileViewComponent },
	{ path: 'statistics', component: ProfilesStatisticsViewComponent },
	// { path: 'params', component: ParamsViewComponent },
	{ path: '', redirectTo: 'generic', pathMatch: 'full' },
	{ path: '**', redirectTo: 'home' }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
