import { Component, OnInit } from '@angular/core';
import { Profile } from 'src/app/interfaces/profile.interface';
import { ProfileResource } from 'src/app/resources/profile.resource';

@Component({
    selector: 'profile-view',
    templateUrl: './profile-view.component.html',
    styleUrls: ['./profile-view.component.css']
})
export class ProfileViewComponent implements OnInit {

    public profiles: Profile[] = [];



    constructor(private _profileResource: ProfileResource ) {}



    ngOnInit() {

        this._profileResource.findAllByNameStartingWith("", 0).subscribe(
            response => { this.profiles = response.content; },
            error => { console.log("can't find all profiles", error); }
        );
    }

}
