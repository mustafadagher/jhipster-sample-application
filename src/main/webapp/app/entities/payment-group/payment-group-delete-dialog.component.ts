import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentGroup } from 'app/shared/model/payment-group.model';
import { PaymentGroupService } from './payment-group.service';

@Component({
    selector: 'jhi-payment-group-delete-dialog',
    templateUrl: './payment-group-delete-dialog.component.html'
})
export class PaymentGroupDeleteDialogComponent {
    paymentGroup: IPaymentGroup;

    constructor(
        private paymentGroupService: PaymentGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paymentGroupListModification',
                content: 'Deleted an paymentGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payment-group-delete-popup',
    template: ''
})
export class PaymentGroupDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaymentGroupDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.paymentGroup = paymentGroup;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
