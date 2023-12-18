# Projet IFT6002 Qualité et métriques du logiciel (Automne 2023)

## Equipe 9

- Laris Babou (NI: 536 951 333)
- Fabrice Gagnon (NI: 111 146 970)
- Louis Greiner (NI: 537 160 604)
- Léonard Pannetier (NI: 537 188 305)
- Bappi Rahaman (NI: 537 046 961)
- Deivi Robles (NI: 536 941 796)
- Noam Schmitt (NI: 537 171 683)
- Loïs Trassoudaine (NI: 537 171 697)
- Tom Visticot (NI: 537 080 885)
- Vladimir Zephirin (NI: 536 923 079)

## Choix d'implémentation

- Gestion de l'orchestration avec GameService.
- Domain riche, délégation du plus de responsabilités affaire possibles aux classes concernées, directement dans le domaine pour respecter le DIP.
- **(modifié)** Utilisation de la classe abstraite `Character` et d'interfaces associées pour les comportements spécifiques (`HamstagramManager`...) pour
  rendre le code le
  plus générique possible et simple à orchestrer.
- **(modifié)** Implémentation de Characters, une classe de liste de Character, avec des méthodes de tri associées, pour éviter les appels redondants relatifs
  aux types des
  personnages. De plus, cela permet de filtrer selon des types génériques.
- Utilisation d'une interface `Action` pour manipuler un type commun d'action que l'on stocke comme une pile `Actions` dans `Turn`.
- Réalisation des tests E2E avec Postman (cf. [newman readme](./newman/README.md)).

## Justifications techniques

### Optional

- Suppression d'une grande partie des `Optional` dans le code :
  - de `Character`, car réglé à travers les interfaces associées et `Characters`
  - de la base de données (`NotFound`) car renvoie d'erreur directement catch dans l'interface.

### Actions

- **(modifié)** Priorités définies à travers `ActionEnumPriority` (OCP, chaîne de responsabilités).
- **(modifié)** Gestion de la priorité à l'aide de l'objet `PriorityQueue<QueueNode>` dans Actions, pour trier les actions selon leur priorité, ainsi que leur
  d'arrivée si la
  priorité est égale (car `PriorityQueue` ne permet pas de connaître l'ordre d'ajout).
- **(modifié)** `GameVisitor` implémente le pattern Visitor, permettant de donner accès aux données du repo aux actions, tout en assurant une synchronisation
  des données des
  repos en fonction des conséquences des actions. Pour faciliter l'orchestration, toutes les autres implémentations des anciennes stories ont été converties en
  Action.

### Enums

- Override l'utilisation par défaut des Enum pour se conformer aux attentes clients (retour attendu en lowercase bien que les Enums sont en uppercase).
- **(modifié)** Validation des types entrés via `fromString` et gestion des erreurs associées au sein de l'Enum, exceptions qui sont catch dans l'interface
  directement.

### Persistence InMemory

- Utilisation de `LinkedHashMap` pour gérer des HashMap dans la couche Persistence, des List dans l'application, et garder l'ordre d'ajout (chronologique) à
  travers
  le projet. Cela est nécessaire pour certaines fonctionnalités des stories (sélection des acteurs par les films en ordre chronologique, par exemple).
- **(modifié)** Throw des exceptions type `NotFound` au niveau de la base de données, qui sont récupérées par l'interface.

### Gestion des erreurs

- **(modifié)** Suppression des exceptions au niveau de l'interface, remplacées par des `Mappers` relatifs à chaque exception du domain. Les `Mappers`
  d'exception
  comportent alors le payload qu'il faut retourner à l'API.
- **(modifié)** `Mapper` d'exception inconnue inchangée, et `ExceptionResponse` encapsule tout type d'erreurs connues.

## Spécifications choisies

### Élimination de personnage durant <i>en tournage</i>

- Si par exemple un personnage meurt après avoir été casté
  (juste avant le tournage), alors il est éliminé du `casting`, mais le deuxième acteur est conservé.
  On calcule un nouveau `potential_casting`, pour ajouter un nouvel acteur au `casting`. On le sélectionne selon
  les critères de `MovieHighBudget` et `MovieLowBudget`.
  Une autre alternative aurait pu être de ne pas garder le deuxième acteur dans `casting` et de sélectionner à nouveau tous
  les acteurs à partir de `potential_casting`.
- **(modifié)** "Est-ce que la récursion est vraiment nécessaire?" (cf. CR1) -> Non, mais sans quoi la fonction `updateNextTurn` devient plus longue et redondante. De plus,
  la récursivité n'est pas dangereuse car on descend d'un seul niveau seulement, et ne risque pas de boucler indéfiniment.