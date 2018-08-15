import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Statistics } from 'app/shared/model/statistics.model';
import { StatisticsService } from './statistics.service';
import { StatisticsComponent } from './statistics.component';
import { StatisticsDetailComponent } from './statistics-detail.component';
import { StatisticsUpdateComponent } from './statistics-update.component';
import { StatisticsDeletePopupComponent } from './statistics-delete-dialog.component';
import { IStatistics } from 'app/shared/model/statistics.model';

@Injectable({ providedIn: 'root' })
export class StatisticsResolve implements Resolve<IStatistics> {
    constructor(private service: StatisticsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((statistics: HttpResponse<Statistics>) => statistics.body));
        }
        return of(new Statistics());
    }
}

export const statisticsRoute: Routes = [
    {
        path: 'statistics',
        component: StatisticsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTutoApp.statistics.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'statistics/:id/view',
        component: StatisticsDetailComponent,
        resolve: {
            statistics: StatisticsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.statistics.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'statistics/new',
        component: StatisticsUpdateComponent,
        resolve: {
            statistics: StatisticsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.statistics.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'statistics/:id/edit',
        component: StatisticsUpdateComponent,
        resolve: {
            statistics: StatisticsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.statistics.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statisticsPopupRoute: Routes = [
    {
        path: 'statistics/:id/delete',
        component: StatisticsDeletePopupComponent,
        resolve: {
            statistics: StatisticsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTutoApp.statistics.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
