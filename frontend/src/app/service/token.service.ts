import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class TokenService {

    private static readonly TOKEN_KEY: string = 'auth-token';
    private static readonly USER_KEY: string = 'auth-user';

    constructor() {
    }

    static setToken(token: string): void {
        this.setInStorage(TokenService.TOKEN_KEY, token);
    }

    static getToken(): string | null {
        return this.getFromStorage(this.TOKEN_KEY);
    }

    static deleteToken(): void {
        this.deleteFromStorage(this.TOKEN_KEY);
    }

    static setUser(user: any): void {
        this.setInStorage(TokenService.USER_KEY, JSON.stringify(user));
    }

    static getUser(): any {
        const user = this.getFromStorage(this.USER_KEY);
        return user ? JSON.parse(user) : null;
    }

    static clearStorage(): void {
        window.sessionStorage.clear();
    }

    private static setInStorage(key: string, value: string): void {
        window.sessionStorage.setItem(key, value);
    }

    private static getFromStorage(key: string): string | null {
        return window.sessionStorage.getItem(key);
    }

    private static deleteFromStorage(key: string): void {
        window.sessionStorage.removeItem(key);
    }
}
