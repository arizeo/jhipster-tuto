import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterTutoAnimalModule } from './animal/animal.module';
import { JhipsterTutoBirdModule } from './bird/bird.module';
import { JhipsterTutoReptileModule } from './reptile/reptile.module';
import { JhipsterTutoStatisticsModule } from './statistics/statistics.module';
import { JhipsterTutoAnimalCarerModule } from './animal-carer/animal-carer.module';
import { JhipsterTutoHabitatModule } from './habitat/habitat.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterTutoAnimalModule,
        JhipsterTutoBirdModule,
        JhipsterTutoReptileModule,
        JhipsterTutoStatisticsModule,
        JhipsterTutoAnimalCarerModule,
        JhipsterTutoHabitatModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTutoEntityModule {}
