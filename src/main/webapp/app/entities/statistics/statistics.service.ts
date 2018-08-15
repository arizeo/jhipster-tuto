import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStatistics } from 'app/shared/model/statistics.model';

type EntityResponseType = HttpResponse<IStatistics>;
type EntityArrayResponseType = HttpResponse<IStatistics[]>;

@Injectable({ providedIn: 'root' })
export class StatisticsService {
    private resourceUrl = SERVER_API_URL + 'api/statistics';

    constructor(private http: HttpClient) {}

    create(statistics: IStatistics): Observable<EntityResponseType> {
        return this.http.post<IStatistics>(this.resourceUrl, statistics, { observe: 'response' });
    }

    update(statistics: IStatistics): Observable<EntityResponseType> {
        return this.http.put<IStatistics>(this.resourceUrl, statistics, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStatistics>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStatistics[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
