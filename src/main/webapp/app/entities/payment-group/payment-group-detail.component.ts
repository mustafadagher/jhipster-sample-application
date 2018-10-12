import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentGroup } from 'app/shared/model/payment-group.model';

@Component({
    selector: 'jhi-payment-group-detail',
    templateUrl: './payment-group-detail.component.html'
})
export class PaymentGroupDetailComponent implements OnInit {
    paymentGroup: IPaymentGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentGroup }) => {
            this.paymentGroup = paymentGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
