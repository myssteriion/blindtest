import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/page.interface';
import {ProfileEditComponent} from "../profile-edit/profile-edit.component";
import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

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
    @Input()  page: Page<Profile>;

    /**
     * On select profile card.
     */
    @Output() onSelect = new EventEmitter();



    constructor(private _ngbActiveModal: NgbActiveModal) {}

    ngOnInit() { }

    /**
     * Close modal.
     */
    public close() {
        this._ngbActiveModal.close();
    }

}
