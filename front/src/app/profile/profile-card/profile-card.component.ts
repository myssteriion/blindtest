import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {faEdit, faTrashAlt, faGamepad, faTimes} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileEditComponent} from 'src/app/profile/profile-edit/profile-edit.component';
import {ToolsService} from "../../tools/tools.service";
import {ModalConfirmComponent} from "../../common/modal/confirm/modal-confirm.component";
import {ProfileResource} from "../../resources/profile.resource";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from 'src/app/services/toaster.service';

/**
 * Profile card.
 */
@Component({
    selector: 'profile-card',
    templateUrl: './profile-card.component.html',
    styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent implements OnInit {

    /**
     * The profile.
     */
    @Input() profile: Profile;

    /**
     * If can update/delete profile.
     */
    @Input() canEdit: boolean;

    /**
     * Event after update/delete profile.
     */
    @Output() onEdit = new EventEmitter();

    /**
     * If can select profile.
     */
    @Input() canSelect: boolean;

    /**
     * On select.
     */
    @Output() onSelect = new EventEmitter();

    /**
     * If can deselect profile.
     */
    @Input() canDeselect: boolean;

    /**
     * On deselect.
     */
    @Output() onDeselect = new EventEmitter();

    faEdit = faEdit;
    faTrashAlt = faTrashAlt;
    faGamepad = faGamepad;
    faTimes = faTimes;


    constructor(private _ngbModal: NgbModal,
                private _profileResource: ProfileResource,
                private _translate: TranslateService,
                private _toasterService: ToasterService) {

    }

    ngOnInit() {

        ToolsService.verifyValue("profile", this.profile);
        ToolsService.verifyValue("profile.avatar", this.profile.avatar);
        ToolsService.verifyValue("canUpdate", this.canEdit);
        ToolsService.verifyValue("canSelect", this.canSelect);
        ToolsService.verifyValue("canDeselect", this.canDeselect);
    }



    /**
     * Gets the avatar flux.
     */
    public getFluxForImg(): string {
        return ToolsService.getFluxForImg(this.profile.avatar.flux);
    }

    /**
     * Gets background class.
     */
    public getBackgroundClass(): string {
        return "profile-card-background-" + this.profile.background;
    }

    /**
     * Open modal for edit profile and emit it.
     */
    public edit(): void {

        const modalRef = this._ngbModal.open(ProfileEditComponent, { backdrop: 'static' } );
        modalRef.componentInstance.profile = this.profile;
        modalRef.componentInstance.create = false;

        modalRef.result.then(
            (result) => { this.profile = result; this.onEdit.emit(); },
            (reason) => { /* do nothing */ }
        );
    }

    /**
     * Open modal for delete profile and emit it.
     */
    public delete(): void {

        const modalRef = this._ngbModal.open( ModalConfirmComponent, { backdrop: 'static' } );
        modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
        modalRef.componentInstance.body = this._translate.instant("PROFILE.CARD.DELETE_BODY", { profile_name: this.profile.name } );

        modalRef.result.then(
            (result) => {
                this._profileResource.delete(this.profile).subscribe(
                    response => {
                        this._toasterService.success( this._translate.instant("PROFILE.CARD.DELETED_TOASTER", { profile_name: this.profile.name } ) );
                        this.onEdit.emit();
                    },
                    error => { throw Error("can't delete profile: " + error); }
                );
            },
            (reason) => { /* do nothing */ }
        );
    }

    /**
     * Test if icons need to be show.
     */
    public showIcons(): boolean {
        return this.canEdit || this.canSelect || this.canDeselect;
    }

}
