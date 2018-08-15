import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTutoSharedModule } from 'app/shared';
import {
    ReptileComponent,
    ReptileDetailComponent,
    ReptileUpdateComponent,
    ReptileDeletePopupComponent,
    ReptileDeleteDialogComponent,
    reptileRoute,
    reptilePopupRoute
} from './';

const ENTITY_STATES = [...reptileRoute, ...reptilePopupRoute];

@NgModule({
    imports: [JhipsterTutoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReptileComponent,
        ReptileDetailComponent,
        ReptileUpdateComponent,
        ReptileDeleteDialogComponent,
        ReptileDeletePopupComponent
    ],
    entryComponents: [ReptileComponent, ReptileUpdateComponent, ReptileDeleteDialogComponent, ReptileDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoReptileModule {}
