export class PasswordUpdateRequest {
    constructor(readonly email: string,
                readonly newPassword: string) {
    }
}
