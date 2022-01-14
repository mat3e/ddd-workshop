### Ćwiczenie 10

1. Pobrać i otworzyć kod z folderu "app"
1. Przeanalizować szczególnie moduły `customer` (zwykły CRUD) i `cardinfo` (sam widok)
1. Przetestować aplikację, korzystając z Postmana i&nbsp;kolekcji żądań "src/main/resources/CreditCard.postman_collection.json"
1. Rozwinąć `cardinfo` - pobierać również nieaktywne karty
1. Np. dodać `findRestricted` oraz `findBy`, łączącą `findActive` i&nbsp;`findRestricted`