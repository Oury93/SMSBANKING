import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { GroupeBancaire } from './groupe-bancaire.model';
import { GroupeBancairePopupService } from './groupe-bancaire-popup.service';
import { GroupeBancaireService } from './groupe-bancaire.service';

@Component({
    selector: 'jhi-groupe-bancaire-dialog',
    templateUrl: './groupe-bancaire-dialog.component.html'
})
export class GroupeBancaireDialogComponent implements OnInit {

    groupeBancaire: GroupeBancaire;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private groupeBancaireService: GroupeBancaireService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['groupeBancaire']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.groupeBancaire.id !== undefined) {
            this.groupeBancaireService.update(this.groupeBancaire)
                .subscribe((res: GroupeBancaire) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.groupeBancaireService.create(this.groupeBancaire)
                .subscribe((res: GroupeBancaire) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: GroupeBancaire) {
        this.eventManager.broadcast({ name: 'groupeBancaireListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-groupe-bancaire-popup',
    template: ''
})
export class GroupeBancairePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private groupeBancairePopupService: GroupeBancairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.groupeBancairePopupService
                    .open(GroupeBancaireDialogComponent, params['id']);
            } else {
                this.modalRef = this.groupeBancairePopupService
                    .open(GroupeBancaireDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
