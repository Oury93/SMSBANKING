import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SmsbankingTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GroupeBancaireDetailComponent } from '../../../../../../main/webapp/app/entities/groupe-bancaire/groupe-bancaire-detail.component';
import { GroupeBancaireService } from '../../../../../../main/webapp/app/entities/groupe-bancaire/groupe-bancaire.service';
import { GroupeBancaire } from '../../../../../../main/webapp/app/entities/groupe-bancaire/groupe-bancaire.model';

describe('Component Tests', () => {

    describe('GroupeBancaire Management Detail Component', () => {
        let comp: GroupeBancaireDetailComponent;
        let fixture: ComponentFixture<GroupeBancaireDetailComponent>;
        let service: GroupeBancaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SmsbankingTestModule],
                declarations: [GroupeBancaireDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GroupeBancaireService,
                    EventManager
                ]
            }).overrideComponent(GroupeBancaireDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupeBancaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupeBancaireService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GroupeBancaire(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.groupeBancaire).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
