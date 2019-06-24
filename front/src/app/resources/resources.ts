import { Injectable } from '@angular/core';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';

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
export class ResourceService {

  constructor(private _http: HttpClient) { }

  public getAllUsers() {
    return this._http.get('/profils');
  }

  public updateUser(user) {
    return this._http.put('/profils', user);
  }

  public createUser(user) {
    return this._http.post('/profils', user);
  }
}
