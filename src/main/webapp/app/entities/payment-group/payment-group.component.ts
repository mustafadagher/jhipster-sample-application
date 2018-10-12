import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPaymentGroup } from 'app/shared/model/payment-group.model';
import { Principal } from 'app/core';
import { PaymentGroupService } from './payment-group.service';

@Component({
    selector: 'jhi-payment-group',
    templateUrl: './payment-group.component.html'
})
export class PaymentGroupComponent implements OnInit, OnDestroy {
    paymentGroups: IPaymentGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentGroupService: PaymentGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.paymentGroupService.query().subscribe(
            (res: HttpResponse<IPaymentGroup[]>) => {
                this.paymentGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPaymentGroup) {
        return item.id;
    }

    registerChangeInPaymentGroups() {
        this.eventSubscriber = this.eventManager.subscribe('paymentGroupListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
