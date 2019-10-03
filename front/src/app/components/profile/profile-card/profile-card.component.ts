import {Component, Input, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {AvatarResource} from 'src/app/resources/avatar.resource';
import {faEdit, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ProfileEditComponent} from 'src/app/components/profile/profile-edit/profile-edit.component';
import {ToolsService} from "../../../tools/tools.service";

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



  constructor(private _avatarResource: AvatarResource,
              private _modalService: NgbModal,
              private _toolsService: ToolsService) {

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

    const modalRef = this._modalService.open(ProfileEditComponent, 
      {
        
      }
    );
    modalRef.componentInstance.profile = this.profile;
    modalRef.componentInstance.create = false;
  }

  public delete() {
    //todo
  }

}
