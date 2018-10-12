import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPaymentRequest } from 'app/shared/model/payment-request.model';
import { PaymentRequestService } from './payment-request.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member';
import { IPaymentGroup } from 'app/shared/model/payment-group.model';
import { PaymentGroupService } from 'app/entities/payment-group';

@Component({
    selector: 'jhi-payment-request-update',
    templateUrl: './payment-request-update.component.html'
})
export class PaymentRequestUpdateComponent implements OnInit {
    paymentRequest: IPaymentRequest;
    isSaving: boolean;

    members: IMember[];

    paymentgroups: IPaymentGroup[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private paymentRequestService: PaymentRequestService,
        private memberService: MemberService,
        private paymentGroupService: PaymentGroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentRequest }) => {
            this.paymentRequest = paymentRequest;
        });
        this.memberService.query().subscribe(
            (res: HttpResponse<IMember[]>) => {
                this.members = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentGroupService.query().subscribe(
            (res: HttpResponse<IPaymentGroup[]>) => {
                this.paymentgroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentRequest.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentRequestService.update(this.paymentRequest));
        } else {
            this.subscribeToSaveResponse(this.paymentRequestService.create(this.paymentRequest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentRequest>>) {
        result.subscribe((res: HttpResponse<IPaymentRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMemberById(index: number, item: IMember) {
        return item.id;
    }

    trackPaymentGroupById(index: number, item: IPaymentGroup) {
        return item.id;
    }
}
