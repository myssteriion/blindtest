import { Component, Input, OnInit } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfileEditComponent } from 'src/app/components/profile/profile-edit/profile-edit.component';
import { ToolsService } from "../../../tools/tools.service";
import { ProfileResource } from 'src/app/resources/profile.resource';

@Component({
    selector: 'profile-card',
    templateUrl: './profile-card.component.html',
    styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent implements OnInit {

    @Input() profile: Profile;

    @Input() canUpdate: boolean;

    faEdit = faEdit;

    faTrashAlt = faTrashAlt;



    constructor(private _ngbModal: NgbModal,
        private _toolsService: ToolsService,
        private _profileResource: ProfileResource) {

    }

    ngOnInit() {

        this._toolsService.verifyValue("profile", this.profile);
        this._toolsService.verifyValue("profile.avatar", this.profile.avatar);
        this._toolsService.verifyValue("canUpdate", this.canUpdate);
    }



    public getAvatarFluxForImg() {
        return this._toolsService.getAvatarFluxForImg(this.profile.avatar);
    }

    public edit() {

        const modalRef = this._ngbModal.open(ProfileEditComponent,
            {
                backdrop: 'static'
            }
        );
        modalRef.componentInstance.profile = this.profile;
        modalRef.componentInstance.create = false;

        modalRef.result
            .then((result) => {
                this._profileResource.update(result).subscribe(
                    response => {
                        this.profile = response;
                    },
                    error => {
                        new Error("can't update profile : " + error);
                    }
                );
            });
    }

}
