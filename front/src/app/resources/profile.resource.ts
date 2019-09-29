import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Profile } from '../interfaces/profile.interface';
import { List } from '../interfaces/list.interface';

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
export class ProfileResource {

  private _profilePath = '/profils';



  constructor(private _http: HttpClient) { }



  public findAll(): Observable< List<Profile> > {
    return this._http.get< List<Profile> >(this._profilePath);
  }

}
