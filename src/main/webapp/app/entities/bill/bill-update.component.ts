import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IBill } from 'app/shared/model/bill.model';
import { BillService } from './bill.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member';
import { IPaymentGroup } from 'app/shared/model/payment-group.model';
import { PaymentGroupService } from 'app/entities/payment-group';

@Component({
    selector: 'jhi-bill-update',
    templateUrl: './bill-update.component.html'
})
export class BillUpdateComponent implements OnInit {
    bill: IBill;
    isSaving: boolean;

    members: IMember[];

    paymentgroups: IPaymentGroup[];
    paymentDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private billService: BillService,
        private memberService: MemberService,
        private paymentGroupService: PaymentGroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bill }) => {
            this.bill = bill;
            this.paymentDate = this.bill.paymentDate != null ? this.bill.paymentDate.format(DATE_TIME_FORMAT) : null;
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
        this.bill.paymentDate = this.paymentDate != null ? moment(this.paymentDate, DATE_TIME_FORMAT) : null;
        if (this.bill.id !== undefined) {
            this.subscribeToSaveResponse(this.billService.update(this.bill));
        } else {
            this.subscribeToSaveResponse(this.billService.create(this.bill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBill>>) {
        result.subscribe((res: HttpResponse<IBill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
