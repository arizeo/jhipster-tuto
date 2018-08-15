/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { ReptileUpdateComponent } from 'app/entities/reptile/reptile-update.component';
import { ReptileService } from 'app/entities/reptile/reptile.service';
import { Reptile } from 'app/shared/model/reptile.model';

describe('Component Tests', () => {
    describe('Reptile Management Update Component', () => {
        let comp: ReptileUpdateComponent;
        let fixture: ComponentFixture<ReptileUpdateComponent>;
        let service: ReptileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [ReptileUpdateComponent]
            })
                .overrideTemplate(ReptileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReptileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReptileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Reptile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reptile = entity;
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
                    const entity = new Reptile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reptile = entity;
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
