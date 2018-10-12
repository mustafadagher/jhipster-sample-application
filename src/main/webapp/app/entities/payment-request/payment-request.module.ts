import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    PaymentRequestComponent,
    PaymentRequestDetailComponent,
    PaymentRequestUpdateComponent,
    PaymentRequestDeletePopupComponent,
    PaymentRequestDeleteDialogComponent,
    paymentRequestRoute,
    paymentRequestPopupRoute
} from './';

const ENTITY_STATES = [...paymentRequestRoute, ...paymentRequestPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentRequestComponent,
        PaymentRequestDetailComponent,
        PaymentRequestUpdateComponent,
        PaymentRequestDeleteDialogComponent,
        PaymentRequestDeletePopupComponent
    ],
    entryComponents: [
        PaymentRequestComponent,
        PaymentRequestUpdateComponent,
        PaymentRequestDeleteDialogComponent,
        PaymentRequestDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPaymentRequestModule {}
