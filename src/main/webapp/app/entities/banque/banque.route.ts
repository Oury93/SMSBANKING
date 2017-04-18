import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BanqueComponent } from './banque.component';
import { BanqueDetailComponent } from './banque-detail.component';
import { BanquePopupComponent } from './banque-dialog.component';
import { BanqueDeletePopupComponent } from './banque-delete-dialog.component';

import { Principal } from '../../shared';


export const banqueRoute: Routes = [
  {
    path: 'banque',
    component: BanqueComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.banque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'banque/:id',
    component: BanqueDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.banque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const banquePopupRoute: Routes = [
  {
    path: 'banque-new',
    component: BanquePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.banque.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'banque/:id/edit',
    component: BanquePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.banque.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'banque/:id/delete',
    component: BanqueDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'smsbankingApp.banque.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
