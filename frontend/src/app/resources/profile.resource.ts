import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http"
import {Observable} from "rxjs";
import {Profile} from "../interfaces/dto/profile.interface";
import {Page} from "../interfaces/base/page.interface";
import {environment} from "src/environments/environment";

/**
 * Profile resource.
 */
@Injectable()
export class ProfileResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/profiles";
	
	
	
	constructor(private _http: HttpClient) { }
	
	
	
	/**
	 * Create profile.
	 *
	 * @param profile the profile.
	 */
	public create(profile: Profile): Observable<Profile> {
		return this._http.post<Profile>(this.path , profile);
	}
	
	/**
	 * Update profile.
	 *
	 * @param profile the profile
	 */
	public update(profile: Profile): Observable<Profile> {
		return this._http.put<Profile>(this.path + "/" + profile.id, profile);
	}
	
	/**
	 * Gets profiles pageable filtered by search name.
	 *
	 * @param name the name filter
	 * @param pageNumber the page number
	 */
	public findAllBySearchName(name: string, pageNumber: number): Observable< Page<Profile> > {
		
		let params = new HttpParams();
		params = params.set("name", name);
		params = params.set("pageNumber", pageNumber.toString());
		params = params.set("itemPerPage", environment.itemPerPageProfiles.toString());
		
		return this._http.get< Page<Profile> >( this.path, { params: params } );
	}
	
	/**
	 * Delete profile.
	 *
	 * @param profile the profile
	 */
	public delete(profile: Profile): Observable<void> {
		return this._http.delete<void>(this.path + "/" + profile.id);
	}
	
}
