import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReptile } from 'app/shared/model/reptile.model';

type EntityResponseType = HttpResponse<IReptile>;
type EntityArrayResponseType = HttpResponse<IReptile[]>;

@Injectable({ providedIn: 'root' })
export class ReptileService {
    private resourceUrl = SERVER_API_URL + 'api/reptiles';

    constructor(private http: HttpClient) {}

    create(reptile: IReptile): Observable<EntityResponseType> {
        return this.http.post<IReptile>(this.resourceUrl, reptile, { observe: 'response' });
    }

    update(reptile: IReptile): Observable<EntityResponseType> {
        return this.http.put<IReptile>(this.resourceUrl, reptile, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReptile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReptile[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
