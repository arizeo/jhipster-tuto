import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTutoSharedModule } from 'app/shared';
import {
    HabitatComponent,
    HabitatDetailComponent,
    HabitatUpdateComponent,
    HabitatDeletePopupComponent,
    HabitatDeleteDialogComponent,
    habitatRoute,
    habitatPopupRoute
} from './';

const ENTITY_STATES = [...habitatRoute, ...habitatPopupRoute];

@NgModule({
    imports: [JhipsterTutoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HabitatComponent,
        HabitatDetailComponent,
        HabitatUpdateComponent,
        HabitatDeleteDialogComponent,
        HabitatDeletePopupComponent
    ],
    entryComponents: [HabitatComponent, HabitatUpdateComponent, HabitatDeleteDialogComponent, HabitatDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoHabitatModule {}
