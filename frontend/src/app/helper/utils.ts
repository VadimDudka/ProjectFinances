import {Subscription} from 'rxjs';

export class Utils {
    static unsubscribe(subscription: Subscription): void {
        if (subscription !== undefined && subscription !== null) {
            subscription.unsubscribe();
        }
    }
}
