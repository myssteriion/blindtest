import { HttpClient, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core";
import { Page } from "myssteriion-utils";
import { Observable } from "rxjs";
import { Avatar } from "src/app/interfaces/entity/avatar";
import { environment } from "src/environments/environment";

/**
 * Avatar resource.
 */
@Injectable()
export class AvatarResource {
	
	/**
	 * Rest path.
	 */
	private path = environment.backendPath + "/avatars";
	
	
	
	constructor(private http: HttpClient) { }
	
	
	
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
		
		return this.http.get< Page<Avatar> >( this.path, { params: params } );
	}
	
}
