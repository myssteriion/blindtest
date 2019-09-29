import { Component, OnInit } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { ProfileResource } from '../../resources/profile.resource';

@Component({
  selector: 'app-profiles',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public profiles: Profile[] = [];
  


  constructor(private _profileResource: ProfileResource ) {}



  ngOnInit() {
   
    this._profileResource.findAll().subscribe(
      response => { console.log("resp", response); this.profiles = response.items; },
      error => { console.log("can't find all profiles", error); }
    );
    
  }

}
