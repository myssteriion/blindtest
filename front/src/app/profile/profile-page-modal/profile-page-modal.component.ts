import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {Page} from 'src/app/interfaces/base/page.interface';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

/**
 * The profiles view.
 */
@Component({
    selector: 'profile-page-modal',
    templateUrl: './profile-page-modal.component.html',
    styleUrls: ['./profile-page-modal.component.css']
})
export class ProfilePageModalComponent implements OnInit {

    /**
     * Profiles page.
     */
    @Input()
    public page: Page<Profile>;

    /**
     * On select profile card.
     */
    @Output()
    public onSelect = new EventEmitter();



    constructor(private _ngbActiveModal: NgbActiveModal) {}

    ngOnInit() { }



    /**
     * Close modal.
     */
    public close(): void {
        this._ngbActiveModal.close();
    }

}
