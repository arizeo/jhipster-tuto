/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { AnimalCarerDetailComponent } from 'app/entities/animal-carer/animal-carer-detail.component';
import { AnimalCarer } from 'app/shared/model/animal-carer.model';

describe('Component Tests', () => {
    describe('AnimalCarer Management Detail Component', () => {
        let comp: AnimalCarerDetailComponent;
        let fixture: ComponentFixture<AnimalCarerDetailComponent>;
        const route = ({ data: of({ animalCarer: new AnimalCarer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [AnimalCarerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnimalCarerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnimalCarerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.animalCarer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
