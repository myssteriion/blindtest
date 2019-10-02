import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Profile } from '../interfaces/profile.interface';
import { List } from '../interfaces/list.interface';
import { environment } from 'src/environments/environment';

@Injectable()
export class ProfileResource {

  private _profilePath = environment.baseBackendUrl + "/profiles";



  constructor(private _http: HttpClient) { }



  public update(profile: Profile): Observable<Profile> {
    return this._http.put<Profile>(this._profilePath + "/" + profile.id, profile);
  }

  public findAll(): Observable< List<Profile> > {
    return this._http.get< List<Profile> >(this._profilePath);
  }

}
