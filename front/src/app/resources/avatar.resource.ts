import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'
import {Observable} from 'rxjs';
import {Avatar} from 'src/app/interfaces/avatar.interface';
import {Page} from 'src/app/interfaces/page.interface';
import {environment} from 'src/environments/environment';

@Injectable()
export class AvatarResource {

	private _avatarPath = environment.baseBackendUrl + "/avatars";



	constructor(private _http: HttpClient) { }



	public refresh(): Observable<any> {
		return this._http.get<Observable<any>>(this._avatarPath + "/refresh");
	}

	public findAllByNameStartingWith(prefixName: string, pageNumber: number): Observable< Page<Avatar> > {

		let params = new HttpParams();
		params = params.set('prefixName', prefixName);
		params = params.set('pageNumber', pageNumber.toString());

		return this._http.get< Page<Avatar> >( this._avatarPath, { params: params } );
	}

}
