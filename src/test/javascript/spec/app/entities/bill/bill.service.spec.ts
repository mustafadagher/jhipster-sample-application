/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BillService } from 'app/entities/bill/bill.service';
import { IBill, Bill } from 'app/shared/model/bill.model';

describe('Service Tests', () => {
    describe('Bill Service', () => {
        let injector: TestBed;
        let service: BillService;
        let httpMock: HttpTestingController;
        let elemDefault: IBill;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BillService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Bill(0, 'AAAAAAA', 0, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        paymentDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Bill', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        paymentDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        paymentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Bill(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Bill', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        amount: 1,
                        paymentDate: currentDate.format(DATE_TIME_FORMAT),
                        mutationId: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        paymentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Bill', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        amount: 1,
                        paymentDate: currentDate.format(DATE_TIME_FORMAT),
                        mutationId: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        paymentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Bill', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
