export class ErrorMessages {
    static readonly VALIDATION_MIN_LENGTH_4: string = 'Filed length can\'t be less than 4';
    static readonly VALIDATION_MAX_LENGTH_20: string = 'Filed length can\'t be more than 20';
    static readonly VALIDATION_EMAIL: string = 'Enter valid email';
    static readonly VALIDATION_CONFIRM_PASSWORD: string = 'Passwords don\'t match';
    static readonly VALIDATION_REQUIRED: string = 'Field can\'t be empty';

    static readonly INCORRECT_CREDENTIALS: string = 'Incorrect login or password';
    static readonly DUPLICATED_USERNAME: string = 'This login is already in use';
    static readonly DUPLICATED_EMAIL: string = 'This email is already in use';

    static readonly NOT_FOUND_EMAIL: string = 'There is no account, associated with this email';
}
