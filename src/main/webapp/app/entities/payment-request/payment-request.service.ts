import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentRequest } from 'app/shared/model/payment-request.model';

type EntityResponseType = HttpResponse<IPaymentRequest>;
type EntityArrayResponseType = HttpResponse<IPaymentRequest[]>;

@Injectable({ providedIn: 'root' })
export class PaymentRequestService {
    private resourceUrl = SERVER_API_URL + 'api/payment-requests';

    constructor(private http: HttpClient) {}

    create(paymentRequest: IPaymentRequest): Observable<EntityResponseType> {
        return this.http.post<IPaymentRequest>(this.resourceUrl, paymentRequest, { observe: 'response' });
    }

    update(paymentRequest: IPaymentRequest): Observable<EntityResponseType> {
        return this.http.put<IPaymentRequest>(this.resourceUrl, paymentRequest, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPaymentRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPaymentRequest[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
