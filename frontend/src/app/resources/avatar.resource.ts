import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http"
import {Observable} from "rxjs";
import {Avatar} from "src/app/interfaces/entity/avatar.interface";
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
	 * @param name        the name filter
	 * @param pageNumber  the page number
	 * @param itemPerPage the item per page
	 * @return avatars list
	 */
	public findAllBySearchName(name: string, pageNumber: number, itemPerPage: string): Observable< Page<Avatar> > {
		
		let params = new HttpParams();
		params = params.set("name", name);
		params = params.set("pageNumber", pageNumber.toString());
		params = params.set("itemPerPage", itemPerPage);
		
		return this._http.get< Page<Avatar> >( this.path, { params: params } );
	}
	
}
