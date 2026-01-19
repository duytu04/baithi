import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Apbp, ApbpCreateRequest } from '../models/book.model';

@Injectable({
    providedIn: 'root'
})
export class BookService {
    private apiUrl = '/api/apbp';

    constructor(private http: HttpClient) { }

    /**
     * Get all books with their authors and publishers
     */
    getAll(): Observable<Apbp[]> {
        return this.http.get<Apbp[]>(this.apiUrl).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Get a single book by ID
     */
    getById(bookId: number): Observable<Apbp> {
        return this.http.get<Apbp>(`${this.apiUrl}/${bookId}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Create a new book with author and profile
     */
    create(request: ApbpCreateRequest): Observable<Apbp> {
        return this.http.post<Apbp>(this.apiUrl, request).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Update an existing book
     */
    update(bookId: number, request: ApbpCreateRequest): Observable<Apbp> {
        return this.http.put<Apbp>(`${this.apiUrl}/${bookId}`, request).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Delete a book
     */
    delete(bookId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${bookId}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Handle HTTP errors
     */
    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'Đã xảy ra lỗi';

        if (error.error instanceof ErrorEvent) {
            // Client-side error
            errorMessage = `Lỗi: ${error.error.message}`;
        } else {
            // Server-side error
            errorMessage = `Lỗi ${error.status}: ${error.message}`;
            if (error.error?.message) {
                errorMessage = error.error.message;
            }
        }

        console.error(errorMessage);
        return throwError(() => new Error(errorMessage));
    }
}
