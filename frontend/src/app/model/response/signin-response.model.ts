export class SigninResponseModel {
    constructor(
        readonly token: string,
        readonly id: number,
        readonly username: string,
        readonly email: string) {
    }
}
