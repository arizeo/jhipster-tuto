/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTutoTestModule } from '../../../test.module';
import { StatisticsDeleteDialogComponent } from 'app/entities/statistics/statistics-delete-dialog.component';
import { StatisticsService } from 'app/entities/statistics/statistics.service';

describe('Component Tests', () => {
    describe('Statistics Management Delete Component', () => {
        let comp: StatisticsDeleteDialogComponent;
        let fixture: ComponentFixture<StatisticsDeleteDialogComponent>;
        let service: StatisticsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [StatisticsDeleteDialogComponent]
            })
                .overrideTemplate(StatisticsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StatisticsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticsService);
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
