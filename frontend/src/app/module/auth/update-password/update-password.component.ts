import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {ErrorMessages} from '../../../helper/constants/error-messages';
import {Utils} from '../../../helper/utils';
import {PasswordRestoreService} from '../../../service/password-restore.service';
import {PathConstants} from '../../../helper/constants/path';
import {switchMap} from 'rxjs/operators';
import {SnackBarService} from '../../../service/snack-bar.service';
import {PasswordUpdateRequest} from '../../../model/request/password-update-request';

@Component({
    selector: 'app-update-password',
    templateUrl: './update-password.component.html',
    styleUrls: ['./update-password.component.css', '../auth.css']
})
export class UpdatePasswordComponent implements OnInit, OnDestroy {

    userEmail: string;

    form: FormGroup;
    hidePasswordMain = true;
    hidePasswordConfirm = true;

    waiting = false;

    tokenCheck = true;

    private passwordUpdateSubscription: Subscription;
    private emailSubscription: Subscription;

    readonly errorMessages = {
        minLength: ErrorMessages.VALIDATION_MIN_LENGTH_4,
        maxLength: ErrorMessages.VALIDATION_MAX_LENGTH_20,
        passwordConfirm: ErrorMessages.VALIDATION_CONFIRM_PASSWORD
    };

    constructor(private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private passwordRestoreService: PasswordRestoreService,
                private snackBarService: SnackBarService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.emailSubscription = this.route.params.pipe(
            switchMap(({tokenValue}) => this.passwordRestoreService.getTokenData(tokenValue))
        ).subscribe(
            response => {
                this.userEmail = response.email;
                this.tokenCheck = false;
            },
            error => {
                switch (error.status) {
                    case 400:
                        this.snackBarService.showInvalidTokenMessage();
                        this.router.navigate([PathConstants.SIGN_IN]).then();
                        break;
                    default:
                        this.snackBarService.showServerErrorMessage();
                        this.router.navigate([PathConstants.SIGN_IN]).then();
                }
            });

        this.form = this.formBuilder.group({
                passwordMain: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    Validators.maxLength(20)
                ]],
                passwordConfirm: [''],
            }, {validator: confirmPasswordValidator('passwordMain', 'passwordConfirm')}
        );
    }

    ngOnDestroy(): void {
        Utils.unsubscribe(this.passwordUpdateSubscription);
        Utils.unsubscribe(this.emailSubscription);
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onMainClick(): void {

        if (this.form.invalid) {
            return;
        }

        this.waiting = true;

        const passwordUpdateRequest: PasswordUpdateRequest = new PasswordUpdateRequest(
            this.userEmail,
            this.f.passwordMain.value
        );

        this.passwordUpdateSubscription = this.passwordRestoreService.updatePassword(passwordUpdateRequest).subscribe(
            request => {
                this.snackBarService.showSuccessfulPasswordUpdateMessage();
                this.router.navigate([PathConstants.SIGN_IN]).then();
            },
            error => {
                this.waiting = false;
                this.snackBarService.showServerErrorMessage();
            }
        );
    }

    onSecondaryClick(): void {
        this.router.navigate([PathConstants.SIGN_IN]).then();
    }
}

function confirmPasswordValidator(mainPassword: string, confirmPassword: string): (formGroup: FormGroup) => void {
    return (formGroup => {
        const mainPasswordValue = formGroup.controls[mainPassword].value;
        const confirmPasswordControl = formGroup.controls[confirmPassword];
        const confirmPasswordValue = confirmPasswordControl.value;
        if (mainPasswordValue !== confirmPasswordValue) {
            confirmPasswordControl.setErrors({confirmError: true});
        }
    });
}
