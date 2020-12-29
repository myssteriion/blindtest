import { Component, Input, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { CommonUtilsService } from "myssteriion-utils";
import { THEMES } from "src/app/tools/constant";
import { Theme } from "../../../interfaces/common/theme.enum";

/**
 * The choice theme modal.
 */
@Component({
	templateUrl: "./choice-theme-modal.component.html",
	styleUrls: ["./choice-theme-modal.component.css"]
})
export class ChoiceThemeModalComponent implements OnInit {
	
	/**
	 * Themes list.
	 */
	@Input()
	public filteredThemes: Theme[];
	
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
	public selectedTheme: Theme;
	
	
	
	constructor(private ngbActiveModal: NgbActiveModal,
				private commonUtilsService: CommonUtilsService) { }
	
	ngOnInit(): void {
		
		this.themes = [];
		THEMES.forEach(theme => {
			
			let index = this.filteredThemes.findIndex(thm => thm === theme.enumVal);
			if (index !== -1)
				this.themes.push(theme);
		});
	}
	
	
	
	/**
	 * Test if the close button must be disable.
	 *
	 * @return TRUE is the close button must be disable, FALSE otherwise
	 */
	public closeButtonIsDisable(): boolean {
		return this.commonUtilsService.isNull(this.selectedTheme);
	}
	
	/**
	 * Close modal.
	 */
	public close(): void {
		this.ngbActiveModal.close(this.selectedTheme);
	}
	
}
