/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { StatisticsDetailComponent } from 'app/entities/statistics/statistics-detail.component';
import { Statistics } from 'app/shared/model/statistics.model';

describe('Component Tests', () => {
    describe('Statistics Management Detail Component', () => {
        let comp: StatisticsDetailComponent;
        let fixture: ComponentFixture<StatisticsDetailComponent>;
        const route = ({ data: of({ statistics: new Statistics(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [StatisticsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StatisticsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StatisticsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.statistics).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
