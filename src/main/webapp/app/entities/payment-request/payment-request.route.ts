import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentRequest } from 'app/shared/model/payment-request.model';
import { PaymentRequestService } from './payment-request.service';
import { PaymentRequestComponent } from './payment-request.component';
import { PaymentRequestDetailComponent } from './payment-request-detail.component';
import { PaymentRequestUpdateComponent } from './payment-request-update.component';
import { PaymentRequestDeletePopupComponent } from './payment-request-delete-dialog.component';
import { IPaymentRequest } from 'app/shared/model/payment-request.model';

@Injectable({ providedIn: 'root' })
export class PaymentRequestResolve implements Resolve<IPaymentRequest> {
    constructor(private service: PaymentRequestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((paymentRequest: HttpResponse<PaymentRequest>) => paymentRequest.body));
        }
        return of(new PaymentRequest());
    }
}

export const paymentRequestRoute: Routes = [
    {
        path: 'payment-request',
        component: PaymentRequestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-request/:id/view',
        component: PaymentRequestDetailComponent,
        resolve: {
            paymentRequest: PaymentRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-request/new',
        component: PaymentRequestUpdateComponent,
        resolve: {
            paymentRequest: PaymentRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-request/:id/edit',
        component: PaymentRequestUpdateComponent,
        resolve: {
            paymentRequest: PaymentRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentRequestPopupRoute: Routes = [
    {
        path: 'payment-request/:id/delete',
        component: PaymentRequestDeletePopupComponent,
        resolve: {
            paymentRequest: PaymentRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentRequest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
