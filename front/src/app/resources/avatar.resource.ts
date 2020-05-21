import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http"
import {Observable} from "rxjs";
import {Avatar} from "src/app/interfaces/dto/avatar.interface";
import {Page} from "src/app/interfaces/base/page.interface";
import {environment} from "src/environments/environment";

/**
 * Avatar resource.
 */
@Injectable()
export class AvatarResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/avatars";
	
	
	
	constructor(private _http: HttpClient) { }
	
	
	
	/**
	 * Gets avatars pageable filtered by search name.
	 *
	 * @param searchName the search name filter
	 * @param pageNumber the page number
	 */
	public findAllBySearchName(searchName: string, pageNumber: number): Observable< Page<Avatar> > {
		
		let params = new HttpParams();
		params = params.set("searchName", searchName);
		params = params.set("pageNumber", pageNumber.toString());
		params = params.set("itemPerPage", environment.itemPerPageAvatars.toString());
		
		return this._http.get< Page<Avatar> >( this.path, { params: params } );
	}
	
}
