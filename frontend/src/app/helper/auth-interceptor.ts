import {Injectable} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from '../service/token.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    private static readonly TOKEN_HEADER_KEY = 'Authorization';

    constructor() {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = TokenService.getToken();
        if (token) {
            req.headers.set(AuthInterceptor.TOKEN_HEADER_KEY, 'Bearer ' + token);
        }
        return next.handle(req);
    }
}
