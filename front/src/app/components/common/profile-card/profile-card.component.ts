import { Component, OnInit, Input } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';

@Component({
  selector: 'profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {

  @Input() profile: Profile;

  constructor() { }

}
