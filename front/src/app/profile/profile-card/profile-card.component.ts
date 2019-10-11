import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfileEditComponent } from 'src/app/profile/profile-edit/profile-edit.component';
import { ToolsService } from "../../tools/tools.service";
import {ModalConfirmComponent} from "../../common/modal/confirm/modal-confirm.component";
import {ProfileResource} from "../../resources/profile.resource";
import { TranslateService } from '@ngx-translate/core';
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
    @Input() canUpdate: boolean;

    /**
     * Event after update/delete profile.
     */
    @Output() onEdit = new EventEmitter();

    faEdit = faEdit;
    faTrashAlt = faTrashAlt;



    constructor(private _ngbModal: NgbModal,
                private _toolsService: ToolsService,
                private _profileResource: ProfileResource,
                private _translate: TranslateService,
                private _toasterService: ToasterService) {

    }

    ngOnInit() {

        this._toolsService.verifyValue("profile", this.profile);
        this._toolsService.verifyValue("profile.avatar", this.profile.avatar);
        this._toolsService.verifyValue("canUpdate", this.canUpdate);
    }


    /**
     * Gets the avatar flux.
     */
    public getFluxForImg(): string {
        return this._toolsService.getFluxForImg(this.profile.avatar.flux);
    }

    /**
     * Gets background class.
     */
    public getBackgroundClass(): string {
        return "profile-card-background-" + this.profile.background;
    }

    /**
     * Open modal for edit profile.
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
     * Open modal for delete profile.
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

}
