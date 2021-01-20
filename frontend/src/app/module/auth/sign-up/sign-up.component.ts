import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../../service/auth.service';
import {SignupRequestModel} from '../../../model/request/signup-request.model';
import {ErrorMessages} from '../../../helper/constants/error-messages';
import {PathConstants} from '../../../helper/constants/path';
import {SnackBarService} from '../../../service/snack-bar.service';
import {Subscription} from 'rxjs';
import {Utils} from '../../../helper/utils';

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.css', '../auth.css']
})
export class SignUpComponent implements OnInit, OnDestroy {

    form: FormGroup;

    hidePassword = true;
    hideConfirmPassword = true;

    duplicatedUsername = false;
    duplicatedEmail = false;

    waiting = false;

    signUpSubscription: Subscription;

    readonly errorMessages = {
        minLength: ErrorMessages.VALIDATION_MIN_LENGTH_4,
        maxLength: ErrorMessages.VALIDATION_MAX_LENGTH_20,
        usernameDuplicated: ErrorMessages.DUPLICATED_USERNAME,
        emailValid: ErrorMessages.VALIDATION_EMAIL,
        emailDuplicated: ErrorMessages.DUPLICATED_EMAIL,
        passwordConfirm: ErrorMessages.VALIDATION_CONFIRM_PASSWORD
    };

    constructor(private snackBarService: SnackBarService,
                private formBuilder: FormBuilder,
                private authService: AuthService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
                firstName: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    Validators.maxLength(20)
                ]],
                lastName: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    Validators.maxLength(20)
                ]],
                username: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    Validators.maxLength(20)
                ]],
                email: ['', [
                    Validators.required,
                    Validators.email
                ]],
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
        Utils.unsubscribe(this.signUpSubscription);
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onMainClick(): void {

        this.duplicatedEmail = false;
        this.duplicatedUsername = false;

        if (this.form.invalid) {
            return;
        }

        this.waiting = true;

        const request: SignupRequestModel = new SignupRequestModel(
            this.f.firstName.value,
            this.f.lastName.value,
            this.f.username.value,
            this.f.email.value,
            this.f.passwordMain.value);

        Utils.unsubscribe(this.signUpSubscription);
        this.signUpSubscription = this.authService.signUp(request).subscribe(
            response => {
                console.log(response);
                this.snackBarService.showSuccessfulRegistrationMessage(this.f.username.value);
                this.router.navigate([PathConstants.SIGN_IN]).then();
            },
            error => {
                this.waiting = false;
                switch (error.status) {
                    case 400:
                        switch (error.error.type) {
                            case 'DUPLICATE_EMAIL':
                                this.duplicatedEmail = true;
                                break;
                            case 'DUPLICATE_USERNAME':
                                this.duplicatedUsername = true;
                                break;
                            default:
                                console.log(`WARN: Unknown type: '${error.error.type}'`);
                        }
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
