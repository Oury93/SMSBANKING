import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GroupeBancaireComponent } from './groupe-bancaire.component';
import { GroupeBancaireDetailComponent } from './groupe-bancaire-detail.component';
import { GroupeBancairePopupComponent } from './groupe-bancaire-dialog.component';
import { GroupeBancaireDeletePopupComponent } from './groupe-bancaire-delete-dialog.component';

import { Principal } from '../../shared';


export const groupeBancaireRoute: Routes = [
  {
    path: 'groupe-bancaire',
    component: GroupeBancaireComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.groupeBancaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'groupe-bancaire/:id',
    component: GroupeBancaireDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.groupeBancaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const groupeBancairePopupRoute: Routes = [
  {
    path: 'groupe-bancaire-new',
    component: GroupeBancairePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.groupeBancaire.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'groupe-bancaire/:id/edit',
    component: GroupeBancairePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.groupeBancaire.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'groupe-bancaire/:id/delete',
    component: GroupeBancaireDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.groupeBancaire.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
