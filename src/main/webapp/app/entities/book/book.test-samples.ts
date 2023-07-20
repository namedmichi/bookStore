import { IBook, NewBook } from './book.model';

export const sampleWithRequiredData: IBook = {
  id: 28066,
  name: 'weiblich Algerien Hellblau',
};

export const sampleWithPartialData: IBook = {
  id: 882,
  name: 'maiores',
  subTitle: 'anlässlich',
  image: 'paradoxerweise',
  averageRating: 29149,
  longDescription: '../fake-data/blob/hipster.txt',
  textSnippet: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IBook = {
  id: 6855,
  name: 'in weiblich nocheinmal',
  title: 'Guatemala Sache',
  description: '../fake-data/blob/hipster.txt',
  subTitle: 'Thailand Senegal',
  publisher: 'Niedersachsen tupfen',
  author: 'Jadegrün Honduras vermöge',
  listPrice: 'Himmelblau',
  language: 'beharrlich',
  retailPrice: 'versöhnen Bayern',
  image: 'Schrank',
  pageCount: 26185,
  averageRating: 26914,
  ratingCount: 25359,
  infoLink: 'unlängst',
  webReaderLink: 'Haiti',
  isbn: '../fake-data/blob/hipster.txt',
  longDescription: '../fake-data/blob/hipster.txt',
  textSnippet: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewBook = {
  name: 'sauber',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
