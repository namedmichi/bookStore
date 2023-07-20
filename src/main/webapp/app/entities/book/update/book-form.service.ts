import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBook, NewBook } from '../book.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBook for edit and NewBookFormGroupInput for create.
 */
type BookFormGroupInput = IBook | PartialWithRequiredKeyOf<NewBook>;

type BookFormDefaults = Pick<NewBook, 'id'>;

type BookFormGroupContent = {
  id: FormControl<IBook['id'] | NewBook['id']>;
  name: FormControl<IBook['name']>;
  title: FormControl<IBook['title']>;
  description: FormControl<IBook['description']>;
  subTitle: FormControl<IBook['subTitle']>;
  publisher: FormControl<IBook['publisher']>;
  author: FormControl<IBook['author']>;
  listPrice: FormControl<IBook['listPrice']>;
  language: FormControl<IBook['language']>;
  retailPrice: FormControl<IBook['retailPrice']>;
  image: FormControl<IBook['image']>;
  pageCount: FormControl<IBook['pageCount']>;
  averageRating: FormControl<IBook['averageRating']>;
  ratingCount: FormControl<IBook['ratingCount']>;
  infoLink: FormControl<IBook['infoLink']>;
  webReaderLink: FormControl<IBook['webReaderLink']>;
  isbn: FormControl<IBook['isbn']>;
  longDescription: FormControl<IBook['longDescription']>;
  textSnippet: FormControl<IBook['textSnippet']>;
};

export type BookFormGroup = FormGroup<BookFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BookFormService {
  createBookFormGroup(book: BookFormGroupInput = { id: null }): BookFormGroup {
    const bookRawValue = {
      ...this.getFormDefaults(),
      ...book,
    };
    return new FormGroup<BookFormGroupContent>({
      id: new FormControl(
        { value: bookRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(bookRawValue.name, {
        validators: [Validators.required],
      }),
      title: new FormControl(bookRawValue.title),
      description: new FormControl(bookRawValue.description),
      subTitle: new FormControl(bookRawValue.subTitle),
      publisher: new FormControl(bookRawValue.publisher),
      author: new FormControl(bookRawValue.author),
      listPrice: new FormControl(bookRawValue.listPrice),
      language: new FormControl(bookRawValue.language),
      retailPrice: new FormControl(bookRawValue.retailPrice),
      image: new FormControl(bookRawValue.image),
      pageCount: new FormControl(bookRawValue.pageCount),
      averageRating: new FormControl(bookRawValue.averageRating),
      ratingCount: new FormControl(bookRawValue.ratingCount),
      infoLink: new FormControl(bookRawValue.infoLink),
      webReaderLink: new FormControl(bookRawValue.webReaderLink),
      isbn: new FormControl(bookRawValue.isbn),
      longDescription: new FormControl(bookRawValue.longDescription),
      textSnippet: new FormControl(bookRawValue.textSnippet),
    });
  }

  getBook(form: BookFormGroup): IBook | NewBook {
    return form.getRawValue() as IBook | NewBook;
  }

  resetForm(form: BookFormGroup, book: BookFormGroupInput): void {
    const bookRawValue = { ...this.getFormDefaults(), ...book };
    form.reset(
      {
        ...bookRawValue,
        id: { value: bookRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BookFormDefaults {
    return {
      id: null,
    };
  }
}
