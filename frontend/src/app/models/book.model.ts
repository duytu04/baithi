export interface Apbp {
    // Book fields
    bookId?: number;
    title: string;
    isbn: string;
    publishedYear: number;
    pages: number;

    // Author fields
    authorId?: number;
    authorName: string;
    nationality: string;

    // Author Profile fields
    biography: string;
    website: string;

    // Publisher fields
    publisherId: number;
    publisherName?: string;
}

export interface Publisher {
    id: number;
    name: string;
    address?: string;
    website?: string;
    email?: string;
    phone?: string;
}

export interface ApbpCreateRequest {
    title: string;
    isbn: string;
    publishedYear: number;
    pages: number;
    authorName: string;
    nationality: string;
    biography: string;
    website: string;
    publisherId: number;
}

// ======== NEW STANDALONE INTERFACES ========

export interface Author {
    id: number;
    fullName: string;
    nationality: string;
    biography?: string;
    website?: string;
}

export interface Book {
    id: number;
    title: string;
    isbn: string;
    publishedYear: number;
    pages: number;
    publisherId: number;
    publisherName?: string;
    authorIds?: number[];
    authorNames?: string[];
}

export interface BookCreateRequest {
    title: string;
    isbn: string;
    publishedYear: number;
    pages: number;
    publisherId: number;
    authorIds: number[];
}

