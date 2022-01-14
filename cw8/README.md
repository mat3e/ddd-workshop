### Ćwiczenie 8

1. Pobrać i otworzyć kod z folderu "app"
1. Przeanalizować istniejące klasy, szczególnie `Card` i `CardRepository`
1. Przetestować aplikację, korzystając z Postmana i&nbsp;kolekcji żądań "src/main/resources/CreditCard8.postman_collection.json"
1. Dodać `CardSnapshot` - zarówno tworzenie agregatu ze snaphota jak i pobieranie snapshota z agregatu
1. Przenieść mapowanie Hibernate'a na `CardSnapshot`
1. Zmienić `CardRepository`, żeby implementacja Springa korzystała z `CardSnapshot`
1. Najprościej dodać metody domyślne, np. 
   ```
   default Optional<Card> findBy(CardId id) {
       return findById(id).map(Card::restore);
   }
   ```
