/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { HabitatUpdateComponent } from 'app/entities/habitat/habitat-update.component';
import { HabitatService } from 'app/entities/habitat/habitat.service';
import { Habitat } from 'app/shared/model/habitat.model';

describe('Component Tests', () => {
    describe('Habitat Management Update Component', () => {
        let comp: HabitatUpdateComponent;
        let fixture: ComponentFixture<HabitatUpdateComponent>;
        let service: HabitatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [HabitatUpdateComponent]
            })
                .overrideTemplate(HabitatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HabitatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabitatService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Habitat(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.habitat = entity;
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
                    const entity = new Habitat();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.habitat = entity;
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
