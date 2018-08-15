/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { BirdDetailComponent } from 'app/entities/bird/bird-detail.component';
import { Bird } from 'app/shared/model/bird.model';

describe('Component Tests', () => {
    describe('Bird Management Detail Component', () => {
        let comp: BirdDetailComponent;
        let fixture: ComponentFixture<BirdDetailComponent>;
        const route = ({ data: of({ bird: new Bird(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [BirdDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BirdDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BirdDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bird).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
