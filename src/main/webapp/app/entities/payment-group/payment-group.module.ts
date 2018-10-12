import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    PaymentGroupComponent,
    PaymentGroupDetailComponent,
    PaymentGroupUpdateComponent,
    PaymentGroupDeletePopupComponent,
    PaymentGroupDeleteDialogComponent,
    paymentGroupRoute,
    paymentGroupPopupRoute
} from './';

const ENTITY_STATES = [...paymentGroupRoute, ...paymentGroupPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentGroupComponent,
        PaymentGroupDetailComponent,
        PaymentGroupUpdateComponent,
        PaymentGroupDeleteDialogComponent,
        PaymentGroupDeletePopupComponent
    ],
    entryComponents: [
        PaymentGroupComponent,
        PaymentGroupUpdateComponent,
        PaymentGroupDeleteDialogComponent,
        PaymentGroupDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPaymentGroupModule {}
