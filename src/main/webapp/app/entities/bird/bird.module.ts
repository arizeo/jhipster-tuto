import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTutoSharedModule } from 'app/shared';
import {
    BirdComponent,
    BirdDetailComponent,
    BirdUpdateComponent,
    BirdDeletePopupComponent,
    BirdDeleteDialogComponent,
    birdRoute,
    birdPopupRoute
} from './';

const ENTITY_STATES = [...birdRoute, ...birdPopupRoute];

@NgModule({
    imports: [JhipsterTutoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BirdComponent, BirdDetailComponent, BirdUpdateComponent, BirdDeleteDialogComponent, BirdDeletePopupComponent],
    entryComponents: [BirdComponent, BirdUpdateComponent, BirdDeleteDialogComponent, BirdDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoBirdModule {}
