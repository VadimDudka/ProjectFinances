import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ErrorMessages} from '../../../helper/constants/error-messages';
import {Router} from '@angular/router';
import {PathConstants} from '../../../helper/constants/path';
import {SnackBarService} from '../../../service/snack-bar.service';
import {PasswordRestoreService} from '../../../service/password-restore.service';
import {PasswordRestoreRequest} from '../../../model/request/password-restore-request';
import {Subscription} from 'rxjs';
import {Utils} from '../../../helper/utils';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.css', '../auth.css']
})
export class ForgotPasswordComponent implements OnInit, OnDestroy {

    form: FormGroup;

    errorMessages = {
        emailValid: ErrorMessages.VALIDATION_EMAIL,
        emailNotFound: ErrorMessages.NOT_FOUND_EMAIL
    };

    emailNotFound = false;

    waiting = false;

    private restoreRequestSubscription: Subscription;

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                private snackBarService: SnackBarService,
                private passwordRestoreService: PasswordRestoreService) {
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            email: ['', [Validators.required, Validators.email]]
        });
    }

    ngOnDestroy(): void {
        Utils.unsubscribe(this.restoreRequestSubscription);
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onMainClick(): void {

        this.emailNotFound = false;

        if (this.form.invalid) {
            return;
        }

        this.waiting = true;

        const passwordRestoreRequest: PasswordRestoreRequest = new PasswordRestoreRequest(this.f.email.value);

        Utils.unsubscribe(this.restoreRequestSubscription);
        this.restoreRequestSubscription = this.passwordRestoreService.restoreRequest(passwordRestoreRequest).subscribe(
            response => {
                this.waiting = false;
                this.snackBarService.showResetPasswordMessage(this.f.email.value);
            },
            error => {
                this.waiting = false;
                switch (error.status) {
                    case 400:
                        this.emailNotFound = true;
                        break;
                    default:
                        this.snackBarService.showServerErrorMessage();
                }
            }
        );
    }

    onSecondaryClick(): void {
        this.router.navigate([PathConstants.SIGN_IN]).then();
    }
}
