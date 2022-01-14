### Ćwiczenie 9

1. Pobrać i otworzyć kod z folderu "cw3/odp"
1. Utworzyć `TaskQueryRepository` jak `TaskRepository`
   1. W `TaskRepository` zostawić tylko `findById`, `save`, `saveAll`, `deleteById`
   1. `count` i `findAllByDoneIsFalseAndProject_Id` przenieść do `TaskQueryRepository`
   1. Dopisać domyślną `areUndoneTasksWithProjectId` korzystającą z `findAllByDoneIsFalseAndProject_Id`
   1. Dopisać `getSingleById` zwracającą `TaskDto`
   1. Dopisać [`<T> Set<T> findAllBy(Class<T> type)`](https://stackoverflow.com/a/48442873/4774651)
1. [Zamienić `TaskWithChangesDto` w interfejs z getterami](https://www.baeldung.com/spring-data-jpa-projections)
1. Usunąć zwracanie `changesCount` z klasy `Task`
1. Skorzystać z `TaskQueryRepository` wszędzie gdzie potrzebne odczyty zadań
