import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHabitat } from 'app/shared/model/habitat.model';

type EntityResponseType = HttpResponse<IHabitat>;
type EntityArrayResponseType = HttpResponse<IHabitat[]>;

@Injectable({ providedIn: 'root' })
export class HabitatService {
    private resourceUrl = SERVER_API_URL + 'api/habitats';

    constructor(private http: HttpClient) {}

    create(habitat: IHabitat): Observable<EntityResponseType> {
        return this.http.post<IHabitat>(this.resourceUrl, habitat, { observe: 'response' });
    }

    update(habitat: IHabitat): Observable<EntityResponseType> {
        return this.http.put<IHabitat>(this.resourceUrl, habitat, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHabitat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHabitat[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
