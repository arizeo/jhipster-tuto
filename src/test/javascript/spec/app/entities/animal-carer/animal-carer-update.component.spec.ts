/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { AnimalCarerUpdateComponent } from 'app/entities/animal-carer/animal-carer-update.component';
import { AnimalCarerService } from 'app/entities/animal-carer/animal-carer.service';
import { AnimalCarer } from 'app/shared/model/animal-carer.model';

describe('Component Tests', () => {
    describe('AnimalCarer Management Update Component', () => {
        let comp: AnimalCarerUpdateComponent;
        let fixture: ComponentFixture<AnimalCarerUpdateComponent>;
        let service: AnimalCarerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [AnimalCarerUpdateComponent]
            })
                .overrideTemplate(AnimalCarerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnimalCarerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnimalCarerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AnimalCarer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.animalCarer = entity;
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
                    const entity = new AnimalCarer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.animalCarer = entity;
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
