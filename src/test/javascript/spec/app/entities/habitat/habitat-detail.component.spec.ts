/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { HabitatDetailComponent } from 'app/entities/habitat/habitat-detail.component';
import { Habitat } from 'app/shared/model/habitat.model';

describe('Component Tests', () => {
    describe('Habitat Management Detail Component', () => {
        let comp: HabitatDetailComponent;
        let fixture: ComponentFixture<HabitatDetailComponent>;
        const route = ({ data: of({ habitat: new Habitat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [HabitatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HabitatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HabitatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.habitat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
