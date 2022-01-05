### Ćwiczenie 3
                        
1. Pobrać i otworzyć kod z folderu "app"
1. Popatrzeć na moduł `task`. Co jest modelem? Gdzie jest logika?
1. Przerobić funkcję `save` z `TaskFacade` - przenieść logikę do modelu
1. Usunąć gettery (poza `getChangesCount`) i settery z encji `Task`. Skorzystać wszędzie z nowej logiki modelu
1. Jakie reguły warto dodać? Co jeszcze poprawić?
1. Przykładowo
   1. `toggle()` do zmiany stanu i licznika
   1. `switchTo(boolean state)` - takie `toggleIfNeeded`
   1. `updateInfo` do zmiany dodatkowych informacji
   1. Nowa reguła - nie można edytować zrobionego taska
