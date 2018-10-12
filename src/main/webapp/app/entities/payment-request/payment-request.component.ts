import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPaymentRequest } from 'app/shared/model/payment-request.model';
import { Principal } from 'app/core';
import { PaymentRequestService } from './payment-request.service';

@Component({
    selector: 'jhi-payment-request',
    templateUrl: './payment-request.component.html'
})
export class PaymentRequestComponent implements OnInit, OnDestroy {
    paymentRequests: IPaymentRequest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentRequestService: PaymentRequestService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.paymentRequestService.query().subscribe(
            (res: HttpResponse<IPaymentRequest[]>) => {
                this.paymentRequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPaymentRequest) {
        return item.id;
    }

    registerChangeInPaymentRequests() {
        this.eventSubscriber = this.eventManager.subscribe('paymentRequestListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
