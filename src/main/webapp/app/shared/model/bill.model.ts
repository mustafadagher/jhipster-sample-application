import { Moment } from 'moment';
import { IMember } from 'app/shared/model//member.model';
import { IPaymentGroup } from 'app/shared/model//payment-group.model';

export interface IBill {
    id?: number;
    description?: string;
    amount?: number;
    paymentDate?: Moment;
    mutationId?: string;
    owner?: IMember;
    paymentGroup?: IPaymentGroup;
}

export class Bill implements IBill {
    constructor(
        public id?: number,
        public description?: string,
        public amount?: number,
        public paymentDate?: Moment,
        public mutationId?: string,
        public owner?: IMember,
        public paymentGroup?: IPaymentGroup
    ) {}
}
