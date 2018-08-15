import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalCarer } from 'app/shared/model/animal-carer.model';
import { AnimalCarerService } from './animal-carer.service';
import { AnimalCarerComponent } from './animal-carer.component';
import { AnimalCarerDetailComponent } from './animal-carer-detail.component';
import { AnimalCarerUpdateComponent } from './animal-carer-update.component';
import { AnimalCarerDeletePopupComponent } from './animal-carer-delete-dialog.component';
import { IAnimalCarer } from 'app/shared/model/animal-carer.model';

@Injectable({ providedIn: 'root' })
export class AnimalCarerResolve implements Resolve<IAnimalCarer> {
    constructor(private service: AnimalCarerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((animalCarer: HttpResponse<AnimalCarer>) => animalCarer.body));
        }
        return of(new AnimalCarer());
    }
}

export const animalCarerRoute: Routes = [
    {
        path: 'animal-carer',
        component: AnimalCarerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTutoApp.animalCarer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'animal-carer/:id/view',
        component: AnimalCarerDetailComponent,
        resolve: {
            animalCarer: AnimalCarerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.animalCarer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'animal-carer/new',
        component: AnimalCarerUpdateComponent,
        resolve: {
            animalCarer: AnimalCarerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.animalCarer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'animal-carer/:id/edit',
        component: AnimalCarerUpdateComponent,
        resolve: {
            animalCarer: AnimalCarerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.animalCarer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const animalCarerPopupRoute: Routes = [
    {
        path: 'animal-carer/:id/delete',
        component: AnimalCarerDeletePopupComponent,
        resolve: {
            animalCarer: AnimalCarerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.animalCarer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
