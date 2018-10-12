/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentGroupDetailComponent } from 'app/entities/payment-group/payment-group-detail.component';
import { PaymentGroup } from 'app/shared/model/payment-group.model';

describe('Component Tests', () => {
    describe('PaymentGroup Management Detail Component', () => {
        let comp: PaymentGroupDetailComponent;
        let fixture: ComponentFixture<PaymentGroupDetailComponent>;
        const route = ({ data: of({ paymentGroup: new PaymentGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PaymentGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaymentGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paymentGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
