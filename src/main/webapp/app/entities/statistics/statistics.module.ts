import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTutoSharedModule } from 'app/shared';
import {
    StatisticsComponent,
    StatisticsDetailComponent,
    StatisticsUpdateComponent,
    StatisticsDeletePopupComponent,
    StatisticsDeleteDialogComponent,
    statisticsRoute,
    statisticsPopupRoute
} from './';

const ENTITY_STATES = [...statisticsRoute, ...statisticsPopupRoute];

@NgModule({
    imports: [JhipsterTutoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StatisticsComponent,
        StatisticsDetailComponent,
        StatisticsUpdateComponent,
        StatisticsDeleteDialogComponent,
        StatisticsDeletePopupComponent
    ],
    entryComponents: [StatisticsComponent, StatisticsUpdateComponent, StatisticsDeleteDialogComponent, StatisticsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoStatisticsModule {}
