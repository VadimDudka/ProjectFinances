import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SigninRequestModel} from '../model/request/signin-request.model';
import {SignupRequestModel} from '../model/request/signup-request.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private httpClient: HttpClient) {
    }

    signIn(signinRequest: SigninRequestModel): Observable<any> {
        return this.httpClient.post('/api/auth/sign-in', signinRequest);
    }

    signUp(signupRequest: SignupRequestModel): Observable<any> {
        return this.httpClient.post('/api/auth/sign-up', signupRequest);
    }
}
