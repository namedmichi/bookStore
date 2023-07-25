import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IBook } from '../entities/book/book.model';
import { NgIf } from '@angular/common';

@Component({
  standalone: true,
  selector: 'jhi-book-detail-card',
  templateUrl: './book-detail-card.component.html',
  styleUrls: ['./book-detail-card.component.scss'],
  imports: [NgIf],
})
export class BookDetailCardComponent implements OnInit {
  book: IBook;
  id: number;
  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.route.params.subscribe(params => {
      let id = params['myBook'];
      console.log(id);
      this.id = id;
      var url = 'http://localhost:9000/api/books/' + id;
      const headers = new HttpHeaders()
        .set('accept', '*/*')
        .set(
          'Authorization',
          'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDM1MjE4OCwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMjY1Nzg4fQ.IRTTud1czNHLQXmNfX8Zh14M4vS_iI92UYL4gJfDkVXyMHjtRnpYkYZvUW3VR7x3sXNulwnzozasX4vrgkthlA'
        );
      this.http.get(url, { headers }).subscribe(data => {
        this.book = data as IBook;
        console.log(this.book);
      });
    });
  }

  delEntry(): void {
    const headers = new HttpHeaders()
      .set('accept', '*/*')
      .set(
        'Authorization',
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDM1MjE4OCwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMjY1Nzg4fQ.IRTTud1czNHLQXmNfX8Zh14M4vS_iI92UYL4gJfDkVXyMHjtRnpYkYZvUW3VR7x3sXNulwnzozasX4vrgkthlA'
      );
    this.http.delete('http://localhost:9000/api/books/' + this.id, { headers }).subscribe(data => {
      console.log(data);
    });
  }
}
