import { Component, OnInit, Input } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { AvatarResource } from 'src/app/resources/avatar.resource';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {

  @Input() profile: Profile;

  @Input() canUpdate: boolean;

  private static _DATA: string = "data:";

  private static _BASE64: string = ";base64,";

  private static _AVATAR_NOT_FOUND = "assets/images/avatar/not-found.png";
  
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;



  constructor(private _avatarResource: AvatarResource) { }
  
  
  
  public getImageSrc() {
    
    let imageSrc: string;

    if (this.profile.avatar.fileExists) {

      imageSrc = ProfileCardComponent._DATA;
      imageSrc += this.profile.avatar.contentType;
      imageSrc += ProfileCardComponent._BASE64;
      imageSrc += this.profile.avatar.flux;
    }
    else {
      imageSrc = ProfileCardComponent._AVATAR_NOT_FOUND;
    }

    return imageSrc;
  }

  public edit() {
    this._avatarResource.getAll().subscribe(
      response => { console.log("ok", response); },
      error => { console.log("can't find all profiles", error); }
    );
  }

  public remove() {
    this._avatarResource.getAll().subscribe(
      response => { console.log("ok", response); },
      error => { console.log("can't find all profiles", error); }
    );
  }

}
