import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Habitat } from 'app/shared/model/habitat.model';
import { HabitatService } from './habitat.service';
import { HabitatComponent } from './habitat.component';
import { HabitatDetailComponent } from './habitat-detail.component';
import { HabitatUpdateComponent } from './habitat-update.component';
import { HabitatDeletePopupComponent } from './habitat-delete-dialog.component';
import { IHabitat } from 'app/shared/model/habitat.model';

@Injectable({ providedIn: 'root' })
export class HabitatResolve implements Resolve<IHabitat> {
    constructor(private service: HabitatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((habitat: HttpResponse<Habitat>) => habitat.body));
        }
        return of(new Habitat());
    }
}

export const habitatRoute: Routes = [
    {
        path: 'habitat',
        component: HabitatComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTutoApp.habitat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'habitat/:id/view',
        component: HabitatDetailComponent,
        resolve: {
            habitat: HabitatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.habitat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'habitat/new',
        component: HabitatUpdateComponent,
        resolve: {
            habitat: HabitatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.habitat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'habitat/:id/edit',
        component: HabitatUpdateComponent,
        resolve: {
            habitat: HabitatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.habitat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const habitatPopupRoute: Routes = [
    {
        path: 'habitat/:id/delete',
        component: HabitatDeletePopupComponent,
        resolve: {
            habitat: HabitatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.habitat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
