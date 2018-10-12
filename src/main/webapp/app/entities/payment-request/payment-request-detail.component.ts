import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentRequest } from 'app/shared/model/payment-request.model';

@Component({
    selector: 'jhi-payment-request-detail',
    templateUrl: './payment-request-detail.component.html'
})
export class PaymentRequestDetailComponent implements OnInit {
    paymentRequest: IPaymentRequest;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentRequest }) => {
            this.paymentRequest = paymentRequest;
        });
    }

    previousState() {
        window.history.back();
    }
}
