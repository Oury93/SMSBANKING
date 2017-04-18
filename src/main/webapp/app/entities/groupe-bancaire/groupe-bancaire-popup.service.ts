import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GroupeBancaire } from './groupe-bancaire.model';
import { GroupeBancaireService } from './groupe-bancaire.service';
@Injectable()
export class GroupeBancairePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private groupeBancaireService: GroupeBancaireService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.groupeBancaireService.find(id).subscribe(groupeBancaire => {
                this.groupeBancaireModalRef(component, groupeBancaire);
            });
        } else {
            return this.groupeBancaireModalRef(component, new GroupeBancaire());
        }
    }

    groupeBancaireModalRef(component: Component, groupeBancaire: GroupeBancaire): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.groupeBancaire = groupeBancaire;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
