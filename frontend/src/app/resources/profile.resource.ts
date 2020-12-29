import { HttpClient, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core";
import { Page } from "myssteriion-utils";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Profile } from "../interfaces/entity/profile";

/**
 * Profile resource.
 */
@Injectable()
export class ProfileResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/profiles";
	
	
	
	constructor(private http: HttpClient) { }
	
	
	
	/**
	 * Create profile.
	 *
	 * @param profile the profile
	 * @return the profile
	 */
	public create(profile: Profile): Observable<Profile> {
		return this.http.post<Profile>(this.path , profile);
	}
	
	/**
	 * Update profile.
	 *
	 * @param profile the profile
	 * @return the profile
	 */
	public update(profile: Profile): Observable<Profile> {
		return this.http.put<Profile>(this.path + "/" + profile.id, profile);
	}
	
	/**
	 * Gets profiles pageable filtered by search name.
	 *
	 * @param name the name filter
	 * @param pageNumber the page number
	 * @param itemPerPage the item per page
	 * @return profiles list
	 */
	public findAllBySearchName(name: string, pageNumber: number, itemPerPage: string): Observable< Page<Profile> > {
		
		let params = new HttpParams();
		params = params.set("name", name);
		params = params.set("pageNumber", pageNumber.toString());
		params = params.set("itemPerPage", itemPerPage);
		
		return this.http.get< Page<Profile> >( this.path, { params: params } );
	}
	
	/**
	 * Delete profile.
	 *
	 * @param profile the profile
	 * @return observable
	 */
	public delete(profile: Profile): Observable<void> {
		return this.http.delete<void>(this.path + "/" + profile.id);
	}
	
}
