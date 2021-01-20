export class SignupRequestModel {
    constructor(
        readonly firstName: string,
        readonly lastName: string,
        readonly username: string,
        readonly email: string,
        readonly password: string) {
    }
}
