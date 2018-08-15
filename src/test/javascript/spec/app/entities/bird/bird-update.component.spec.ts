/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { BirdUpdateComponent } from 'app/entities/bird/bird-update.component';
import { BirdService } from 'app/entities/bird/bird.service';
import { Bird } from 'app/shared/model/bird.model';

describe('Component Tests', () => {
    describe('Bird Management Update Component', () => {
        let comp: BirdUpdateComponent;
        let fixture: ComponentFixture<BirdUpdateComponent>;
        let service: BirdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [BirdUpdateComponent]
            })
                .overrideTemplate(BirdUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BirdUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BirdService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bird(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bird = entity;
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
                    const entity = new Bird();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bird = entity;
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
