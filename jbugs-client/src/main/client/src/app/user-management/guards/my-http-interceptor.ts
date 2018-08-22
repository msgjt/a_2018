import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {TOKENKEY} from "../services/user.service";

export class MyHttpInterceptor implements HttpInterceptor{
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = localStorage.getItem(TOKENKEY);
    let cloneRequest = req.clone({
      setHeaders: {'Authorization': `Bearer ${token}`}
    });
    return next.handle(cloneRequest);
  }
}
