import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentGroup } from 'app/shared/model/payment-group.model';
import { PaymentGroupService } from './payment-group.service';
import { PaymentGroupComponent } from './payment-group.component';
import { PaymentGroupDetailComponent } from './payment-group-detail.component';
import { PaymentGroupUpdateComponent } from './payment-group-update.component';
import { PaymentGroupDeletePopupComponent } from './payment-group-delete-dialog.component';
import { IPaymentGroup } from 'app/shared/model/payment-group.model';

@Injectable({ providedIn: 'root' })
export class PaymentGroupResolve implements Resolve<IPaymentGroup> {
    constructor(private service: PaymentGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((paymentGroup: HttpResponse<PaymentGroup>) => paymentGroup.body));
        }
        return of(new PaymentGroup());
    }
}

export const paymentGroupRoute: Routes = [
    {
        path: 'payment-group',
        component: PaymentGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-group/:id/view',
        component: PaymentGroupDetailComponent,
        resolve: {
            paymentGroup: PaymentGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-group/new',
        component: PaymentGroupUpdateComponent,
        resolve: {
            paymentGroup: PaymentGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-group/:id/edit',
        component: PaymentGroupUpdateComponent,
        resolve: {
            paymentGroup: PaymentGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentGroupPopupRoute: Routes = [
    {
        path: 'payment-group/:id/delete',
        component: PaymentGroupDeletePopupComponent,
        resolve: {
            paymentGroup: PaymentGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.paymentGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
