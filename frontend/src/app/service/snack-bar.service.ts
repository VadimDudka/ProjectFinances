import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class SnackBarService {

    constructor(private matSnackBar: MatSnackBar) {
    }

    // ----------------------------------------------------------
    // | Abstract messages
    // ----------------------------------------------------------
    private showMessage(message: string, config: any): void {
        this.matSnackBar.open(message, '', config);
    }

    private showInfoMessage(message: string): void {
        this.showMessage(message, { duration: 2000 });
    }

    private showErrorMessage(message: string): void {
        this.showMessage(message, { duration: 5000, panelClass: 'error-snackbar' });
    }

    private showSuccessMessage(message: string): void {
        this.showMessage(message, { duration: 2500, panelClass: 'success-snackbar' });
    }

    // ----------------------------------------------------------
    // | Info messages
    // ----------------------------------------------------------
    showResetPasswordMessage(email: string): void {
        this.showInfoMessage(`The email has been sent to '${email}'`);
    }

    // ----------------------------------------------------------
    // | Error messages
    // ----------------------------------------------------------
    showServerErrorMessage(): void {
        this.showErrorMessage(`This feature is unavailable now. There is nothing you cant to do here. Please try later`);
    }

    showInvalidTokenMessage(): void {
        this.showErrorMessage(`This token is invalid, please create another one`);
    }

    // ----------------------------------------------------------
    // | Success messages
    // ----------------------------------------------------------
    showSuccessfulRegistrationMessage(login: string): void {
        this.showSuccessMessage(`You have successfully registered as '${login}'`);
    }

    showSuccessfulLoginMessage(): void {
        this.showSuccessMessage(`Welcome back`);
    }

    showSuccessfulPasswordUpdateMessage(): void {
        this.showSuccessMessage(`Your password has been updated`);
    }
}
