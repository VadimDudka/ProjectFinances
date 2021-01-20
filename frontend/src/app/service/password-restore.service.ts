import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PasswordRestoreRequest} from '../model/request/password-restore-request';
import {Observable} from 'rxjs';
import {PasswordUpdateRequest} from '../model/request/password-update-request';

@Injectable({
    providedIn: 'root'
})
export class PasswordRestoreService {

    constructor(private httpClient: HttpClient) {
    }

    restoreRequest(passwordRestoreRequest: PasswordRestoreRequest): Observable<any> {
        return this.httpClient.post('/api/password-restore/token', passwordRestoreRequest);
    }

    getTokenData(tokenValue: string): Observable<any> {
        return this.httpClient.get(`/api/password-restore/token/${tokenValue}`);
    }

    updatePassword(passwordUpdateRequest: PasswordUpdateRequest): Observable<any> {
        return this.httpClient.put('/api/password-restore/password', passwordUpdateRequest);
    }
}
