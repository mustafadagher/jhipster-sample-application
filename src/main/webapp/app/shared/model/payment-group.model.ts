import { Moment } from 'moment';
import { IBill } from 'app/shared/model//bill.model';
import { IPaymentRequest } from 'app/shared/model//payment-request.model';
import { IMember } from 'app/shared/model//member.model';

export interface IPaymentGroup {
    id?: number;
    name?: string;
    startDate?: Moment;
    endDate?: Moment;
    ongoing?: boolean;
    settled?: boolean;
    bills?: IBill[];
    paymentRequests?: IPaymentRequest[];
    author?: IMember;
    members?: IMember[];
}

export class PaymentGroup implements IPaymentGroup {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public ongoing?: boolean,
        public settled?: boolean,
        public bills?: IBill[],
        public paymentRequests?: IPaymentRequest[],
        public author?: IMember,
        public members?: IMember[]
    ) {
        this.ongoing = this.ongoing || false;
        this.settled = this.settled || false;
    }
}
