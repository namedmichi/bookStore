import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IBook } from '../entities/book/book.model';

@Component({
  selector: 'jhi-book-search',
  templateUrl: './book-search.component.html',
  styleUrls: ['./book-search.component.scss'],
})
export class BookSearchComponent implements OnInit {
  constructor(private http: HttpClient) {}

  books: IBook[] = [];

  async getBooks(thema: String) {
    var url = 'https://www.googleapis.com/books/v1/volumes?q=' + thema;

    // Set the headers
    const headers = new HttpHeaders().set('accept', '*/*');

    try {
      // Make the GET request
      const response = await this.http.get(url, { headers }).toPromise();

      console.log(response);
      return response;
    } catch (error) {
      // Handle any errors here
      console.error(error);
      return [];
    } finally {
      console.log('finally');
    }
  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
  }

  makeRequest(): void {
    var thema;

    thema = (<HTMLInputElement>document.getElementById('thema')).value;
    console.log(thema);
    this.getBooks(thema).then(data => {
      var dataArray = data as any;
      console.log(dataArray);
      console.log(typeof dataArray);

      var firstElement = dataArray[Object.keys(dataArray)[2]];
      var len = Object.keys(firstElement).length;
      console.log(dataArray[Object.keys(dataArray)[2]]);

      var books: IBook[] = [];
      for (let i = 0; i < len; i++) {
        let newBook: IBook = {
          id: i,
          name: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo.title,
          title: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo.title,

          subTitle: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.subtitle,

          description: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.description,

          language: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.language,

          listPrice: dataArray[Object.keys(dataArray)[2]][i]?.saleInfo?.listPrice?.amount.toString(),

          image: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.imageLinks?.thumbnail,

          pageCount: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.pageCount,

          averageRating: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.averageRating,

          ratingCount: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.ratingCount,

          author: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.authors[0].toString(),

          isbn: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.industryIdentifiers?.[1]?.identifier,

          infoLink: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.infoLink,

          webReaderLink: dataArray[Object.keys(dataArray)[2]][i]?.accessInfo?.webReaderLink,

          longDescription: dataArray[Object.keys(dataArray)[2]][i]?.volumeInfo?.description,

          textSnippet: dataArray[Object.keys(dataArray)[2]][i]?.searchInfo?.textSnippet,
        };
        console.log(newBook);
        books.push(newBook);
      }
      for (let i = 0; i < books.length; i++) {
        this.postBook(books[i]);
        console.log();
      }
    });
  }

  postBook(book: IBook) {
    console.log(book);
    const url = 'http://localhost:9000/api/books';

    // Define the headers for the request
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization:
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4OTk3NDA5NywiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjg5ODg3Njk3fQ.veaH5nIEKb2GVKad1hg8AsgECwSII23dZ0cxxxr9GKHh2mNJfnDKC9Ckmh0J0wSn42sezzvnXzeAZUKkdSZdHA',
      accept: '*/*',
    });

    if (book?.description?.length && book.description.length > 250) {
      book.description = book.description.substring(0, 247) + '...';
    }
    // Define the request body
    const requestBody = {
      name: book?.name || 'null',
      title: book?.title || 'null',
      description: book?.description || 'null',
      subTitle: book?.subTitle || 'null',

      publisher: book?.publisher || 'null',
      author: book?.author || 'null',
      listPrice: book?.listPrice || 'null',

      language: book?.language || 'null',
      retailPrice: book?.retailPrice || 'null',
      image: book?.image || 'null',

      pageCount: book?.pageCount || -999,
      averageRating: book?.averageRating || -999,
      ratingCount: book?.ratingCount || -999,

      isbn: book?.isbn || 'null',
      infoLink: book?.infoLink || 'null',
      webReaderLink: book?.webReaderLink || 'null',
      longDescription: book?.longDescription || 'null',
      textSnippet: book?.textSnippet || 'null',
    };

    console.log(JSON.stringify(requestBody, null, 2));
    // Send the POST request
    this.http.post(url, requestBody, { headers }).subscribe(
      response => {
        // Handle the response here
        console.log(response);
      },
      error => {
        // Handle any errors here
        console.error(error);
      }
    );
  }
}
