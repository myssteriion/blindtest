import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Avatar } from 'src/app/interfaces/avatar.interface';
import { List } from 'src/app/interfaces/list.interface';

@Injectable()
export class UrlInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = 'http://localhost:8080';
    req = req.clone({
      url: url + req.url
    });
    return next.handle(req);
  }
}

@Injectable()
export class AvatarResource {

  private _avatarPath = '/avatars';



  constructor(private _http: HttpClient) { }



  public getAll(): Observable< List<Avatar> > {
    return this._http.get< List<Avatar> >(this._avatarPath);
  }

}
