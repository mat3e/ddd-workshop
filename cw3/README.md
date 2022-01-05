### Ćwiczenie 3
                        
1. <!-- .element: class="fragment Fz(.75em)" --> Pobrać i otworzyć kod z folderu "app"
1. <!-- .element: class="fragment Fz(.75em)" --> Popatrzeć na moduł `task`. Co jest modelem? Gdzie jest logika?
1. <!-- .element: class="fragment Fz(.75em)" --> Przerobić funkcję `save` z `TaskFacade` - przenieść logikę do modelu
1. <!-- .element: class="fragment Fz(.75em)" --> Usunąć gettery i settery z encji `Task`. Skorzystać wszędzie z nowej logiki modelu
1. <!-- .element: class="fragment Fz(.75em)" --> Jakie reguły warto dodać? Co jeszcze poprawić?
1. <!-- .element: class="fragment Fz(.75em)" --> Przykładowo
   1. <!-- .element: class="fragment" --> `toggle()` do zmiany stanu i licznika
   1. <!-- .element: class="fragment" --> `switchTo(boolean state)` - takie `toggleIfNeeded`
   1. <!-- .element: class="fragment" --> `updateInfo` do zmiany dodatkowych informacji
   1. <!-- .element: class="fragment" --> Nowa reguła - nie można edytować zrobionego taska
