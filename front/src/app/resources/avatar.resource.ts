import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Avatar } from 'src/app/interfaces/avatar.interface';
import { List } from 'src/app/interfaces/list.interface';
import { environment } from 'src/environments/environment';

@Injectable()
export class AvatarResource {

  private _avatarPath = environment.baseBackendUrl + "/avatars";



  constructor(private _http: HttpClient) { }



  public getAll(): Observable< List<Avatar> > {
    return this._http.get< List<Avatar> >(this._avatarPath);
  }

}
