import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTutoSharedModule } from 'app/shared';
import {
    AnimalCarerComponent,
    AnimalCarerDetailComponent,
    AnimalCarerUpdateComponent,
    AnimalCarerDeletePopupComponent,
    AnimalCarerDeleteDialogComponent,
    animalCarerRoute,
    animalCarerPopupRoute
} from './';

const ENTITY_STATES = [...animalCarerRoute, ...animalCarerPopupRoute];

@NgModule({
    imports: [JhipsterTutoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnimalCarerComponent,
        AnimalCarerDetailComponent,
        AnimalCarerUpdateComponent,
        AnimalCarerDeleteDialogComponent,
        AnimalCarerDeletePopupComponent
    ],
    entryComponents: [AnimalCarerComponent, AnimalCarerUpdateComponent, AnimalCarerDeleteDialogComponent, AnimalCarerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoAnimalCarerModule {}
