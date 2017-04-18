import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Banque } from './banque.model';
import { BanquePopupService } from './banque-popup.service';
import { BanqueService } from './banque.service';
import { GroupeBancaire, GroupeBancaireService } from '../groupe-bancaire';

@Component({
    selector: 'jhi-banque-dialog',
    templateUrl: './banque-dialog.component.html'
})
export class BanqueDialogComponent implements OnInit {

    banque: Banque;
    authorities: any[];
    isSaving: boolean;

    groupebancaires: GroupeBancaire[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private banqueService: BanqueService,
        private groupeBancaireService: GroupeBancaireService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['banque']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.groupeBancaireService.query().subscribe(
            (res: Response) => { this.groupebancaires = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.banque.id !== undefined) {
            this.banqueService.update(this.banque)
                .subscribe((res: Banque) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.banqueService.create(this.banque)
                .subscribe((res: Banque) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Banque) {
        this.eventManager.broadcast({ name: 'banqueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackGroupeBancaireById(index: number, item: GroupeBancaire) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-banque-popup',
    template: ''
})
export class BanquePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private banquePopupService: BanquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.banquePopupService
                    .open(BanqueDialogComponent, params['id']);
            } else {
                this.modalRef = this.banquePopupService
                    .open(BanqueDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
