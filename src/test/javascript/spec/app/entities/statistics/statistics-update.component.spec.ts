/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { StatisticsUpdateComponent } from 'app/entities/statistics/statistics-update.component';
import { StatisticsService } from 'app/entities/statistics/statistics.service';
import { Statistics } from 'app/shared/model/statistics.model';

describe('Component Tests', () => {
    describe('Statistics Management Update Component', () => {
        let comp: StatisticsUpdateComponent;
        let fixture: ComponentFixture<StatisticsUpdateComponent>;
        let service: StatisticsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [StatisticsUpdateComponent]
            })
                .overrideTemplate(StatisticsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StatisticsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Statistics(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.statistics = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Statistics();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.statistics = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
