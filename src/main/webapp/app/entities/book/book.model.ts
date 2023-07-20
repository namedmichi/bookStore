export interface IBook {
  id: number;
  name?: string | null;
  title?: string | null;
  description?: string | null;
  subTitle?: string | null;
  publisher?: string | null;
  author?: string | null;
  listPrice?: string | null;
  language?: string | null;
  retailPrice?: string | null;
  image?: string | null;
  pageCount?: number | null;
  averageRating?: number | null;
  ratingCount?: number | null;
  infoLink?: string | null;
  webReaderLink?: string | null;
  isbn?: string | null;
  longDescription?: string | null;
  textSnippet?: string | null;
}

export type NewBook = Omit<IBook, 'id'> & { id: null };
