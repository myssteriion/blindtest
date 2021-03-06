import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ToolsService} from "../../../tools/tools.service";
import {THEMES} from 'src/app/tools/constant';

/**
 * The choice theme modal.
 */
@Component({
	selector: 'choice-theme-modal',
	templateUrl: './choice-theme-modal.component.html',
	styleUrls: ['./choice-theme-modal.component.css']
})
export class ChoiceThemeModalComponent implements OnInit {
	
	/**
	 * Themes list.
	 */
	@Input()
	private filteredThemes: Theme[];
	
	/**
	 * Player name.
	 */
	@Input()
	public playerName: string;
	
	/**
	 * Themes list.
	 */
	public themes: {}[];
	
	/**
	 * Selected theme.
	 */
	private selectedTheme: Theme;
	
	
	
	constructor(private _ngbActiveModal: NgbActiveModal) {}
	
	ngOnInit(): void {
		
		this.themes = [];
		THEMES.forEach(theme => {
			
			let index = this.filteredThemes.findIndex(thm => thm === theme.enumVal);
			if (index !== -1) {
				this.themes.push(theme);
			}
		});
	}
	
	
	
	/**
	 * If the button is disabled.
	 */
	public closeIsDisabled(): boolean {
		return ToolsService.isNull(this.selectedTheme);
	}
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this._ngbActiveModal.close(this.selectedTheme);
	}
	
}
