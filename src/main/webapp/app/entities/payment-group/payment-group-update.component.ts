import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPaymentGroup } from 'app/shared/model/payment-group.model';
import { PaymentGroupService } from './payment-group.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member';

@Component({
    selector: 'jhi-payment-group-update',
    templateUrl: './payment-group-update.component.html'
})
export class PaymentGroupUpdateComponent implements OnInit {
    paymentGroup: IPaymentGroup;
    isSaving: boolean;

    members: IMember[];
    startDate: string;
    endDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private paymentGroupService: PaymentGroupService,
        private memberService: MemberService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentGroup }) => {
            this.paymentGroup = paymentGroup;
            this.startDate = this.paymentGroup.startDate != null ? this.paymentGroup.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.paymentGroup.endDate != null ? this.paymentGroup.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.memberService.query().subscribe(
            (res: HttpResponse<IMember[]>) => {
                this.members = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.paymentGroup.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.paymentGroup.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.paymentGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentGroupService.update(this.paymentGroup));
        } else {
            this.subscribeToSaveResponse(this.paymentGroupService.create(this.paymentGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentGroup>>) {
        result.subscribe((res: HttpResponse<IPaymentGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
