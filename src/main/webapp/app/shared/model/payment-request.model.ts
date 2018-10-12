import { IMember } from 'app/shared/model//member.model';
import { IPaymentGroup } from 'app/shared/model//payment-group.model';

export interface IPaymentRequest {
    id?: number;
    amount?: number;
    settled?: boolean;
    url?: string;
    token?: string;
    from?: IMember;
    to?: IMember;
    group?: IPaymentGroup;
}

export class PaymentRequest implements IPaymentRequest {
    constructor(
        public id?: number,
        public amount?: number,
        public settled?: boolean,
        public url?: string,
        public token?: string,
        public from?: IMember,
        public to?: IMember,
        public group?: IPaymentGroup
    ) {
        this.settled = this.settled || false;
    }
}
