/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTutoTestModule } from '../../../test.module';
import { HabitatDeleteDialogComponent } from 'app/entities/habitat/habitat-delete-dialog.component';
import { HabitatService } from 'app/entities/habitat/habitat.service';

describe('Component Tests', () => {
    describe('Habitat Management Delete Component', () => {
        let comp: HabitatDeleteDialogComponent;
        let fixture: ComponentFixture<HabitatDeleteDialogComponent>;
        let service: HabitatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [HabitatDeleteDialogComponent]
            })
                .overrideTemplate(HabitatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HabitatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HabitatService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
