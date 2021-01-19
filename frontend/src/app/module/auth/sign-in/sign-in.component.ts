import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../service/auth.service';
import {SigninRequestModel} from '../../../model/request/signin-request.model';
import {TokenService} from '../../../service/token.service';
import {Router} from '@angular/router';
import {PathConstants} from '../../../helper/constants/path';
import {ErrorMessages} from '../../../helper/constants/error-messages';
import {SnackBarService} from '../../../service/snack-bar.service';
import {Subscription} from 'rxjs';
import {Utils} from '../../../helper/utils';

@Component({
    selector: 'app-sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css', '../auth.css']
})
export class SignInComponent implements OnInit, OnDestroy {

    form: FormGroup;
    hidePassword = true;
    loginFailed = false;
    waiting = false;

    private signInSubscription: Subscription;

    readonly errorMessages = {
        required: ErrorMessages.VALIDATION_REQUIRED,
        incorrectCredentials: ErrorMessages.INCORRECT_CREDENTIALS
    };

    constructor(private snackBarService: SnackBarService,
                private formBuilder: FormBuilder,
                private authService: AuthService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (TokenService.getToken()) {
            this.router.navigate([PathConstants.MAIN_PAGE]).then();
        }

        this.form = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    ngOnDestroy(): void {
        Utils.unsubscribe(this.signInSubscription);
    }

    get f(): { [key: string]: AbstractControl } {
        return this.form.controls;
    }

    onMainClick(): void {

        this.loginFailed = false;

        if (this.form.invalid) {
            return;
        }

        this.waiting = true;

        const request: SigninRequestModel = new SigninRequestModel(
            this.f.username.value,
            this.f.password.value);

        Utils.unsubscribe(this.signInSubscription);
        this.signInSubscription = this.authService.signIn(request).subscribe(
            response => {
                TokenService.setToken(response.token);
                this.snackBarService.showSuccessfulLoginMessage();
                this.router.navigate([PathConstants.MAIN_PAGE]).then();
            },
            error => {
                this.waiting = false;
                switch (error.status) {
                    case 401:
                        this.loginFailed = true;
                        break;
                    default:
                        this.snackBarService.showServerErrorMessage();
                }
            }
        );
    }

    onSecondaryClick(): void {
        this.router.navigate([PathConstants.SIGN_UP]).then();
    }

    onLostPasswordClick(): void {
        this.router.navigate([PathConstants.FORGOT_PASSWORD]).then();
    }
}
