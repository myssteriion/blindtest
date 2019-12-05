import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Profile} from 'src/app/interfaces/dto/profile.interface';
import {faEdit, faTimes, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileEditModalComponent} from 'src/app/profile/profile-edit-modal/profile-edit-modal.component';
import {ToolsService} from "../../tools/tools.service";
import {ConfirmModalComponent} from "../../common/modal/confirm/confirm-modal.component";
import {ProfileResource} from "../../resources/profile.resource";
import {TranslateService} from '@ngx-translate/core';
import {ToasterService} from 'src/app/services/toaster.service';
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {HTTP_CONFLICT} from "../../tools/constant";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";

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
    @Input()
    private profile: Profile;

    /**
     * If can update/delete profile.
     */
    @Input()
    private canEdit: boolean;

    /**
     * Event after update/delete profile.
     */
    @Output()
    private onEdit = new EventEmitter();

    /**
     * If can select profile.
     */
    @Input()
    private canSelect: boolean;

    /**
     * On select.
     */
    @Output()
    private onSelect = new EventEmitter();

    /**
     * If can deselect profile.
     */
    @Input()
    private canDeselect: boolean;

    /**
     * On deselect.
     */
    @Output()
    private onDeselect = new EventEmitter();

    private faEdit = faEdit;
    private faTrashAlt = faTrashAlt;
    private faTimes = faTimes;



    constructor(private _ngbModal: NgbModal,
                private _profileResource: ProfileResource,
                private _translate: TranslateService,
                private _toasterService: ToasterService) {

    }

    ngOnInit(): void {

        ToolsService.verifyValue("profile", this.profile);
        ToolsService.verifyValue("profile.avatar", this.profile.avatar);
        ToolsService.verifyValue("canUpdate", this.canEdit);
        ToolsService.verifyValue("canSelect", this.canSelect);
        ToolsService.verifyValue("canDeselect", this.canDeselect);
    }



    /**
     * Gets the avatar flux.
     */
    private getFluxForImg(): string {
        return ToolsService.getFluxForImg(this.profile.avatar.flux);
    }

    /**
     * Gets background class.
     */
    private getBackgroundClass(): string {

        let backgroundClass = "profile-card-background-" + this.profile.background;

        if (this.canSelect)
            backgroundClass += " profile-card-profile-frame-can-select";

        return backgroundClass;
    }

    /**
     * Open modal for edit profile and emit it.
     */
    private edit(): void {

        const modalRef = this._ngbModal.open(ProfileEditModalComponent, { backdrop: 'static', size: 'lg', centered: true } );
        modalRef.componentInstance.profile = this.profile;
        modalRef.componentInstance.create = false;

        modalRef.result.then(
            (result) => { this.profile = result; this.onEdit.emit(); },
            (reason) => { /* do nothing */ }
        );
    }

    /**
     * Open modal for delete profile.
     */
    private delete(): void {

        const modalRef = this._ngbModal.open( ConfirmModalComponent, { backdrop: 'static' } );
        modalRef.componentInstance.title = this._translate.instant("COMMON.WARNING");
        modalRef.componentInstance.body = this._translate.instant("PROFILE.CARD.DELETE_BODY", { profile_name: this.profile.name } );

        modalRef.result.then(
            (result) => { this.deletedProfile() },
            (reason) => { /* do nothing */ }
        );
    }

    /**
     * Delete profile and emit it.
     */
    private deletedProfile(): void {

        this._profileResource.delete(this.profile).subscribe(
            response => {
                this._toasterService.success( this._translate.instant("PROFILE.CARD.DELETED_TOASTER", { profile_name: this.profile.name } ) );
                this.onEdit.emit();
            },
            error => {

                let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };

                if (errorAlert.status === HTTP_CONFLICT) {
                    this._toasterService.error( this._translate.instant("PROFILE.EDIT_MODAL.PROFILE_ALREADY_EXISTS_ERROR") );
                }
                else {

                    const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' });
                    modalRef.componentInstance.text = this._translate.instant("PROFILE.CARD.DELETE_ERROR");
                    modalRef.componentInstance.suggestions = undefined;
                    modalRef.componentInstance.error = errorAlert;
                    modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
                    modalRef.componentInstance.showRetry = true;
                    modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.CLOSE");

                    modalRef.result.then(
                        (result) => { this.deletedProfile(); },
                        (reason) => { /* do nothing */ }
                    );
                }
            }
        );
    }

    /**
     * On click on profile.
     */
    private select(): void {
        if (this.canSelect)
            this.onSelect.emit();
    }

    /**
     * Test if icons need to be show.
     */
    private showIcons(): boolean {
        return this.canEdit || this.canDeselect;
    }

}
