import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {TokenService} from '../service/token.service';
import {PathConstants} from './constants/path';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

        // const currentUser = TokenService.getUser();
        const currentToken = TokenService.getToken();
        if (currentToken) {
            return true;
        }
        this.router.navigate([PathConstants.SIGN_IN]).then();
        return false;
    }
}
