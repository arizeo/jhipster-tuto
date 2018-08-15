import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Reptile } from 'app/shared/model/reptile.model';
import { ReptileService } from './reptile.service';
import { ReptileComponent } from './reptile.component';
import { ReptileDetailComponent } from './reptile-detail.component';
import { ReptileUpdateComponent } from './reptile-update.component';
import { ReptileDeletePopupComponent } from './reptile-delete-dialog.component';
import { IReptile } from 'app/shared/model/reptile.model';

@Injectable({ providedIn: 'root' })
export class ReptileResolve implements Resolve<IReptile> {
    constructor(private service: ReptileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reptile: HttpResponse<Reptile>) => reptile.body));
        }
        return of(new Reptile());
    }
}

export const reptileRoute: Routes = [
    {
        path: 'reptile',
        component: ReptileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTutoApp.reptile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reptile/:id/view',
        component: ReptileDetailComponent,
        resolve: {
            reptile: ReptileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.reptile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reptile/new',
        component: ReptileUpdateComponent,
        resolve: {
            reptile: ReptileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.reptile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reptile/:id/edit',
        component: ReptileUpdateComponent,
        resolve: {
            reptile: ReptileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.reptile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reptilePopupRoute: Routes = [
    {
        path: 'reptile/:id/delete',
        component: ReptileDeletePopupComponent,
        resolve: {
            reptile: ReptileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.reptile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
