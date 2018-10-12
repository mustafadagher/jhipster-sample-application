import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentGroup } from 'app/shared/model/payment-group.model';

type EntityResponseType = HttpResponse<IPaymentGroup>;
type EntityArrayResponseType = HttpResponse<IPaymentGroup[]>;

@Injectable({ providedIn: 'root' })
export class PaymentGroupService {
    private resourceUrl = SERVER_API_URL + 'api/payment-groups';

    constructor(private http: HttpClient) {}

    create(paymentGroup: IPaymentGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentGroup);
        return this.http
            .post<IPaymentGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(paymentGroup: IPaymentGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentGroup);
        return this.http
            .put<IPaymentGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPaymentGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPaymentGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(paymentGroup: IPaymentGroup): IPaymentGroup {
        const copy: IPaymentGroup = Object.assign({}, paymentGroup, {
            startDate: paymentGroup.startDate != null && paymentGroup.startDate.isValid() ? paymentGroup.startDate.toJSON() : null,
            endDate: paymentGroup.endDate != null && paymentGroup.endDate.isValid() ? paymentGroup.endDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((paymentGroup: IPaymentGroup) => {
            paymentGroup.startDate = paymentGroup.startDate != null ? moment(paymentGroup.startDate) : null;
            paymentGroup.endDate = paymentGroup.endDate != null ? moment(paymentGroup.endDate) : null;
        });
        return res;
    }
}
