import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {
  @Output() searchChanged = new EventEmitter<string>();
  onSearchChange(event: any) {
    const searchTerm = event.target.value;
  }

  onSearch() {
    const searchInput = document.querySelector('.search-input') as HTMLInputElement;
    const searchTerm = searchInput.value.trim();

    this.searchChanged.emit(searchTerm);

    searchInput.value = '';
  }
}
