/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTutoTestModule } from '../../../test.module';
import { ReptileDetailComponent } from 'app/entities/reptile/reptile-detail.component';
import { Reptile } from 'app/shared/model/reptile.model';

describe('Component Tests', () => {
    describe('Reptile Management Detail Component', () => {
        let comp: ReptileDetailComponent;
        let fixture: ComponentFixture<ReptileDetailComponent>;
        const route = ({ data: of({ reptile: new Reptile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTutoTestModule],
                declarations: [ReptileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReptileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReptileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reptile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
