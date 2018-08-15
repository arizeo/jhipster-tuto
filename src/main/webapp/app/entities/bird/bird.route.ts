import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bird } from 'app/shared/model/bird.model';
import { BirdService } from './bird.service';
import { BirdComponent } from './bird.component';
import { BirdDetailComponent } from './bird-detail.component';
import { BirdUpdateComponent } from './bird-update.component';
import { BirdDeletePopupComponent } from './bird-delete-dialog.component';
import { IBird } from 'app/shared/model/bird.model';

@Injectable({ providedIn: 'root' })
export class BirdResolve implements Resolve<IBird> {
    constructor(private service: BirdService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bird: HttpResponse<Bird>) => bird.body));
        }
        return of(new Bird());
    }
}

export const birdRoute: Routes = [
    {
        path: 'bird',
        component: BirdComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTutoApp.bird.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bird/:id/view',
        component: BirdDetailComponent,
        resolve: {
            bird: BirdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.bird.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bird/new',
        component: BirdUpdateComponent,
        resolve: {
            bird: BirdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.bird.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bird/:id/edit',
        component: BirdUpdateComponent,
        resolve: {
            bird: BirdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.bird.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const birdPopupRoute: Routes = [
    {
        path: 'bird/:id/delete',
        component: BirdDeletePopupComponent,
        resolve: {
            bird: BirdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.bird.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
