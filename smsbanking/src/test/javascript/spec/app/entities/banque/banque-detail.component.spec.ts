import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SmsbankingTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BanqueDetailComponent } from '../../../../../../main/webapp/app/entities/banque/banque-detail.component';
import { BanqueService } from '../../../../../../main/webapp/app/entities/banque/banque.service';
import { Banque } from '../../../../../../main/webapp/app/entities/banque/banque.model';

describe('Component Tests', () => {

    describe('Banque Management Detail Component', () => {
        let comp: BanqueDetailComponent;
        let fixture: ComponentFixture<BanqueDetailComponent>;
        let service: BanqueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmsbankingTestModule],
                declarations: [BanqueDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BanqueService,
                    EventManager
                ]
            }).overrideComponent(BanqueDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BanqueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BanqueService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Banque(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.banque).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
