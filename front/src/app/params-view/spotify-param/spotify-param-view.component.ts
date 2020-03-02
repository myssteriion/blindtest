import {Component, OnInit} from '@angular/core';
import {ProfileResource} from "../../resources/profile.resource";
import {SpotifyParamResource} from "../../resources/spotify-param.resource";
import {SpotifyParam} from "../../interfaces/dto/spotify-param.interface";
import {ErrorAlert} from "../../interfaces/base/error.alert.interface";
import {ErrorAlertModalComponent} from "../../common/error-alert/error-alert-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TranslateService} from "@ngx-translate/core";
import {HOME_PATH, THEMES} from "../../tools/constant";
import {Router} from "@angular/router";
import {faEye, faEyeSlash, IconDefinition} from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'spotify-param-view',
    templateUrl: './spotify-param-view.component.html',
    styleUrls: ['./spotify-param-view.component.css']
})
export class SpotifyParamViewComponent implements OnInit {
    
    /**
     * The SpotifyParam.
     */
    public spotifyParams: SpotifyParam;
    
    /**
     * Show/hide clientSecret.
     */
    public isHide: boolean;
    
    /**
     * Themes list.
     */
    public themes = THEMES;
    
    public faEye = faEye;
    public faEyeSlash = faEyeSlash;
    
    
    
    constructor(private _spotifyParamResource: SpotifyParamResource,
                private _translate: TranslateService,
                private _ngbModal: NgbModal,
                private _router: Router) { }
    
    ngOnInit() {
        
        this.isHide = true;
        this.findSpotifyParam();
    }
    
    
    
    private findSpotifyParam(): void {
        
        this._spotifyParamResource.find().subscribe(
            response => {
                this.spotifyParams = response;
            },
            error => {
                
                let errorAlert: ErrorAlert = { status: error.status, name: error.name, error: error.error };
                
                const modalRef = this._ngbModal.open(ErrorAlertModalComponent, { backdrop: 'static', size: 'lg' } );
                modalRef.componentInstance.text = this._translate.instant("PARAMS_VIEW.SPOTIFY_PARAM_VIEW.FIND_SPOTIFY_PARAM_ERROR");
                modalRef.componentInstance.suggestions = undefined;
                modalRef.componentInstance.error = errorAlert;
                modalRef.componentInstance.level = ErrorAlertModalComponent.ERROR;
                modalRef.componentInstance.showRetry = true;
                modalRef.componentInstance.closeLabel = this._translate.instant("COMMON.GO_HOME");
                
                modalRef.result.then(
                    () => { this.findSpotifyParam(); },
                    () => { this._router.navigateByUrl(HOME_PATH); }
                );
            }
        )
    }
    
    /**
     * Gets clientSecret type.
     *
     * @return "password" or "text"
     */
    public getClientSecretType(): string {
        return (this.isHide) ? "password" : "text";
    }
    
    /**
     * Gets clientSecret icon.
     *
     * @return "faEye" or "faEyeSlash"
     */
    public getClientSecretIcon(): IconDefinition {
        return (this.isHide) ? faEye : faEyeSlash;
    }
    
    public getSubListThemes(isOdd: boolean): {}[] {
        
        let subListThemes: {}[] = [];
    
        let start = (isOdd) ? 0 : 1;
        
        for (let i = start; i < this.themes.length; i = i+2)
            subListThemes.push(this.themes[i]);
            
        return subListThemes
    }
    
}
