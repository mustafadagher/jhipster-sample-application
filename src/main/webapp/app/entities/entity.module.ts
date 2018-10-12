import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationMemberModule } from './member/member.module';
import { JhipsterSampleApplicationPaymentGroupModule } from './payment-group/payment-group.module';
import { JhipsterSampleApplicationBillModule } from './bill/bill.module';
import { JhipsterSampleApplicationPaymentRequestModule } from './payment-request/payment-request.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationMemberModule,
        JhipsterSampleApplicationPaymentGroupModule,
        JhipsterSampleApplicationBillModule,
        JhipsterSampleApplicationPaymentRequestModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
