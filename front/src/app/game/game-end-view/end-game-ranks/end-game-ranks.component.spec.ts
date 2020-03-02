import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EndGameRanksComponent} from './end-game-ranks.component';

describe('EndGameRanksComponent', () => {
	let component: EndGameRanksComponent;
	let fixture: ComponentFixture<EndGameRanksComponent>;
	
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ EndGameRanksComponent ]
		})
			.compileComponents();
	}));
	
	beforeEach(() => {
		fixture = TestBed.createComponent(EndGameRanksComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});
	
	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
