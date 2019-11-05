import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameResumeViewComponent } from './game-resume-view.component';

describe('GameResumeViewComponent', () => {
	let component: GameResumeViewComponent;
	let fixture: ComponentFixture<GameResumeViewComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ GameResumeViewComponent ]
		})
			.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(GameResumeViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
