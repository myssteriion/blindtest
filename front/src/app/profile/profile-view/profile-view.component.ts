import {Component, OnInit} from '@angular/core';
import {Profile} from 'src/app/interfaces/profile.interface';
import {ProfileResource} from 'src/app/resources/profile.resource';
import {Page} from 'src/app/interfaces/page.interface';
import {ToolsService} from "../../tools/tools.service";
import {ProfileEditComponent} from "../profile-edit/profile-edit.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'profile-view',
    templateUrl: './profile-view.component.html',
    styleUrls: ['./profile-view.component.css']
})
export class ProfileViewComponent implements OnInit {

    public page: Page<Profile>;

    public showProfiles: boolean;

    public prefixName: string;



    constructor(private _profileResource: ProfileResource,
                private _toolsService: ToolsService,
                private _ngbModal: NgbModal) {}

    ngOnInit() {

        this.showProfiles = false;
        this.prefixName = "";
        this.loadProfiles(1)
    }



    public loadProfiles(pageNumber: number): void {

        this._profileResource.findAllByNameStartingWith(this.prefixName, pageNumber-1).subscribe(
            response => { this.page = response; this.showProfiles = true; },
            error => { console.log("can't find all profiles", error); }
        );
    }

    public createProfile(): void {

        const modalRef = this._ngbModal.open(ProfileEditComponent, { backdrop: 'static' } );
        modalRef.componentInstance.create = true;

        modalRef.result.then(
            (result) => { this.loadProfiles(1); },
            (reason) => { /* do nothing */ }
        );
    }

}
