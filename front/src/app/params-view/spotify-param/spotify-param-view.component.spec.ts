import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpotifyParamViewComponent } from './spotify-param-view.component';

describe('SpotifyParamComponent', () => {
	let component: SpotifyParamViewComponent;
	let fixture: ComponentFixture<SpotifyParamViewComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ SpotifyParamViewComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(SpotifyParamViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
